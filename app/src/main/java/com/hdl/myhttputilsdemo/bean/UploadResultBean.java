package com.hdl.myhttputilsdemo.bean;

import java.io.Serializable;

/**
 * Created by HDL on 2016/10/15.
 */

public class UploadResultBean implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UploadResultBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
