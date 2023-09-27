package org.ckn.sp.util;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author ckn
 */
public class CopyUtil {
    public static <T> T copy(Object obj, Class<T> clazz) {
        try {
            return obj == null ? clazz.newInstance() : JSONObject.parseObject(JSONObject.toJSONString(obj), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> List<T> copy(List list, Class<T> clazz) {
        return list == null ? null : JSONObject.parseArray(JSONObject.toJSONString(list), clazz);
    }
}
