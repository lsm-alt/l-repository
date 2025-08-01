package com.codedata.rbac.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static{
        objectMapper
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ERR_JSON);
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ERR_JSON);
        }
    }

    public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ERR_JSON);
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(text, typeReference);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ERR_JSON);
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ERR_JSON);
        }
    }

    public static <T> List<T> jsonToArrayList(String str, Class<T> valueType) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, valueType);
            return objectMapper.readValue(str, type);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ERR_JSON,e.getMessage());
        }
    }

    public static <K, V> Map<K, V> jsonToMap(String text) {
        try {
            return objectMapper.readValue(text, new TypeReference<Map<K, V>>() {
            });
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ERR_JSON);
        }
    }

}
