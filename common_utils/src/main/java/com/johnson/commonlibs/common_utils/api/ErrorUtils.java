package com.johnson.commonlibs.common_utils.api;


import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by zhangwt on 15/12/1.<br/>
 * Error处理工具
 *
 * @author zhangwt
 * @version 1.0
 * @date 15/12/1
 */
public class ErrorUtils {

    public static APIError parseError(Response<?> response, Retrofit retrofit) {
        Converter<ResponseBody, APIError> converter = retrofit.responseBodyConverter(APIError.class, new Annotation[0]);
        APIError apiError;
        try {
            apiError = converter.convert(response.errorBody());
        } catch (IOException e) {
            e.printStackTrace();
            return new APIError();
        }
        return apiError;
    }
}
