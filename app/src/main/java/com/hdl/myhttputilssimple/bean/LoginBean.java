package com.hdl.myhttputilssimple.bean;

/**
 * Created by HDL on 2016/12/21.
 */

public class LoginBean {

    private String response;
    private String msg;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "response='" + response + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
