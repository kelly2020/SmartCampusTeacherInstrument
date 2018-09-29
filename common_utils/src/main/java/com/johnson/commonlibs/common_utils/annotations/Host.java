package com.johnson.commonlibs.common_utils.annotations;

import java.lang.annotation.*;

/**
 * Created by zhangwt on 15/10/9.<br/>
 * Version 1.0 <br/>
 * Description remote server url
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Host {
    String value() default "";
}
