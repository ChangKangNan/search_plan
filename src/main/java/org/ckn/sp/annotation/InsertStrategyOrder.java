package org.ckn.sp.annotation;

import java.lang.annotation.*;
import java.sql.Connection;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface InsertStrategyOrder {
    /**
     * 加载顺序
     */
    int index() default 0;
}
