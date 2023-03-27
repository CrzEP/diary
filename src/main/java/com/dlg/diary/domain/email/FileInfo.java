package com.dlg.diary.domain.email;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lingui
 * @Date 2023/3/24 18:29
 */
@Data
@Entity
@Table(name = "t_fileInfo")
@NoArgsConstructor
public class FileInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;
    private Date createTime;

    public FileInfo(String fileName, String filePath, Date createTime) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.createTime = createTime;
    }
}
