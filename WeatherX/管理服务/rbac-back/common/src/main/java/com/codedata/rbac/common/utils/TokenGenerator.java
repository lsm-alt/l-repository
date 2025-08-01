package com.codedata.rbac.common.utils;
import cn.hutool.core.codec.Base64;
import com.codedata.rbac.common.exception.ServiceException;
import java.util.UUID;
public class TokenGenerator {
    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }
    private static String generateValue(String param) {
        try {
            //TODO 目前直接转成base64生成token
            byte[] res = param.getBytes();
            return new String(Base64.encode(res));
        } catch (Exception e) {
            throw new ServiceException("生成Token失败:"+e.getMessage());
        }
    }
}
