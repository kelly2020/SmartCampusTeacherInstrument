package com.johnson.commonlibs.common_utils.api;

/**
 *
 * @author zhangwt
 * @version 1.0
 * @date 15/12/1
 */
public class APIError {
    private int statusCode;
    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
