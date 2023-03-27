package com.dlg.diary.util;

import com.dlg.diary.domain.email.EmailAccount;
import com.dlg.diary.entity.EmailInfo;
import com.dlg.diary.domain.email.FileInfo;
import com.dlg.diary.domain.email.FileType;
import com.dlg.diary.exception.ProcessException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import java.io.*;
import java.util.*;

/**
 * @author lingui
 * @Date 2023/3/24 16:10
 */
@Slf4j
public class EmailReceiveUtil {

    /**
     * 自定义的转发邮箱
     * 需要前往163设置
     */
    private static final String FLODER = "diary";

    /**
     * 收取邮件
     *
     * @param account 账户信息
     * @return 邮件信息
     */
    public static List<EmailInfo> receiveEmail(EmailAccount account) {
        try {
            return receiveEmails(account);
        } catch (MessagingException | IOException e) {
            throw ProcessException.processInterrupt("收取邮件");
        }
    }

    /**
     * 收取邮件
     *
     * @param account 账户信息
     * @return 邮件信息
     */
    public static List<EmailInfo> receiveEmails(EmailAccount account) throws MessagingException, IOException {
        Properties prop = System.getProperties();
        prop.put("mail.store.protocol", "imap");
        prop.put("mail.imap.host", "imap.163.com");
        prop.put("mail.imap.port", "143");
        prop.put("mail.imap.connectiontimeout", "10000");
        prop.put("mail.imap.timeout", "10000");

        HashMap<String, String> IAM = getIam();

        Session session = Session.getInstance(prop);
        // session.setDebug(true);
        System.out.println("sss" + session);
        IMAPStore store = (IMAPStore) session.getStore("imap");
        // 使用imap会话机制，连接服务器
        store.connect(account.getEmailAccount(), account.getEmailPasswd());
        store.id(IAM);
        // 收件箱
        IMAPFolder folder = (IMAPFolder) store.getFolder(FLODER);
        folder.open(Folder.READ_WRITE);
        //获取未读邮件
        int unRead = folder.getMessageCount() - folder.getUnreadMessageCount() + 1;
        Message[] messages = folder.getMessages(unRead, folder.getMessageCount());
        //解析邮件
        List<EmailInfo> emailList = parseMessage(account, messages);
        //释放资源
        folder.close(true);
        store.close();
        log.info("收取邮件成功： {}", emailList);
        return emailList;

    }

    private static HashMap<String, String> getIam() {
        HashMap<String, String> IAM = new HashMap<>();
        //带上IMAP ID信息，由key和value组成，例如name，version，vendor，support-email等。
        IAM.put("name", "lingui");
        IAM.put("version", "1.0.0");
        IAM.put("vendor", "diaryService");
        IAM.put("support-email", "lingui1080@163.com");
        return IAM;
    }

    /**
     * 解析邮件
     *
     * @param messages 要解析的邮件列表
     */
    private static List<EmailInfo> parseMessage(EmailAccount account, Message... messages) throws MessagingException, IOException {
        List<EmailInfo> emailList = new ArrayList<>();
        if (messages == null || messages.length < 1) {
            return emailList;
        }
        // 解析所有邮件
        for (Message message : messages) {
            MimeMessage msg = (MimeMessage) message;
            InternetAddress internetAddress = getFrom(msg);
            String address = internetAddress.getAddress();
            String filterEmail = account.getReceiveFilterEmail();
            //如果指定邮箱,只读指定邮箱的未读邮件
            if (StringUtils.isNotBlank(filterEmail) && !address.startsWith(filterEmail)) {
                continue;
            }
            // 标记已读
            msg.setFlag(Flags.Flag.SEEN, true);
            //存储邮件信息
            EmailInfo emailInfo = new EmailInfo();
            emailInfo.setEmailCode(msg.getMessageID()); //ID
            System.out.println("content " + msg.getContent());
            MimeMultipart multipart = (MimeMultipart) msg.getContent();
            int multipartCount = multipart.getCount();
            List<FileInfo> fileInfos = new ArrayList<>();
            for (int j = 0; j < multipartCount; j++) {
                BodyPart bodyPart = multipart.getBodyPart(j);
                String contentType = bodyPart.getContentType();
                log.debug("检测到内容类型为： {}", contentType);
                // 是一个文件
                if (contentType.contains(MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
                    String fileName = bodyPart.getFileName();
                    InputStream inputStream = bodyPart.getInputStream();
                    String filePath = account.getFileSaveDir() + FileType.name2item(fileName)
                            + File.separator + DateUtils.dateTimeNow() + "_" + fileName;
                    IOUtils.copy(inputStream, new FileOutputStream(filePath));
                    log.info("已储存文件： {}", filePath);
                    // 文件信息
                    FileInfo fileInfo = new FileInfo(fileName, filePath, DateUtils.getNowDate());
                    fileInfos.add(fileInfo);
                }
            }
            emailInfo.setFileInfos(fileInfos);
            emailInfo.setSender(address);
            //转码后的标题
            emailInfo.setTitle(decodeText(msg.getSubject()));
            //收件人
            emailInfo.setReceiver(getReceiveAddress(msg));
            //收件日期
            emailInfo.setAcceptTime(msg.getSentDate());
            emailList.add(emailInfo);
        }
        return emailList;
    }


    /**
     * 获得邮件发件人
     *
     * @param msg 邮件内容
     * @return 地址
     * @throws MessagingException 消息异常
     */
    private static InternetAddress getFrom(MimeMessage msg) throws MessagingException {
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");

        return (InternetAddress) froms[0];
    }

    /**
     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
     * <p>Message.RecipientType.TO  收件人</p>
     * <p>Message.RecipientType.CC  抄送</p>
     * <p>Message.RecipientType.BCC 密送</p>
     *
     * @param msg 邮件内容
     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
     * @throws MessagingException 邮件异常
     */
    private static String getReceiveAddress(MimeMessage msg) throws MessagingException {
        StringBuilder receiveAddress = new StringBuilder();
        Address[] addresss;
        addresss = msg.getAllRecipients();
        if (addresss == null || addresss.length < 1)
            return "";
        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress) address;
            receiveAddress.append(internetAddress.toUnicodeString()).append(",");
        }

        receiveAddress.deleteCharAt(receiveAddress.length() - 1); //删除最后一个逗号

        return receiveAddress.toString();
    }

    /**
     * 文本解码
     *
     * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本
     * @return 解码后的文本
     * @throws UnsupportedEncodingException 不支持的解码类型
     */
    private static String decodeText(String encodeText) throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }

    public static void main(String[] args) {
        EmailAccount account = new EmailAccount(
                "appTag", "",
                "sucrzep@163.com", "***", "D://temp//");
        List<EmailInfo> emailInfos = EmailReceiveUtil.receiveEmail(account);
        System.out.println(emailInfos);
    }


}
