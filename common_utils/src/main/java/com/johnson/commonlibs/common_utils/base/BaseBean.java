package com.johnson.commonlibs.common_utils.base;

import java.io.Serializable;

/**
 * Created by zhangwt on 15/12/22.<br/>
 * 基类Bean
 * @author zhangwt
 * @version 1.0
 * @date 15/12/22
 */
public class BaseBean implements Serializable {
    /**
     * 服务器返回状态
     */
    private int status;
    /**
     * 服务器返回信息
     */
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
