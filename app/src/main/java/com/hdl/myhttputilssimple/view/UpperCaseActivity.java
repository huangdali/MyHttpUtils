package com.hdl.myhttputilssimple.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hdl.myhttputils.MyHttpUtils;
import com.hdl.myhttputils.bean.StringCallBack;
import com.hdl.myhttputils.utils.FailedMsgUtils;
import com.hdl.myhttputilssimple.R;
import com.hdl.myhttputilssimple.utils.ToastUtils;

public class UpperCaseActivity extends AppCompatActivity {

    private EditText etContent;
    private TextView tvResult,tvIp,tvContry,tvArea,tvRegion,tvCity,tvIsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upper_case);
        etContent = (EditText) findViewById(R.id.et_uppercase_content);
        tvResult = (TextView) findViewById(R.id.tv_uppercase_result);

    }

    /**
     * 单击转换按钮的事件
     *
     * @param view
     */
    public void onUpperCase(View view) {
        String content = etContent.getText().toString();//拿到用户输入的内容
        MyHttpUtils.build()//构建myhttputils
                .url("http://192.168.2.153:8080/MyHttpUtilsServer/string.action")//请求的url
                .addParam("content",content)
                .onExecute(new StringCallBack() {//开始执行，并有一个回调（异步的哦---->直接可以更新ui）
                    @Override
                    public void onSucceed(String result) {//请求成功之后会调用这个方法
                        tvResult.append("\n");
                        tvResult.append(Html.fromHtml("<font size='30px' color='red'>" + result + "</font>"));
                        tvResult.append("\n");
                    }

                    @Override
                    public void onFailed(Throwable throwable) {//请求失败的时候会调用这个方法
                        ToastUtils.showToast(UpperCaseActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }
                });
    }
}
