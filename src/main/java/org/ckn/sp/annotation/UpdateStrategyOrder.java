package org.ckn.sp.annotation;

import java.lang.annotation.*;

/**
 * @author ckn
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UpdateStrategyOrder {
    /**
     * 加载顺序
     */
    int index() default 0;
}
