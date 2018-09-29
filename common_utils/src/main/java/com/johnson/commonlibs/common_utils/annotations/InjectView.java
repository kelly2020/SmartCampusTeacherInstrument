package com.johnson.commonlibs.common_utils.annotations;

import java.lang.annotation.*;

/**
 * Created by zhangwt on 15/10/9.<br/>
 * Version 1.0 <br/>
 * Description dependence view
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface InjectView {

    public int id() default -1;

    public String onClick() default "";
}
