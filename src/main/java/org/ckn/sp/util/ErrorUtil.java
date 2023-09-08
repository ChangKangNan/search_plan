package org.ckn.sp.util;

import cn.ft.ckn.fastmapper.config.ApiException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.Collection;

/**
 * @author ckn
 */
public class ErrorUtil {

    public static void isEmpty(Object object, String msg) {
        if (ObjectUtil.isNotEmpty(object)) {
           return;
        }
        throw new ApiException(msg);
    }

    public static void isEmpty(Collection collection, String msg) {
        if (CollUtil.isNotEmpty(collection)) {
            return;
        }
        throw new ApiException(msg);
    }

    public static void isEmpty(Object[] array, String msg) {
        if (ArrayUtil.isNotEmpty(array)) {
            return;
        }
        throw new ApiException(msg);
    }
}
