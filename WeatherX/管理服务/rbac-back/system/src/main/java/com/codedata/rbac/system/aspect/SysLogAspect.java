package com.codedata.rbac.system.aspect;

import com.codedata.rbac.common.utils.HttpContextUtils;
import com.codedata.rbac.common.utils.IPUtils;
import com.codedata.rbac.common.utils.JsonUtils;
import com.codedata.rbac.security.auth.AuthedUser;
import com.codedata.rbac.system.annotation.SysLog;
import com.codedata.rbac.system.entity.LogEntity;
import com.codedata.rbac.system.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

@Aspect
@Component
public class SysLogAspect {
    private LogService logService;
    private static Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    public SysLogAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("@annotation(com.codedata.rbac.system.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        int ret = 1;
        Object result = null;
        String exception = null;
        //执被注解的方法
        try {
            result = point.proceed();
        } catch (Throwable e) {
            ret = 0;
            exception = getExceptionStr(e);
            throw e;
        } finally {
            long time = System.currentTimeMillis() - beginTime;
            saveLog(point, time, ret, result, exception);
        }
        return result;
    }


    private String getExceptionStr(Throwable cause) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        cause.printStackTrace(pw);
        return sw.toString();
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time, int ret, Object result, String exception) {
        try {
            StringBuilder content = new StringBuilder();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            LogEntity log = new LogEntity();
            SysLog syslog = method.getAnnotation(SysLog.class);
            if (syslog != null) {
                //获取注解上的type值
                log.setType(syslog.type());
            }
            //设置状态
            log.setResult(ret);
            //获取请求的方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            content.append("->请求方法:\n");
            content.append(className);
            content.append(".");
            content.append(methodName);
            content.append("()");
            content.append("\n");
            Object[] args = joinPoint.getArgs();
            if (args != null) {
                String params = JsonUtils.toJsonString(args);
                content.append("->请求参数:\n");
                content.append(params);
                content.append("\n");
            }
            if (result != null) {
                content.append("->响应参数:\n");
                String resp = JsonUtils.toJsonString(result);
                content.append(resp);
                content.append("\n");
            }
            if (exception != null) {
                content.append("->异常堆栈:\n");
                content.append(exception);
                content.append("\n");
            }
            if(content.length()>1000){
                content.setLength(1000);
                content.append("......");
            }
            log.setContent(content.toString());
            //获取request
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            //设置IP地址
            log.setIp(IPUtils.getIpAddr(request));
            //设置用户名
            String username = AuthedUser.getUser().getUsername();
            log.setUsername(username);
            log.setTime(time);
            logService.save(log);
        } catch (Exception e) {
            logger.error("保存系统日志失败:{}", e.getMessage());
        }
    }


}
