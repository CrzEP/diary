package com.dlg.diary.aspect;

import com.alibaba.fastjson.JSONObject;
import com.dlg.diary.common.annotation.ApiLog;
import com.dlg.diary.entity.ApiLogEntity;
import com.dlg.diary.repository.ApiLogRepository;
import com.dlg.diary.util.DateUtils;
import com.dlg.diary.util.HttpContextUtils;
import com.dlg.diary.util.IpUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 操作日志，切面处理类
 *
 * @author lingui
 */
@Aspect
@Component
@Slf4j
public class LogOperationAspect {

    @Resource
    ApiLogRepository repository;

    @Pointcut("@annotation(com.dlg.diary.common.annotation.ApiLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            //执行方法
            Object result = point.proceed();

            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, true);

            return result;
        } catch (Exception e) {
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, false);

            throw e;
        }
    }

    /**
     * 保存日志
     *
     * @param joinPoint 切入点
     * @param time      耗时
     * @param status    请求状态
     * @throws Exception 异常
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time, boolean status) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        ApiLog annotation = method.getAnnotation(ApiLog.class);

        ApiLogEntity entity = new ApiLogEntity();

        String date = DateUtils.getTime();
        entity.setLogTime(date);

        if (annotation != null) {
            //注解上的描述
            entity.setApi(annotation.value());
        }
        // 耗时
        entity.setTime(time);
        // 状态
        entity.setStatus(status);

        //请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (null != request) {
            entity.setIpAddr(IpUtils.getIpAddr(request));
            String header = request.getHeader(HttpHeaders.USER_AGENT);
            entity.setHeader(header);
            entity.setRequestUri(request.getRequestURI());
            entity.setMerthod(request.getMethod());
        }

        //请求参数
        Object[] args = joinPoint.getArgs();
        try {
            if (null != args && args.length>0) {
                String params = JSONObject.toJSONString(args[0]);
                entity.setArgs(params);
            }
        } catch (Exception e) {
            log.error("参数序列错误：",e);
            e.printStackTrace();
        }

        //保存到DB
        repository.save(entity);
    }
}