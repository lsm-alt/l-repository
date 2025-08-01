package com.codedata.rbac.common.utils;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * 数据转换
 * 主要用于 entity 与 view 互转
 */
public class ConvertUtils {
    private static Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    public static <T> T sourceToTarget(Object source, Class<T> target){
        if(source == null){
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.newInstance();
            BeanUtils.copyProperties(source, targetObject);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ERR_DATA_CONVERT,e.getMessage());
        }

        return targetObject;
    }
    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target){
        if(sourceList == null){
            return null;
        }
        List targetList = new ArrayList<T>(sourceList.size());
        try {
            for(Object source : sourceList){
                T targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        }catch (Exception e){
            throw new ServiceException(ErrorCode.ERR_DATA_CONVERT,e.getMessage());
        }
        return targetList;
    }
}
