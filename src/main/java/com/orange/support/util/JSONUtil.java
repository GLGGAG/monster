package com.orange.support.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@SuppressWarnings("unchecked")
public class JSONUtil {

    /** 只有一些基础规则的 json 映射器. 需要序列化和反序列化时, 就使用这个映射器 */
    private static final ObjectMapper BASIC = new ObjectMapper();

    /**
     * 用来渲染给前台的 json 映射器, 定义了一些自定义类的序列化规则, 然而并没有反序列化规则<br>
     * 所以使用此映射器序列化的 json, 想要反序列化回来调用(toObject toList)时将会不成功
     */
    public static final ObjectMapper RENDER = new RenderObjectMapper();

    private static class RenderObjectMapper extends ObjectMapper {
        private RenderObjectMapper() {
            super();
            // 不确定值的枚举返回 null
            configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
            // 不确定的属性项上不要失败
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // null 不序列化, 如果想要 空字符串也不序列化, 改成 NON_EMPTY 即可
            // setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    }

    /** 对象转换成 json 字符串 */
    public static String toJson(Object obj) {
        return toJson(BASIC, obj);
    }

    private static String toJson(ObjectMapper om, Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("object(" + obj + ") to json exception.", e);
        }
    }

    /** 对象转换成 json 字符串, 主要用来渲染到前台 */
    public static String toRender(Object obj) {
        return toJson(RENDER, obj);
    }

    /** 将 json 字符串转换为对象 */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return BASIC.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("json (" + json + ") to object(" + clazz.getName() + ") exception", e);
        }
    }

    /** 将 json 字符串转换为对象, 当转换异常时, 返回 null */
    public static <T> T toObjectNil(String json, Class<T> clazz) {
        try {
            return BASIC.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /** 将 json 字符串转换为指定的数组列表 */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            return BASIC.readValue(json, BASIC.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException("json(" + json + ") to list(" + clazz.getName() + ") exception.", e);
        }
    }
    /** 将 json 字符串转换为指定的数组列表 */
    public static <T> List<T> toListNil(String json, Class<T> clazz) {
        try {
            return BASIC.readValue(json, BASIC.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            return null;
        }
    }
}
