package com.johnson.commonlibs.common_utils.base;

import android.os.Parcelable;

/**
 * Created by zhangwt on 15/12/22.<br/>
 * 实体bean的基类
 *
 * @author zhangwt
 * @version 1.0
 * @date 15/12/22
 */
public class ResponseBean<T extends BaseBean & Parcelable> {
    T result;
}
