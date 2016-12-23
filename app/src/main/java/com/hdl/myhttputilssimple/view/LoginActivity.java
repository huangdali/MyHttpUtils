package com.hdl.myhttputilssimple.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hdl.myhttputils.MyHttpUtils;
import com.hdl.myhttputils.bean.CommCallback;
import com.hdl.myhttputils.utils.FailedMsgUtils;
import com.hdl.myhttputilssimple.R;
import com.hdl.myhttputilssimple.bean.LoginBean;
import com.hdl.myhttputilssimple.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName, etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName = (EditText) findViewById(R.id.et_login_username);
        etPwd = (EditText) findViewById(R.id.et_login_pwd);

    }

    /**
     * 登录的单击事件
     *
     * @param view
     */
    public void onLogin(View view) {
        String username = etUserName.getText().toString().trim();//获取用户输入的用户名
        String pwd = etPwd.getText().toString().trim();//获取用户输入的密码
        Map<String, Object> params = new HashMap<>();//构造请求的参数
        params.put("username", username);
        params.put("pwd", pwd);
        MyHttpUtils.build()
                .url("http://192.168.2.153:8080/MyHttpUtilsServer/userlogin")
                .addParams(params)
                .setJavaBean(LoginBean.class)
                .onExecuteByPost(new CommCallback<LoginBean>() {
                    @Override
                    public void onSucceed(LoginBean loginBean) {
                        ToastUtils.showToast(LoginActivity.this,loginBean.getMsg());
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        ToastUtils.showToast(LoginActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }
                });
    }
}
