package com.codedata.rbac.common.exception;
import com.codedata.rbac.common.utils.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 自定义的异常 不记录日志
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public Result handleServiceException(ServiceException e){
        return new Result().error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handlerNoFoundException(Exception e) {
        return new Result().error(ErrorCode.HTTP_404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        logger.error(e.getMessage(), e);
        return new Result().error(ErrorCode.ERR_DB_OPERATION,"数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(AuthorizationException e){
        logger.error(e.getMessage(), e);
        return new Result().error(ErrorCode.ERR_NO_PERMIT,"认证失败:"+e.getMessage());
    }
    /**
     * 顶级异常 记录日志
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        logger.error(e.getMessage(), e);
        return new Result().error(ErrorCode.HTTP_500,"操作异常:"+e.getMessage());
    }

}
