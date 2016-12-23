package com.hdl.myhttputils.module;

/**
 * 提供http请求器
 * Created by HDL on 2016/12/21.
 */

public class ProvideHttpRequester {
    private HttpRequester mHttpRequester;

    public ProvideHttpRequester(HttpRequester mHttpRequester) {
        this.mHttpRequester = mHttpRequester;
    }

    /**
     * 开始请求
     */
    public void startRequest() {
        mHttpRequester.request();
    }
}
