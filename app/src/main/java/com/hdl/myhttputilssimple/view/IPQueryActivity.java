package com.hdl.myhttputilssimple.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hdl.myhttputils.MyHttpUtils;
import com.hdl.myhttputils.bean.CommCallback;
import com.hdl.myhttputils.utils.FailedMsgUtils;
import com.hdl.myhttputilssimple.R;
import com.hdl.myhttputilssimple.bean.IPBean;
import com.hdl.myhttputilssimple.utils.ToastUtils;

public class IPQueryActivity extends AppCompatActivity {

    private EditText etIp;
    private TextView tvIp, tvCountry, tvArea, tvRegion, tvCity, tvIsp;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_query);
        etIp = (EditText) findViewById(R.id.et_ip_input_ip);
        tvIp = (TextView) findViewById(R.id.tv_ip_queryip);
        tvCountry = (TextView) findViewById(R.id.tv_ip_country);
        tvArea = (TextView) findViewById(R.id.tv_ip_area);
        tvRegion = (TextView) findViewById(R.id.tv_ip_region);
        tvCity = (TextView) findViewById(R.id.tv_ip_city);
        tvIsp = (TextView) findViewById(R.id.tv_ip_isp);
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("查询中，请稍后...");
    }

    /**
     * 查询按钮单击事件
     *
     * @param view
     */
    public void onQuery(View view) {
        mProgressDialog.show();
        String ip = etIp.getText().toString().trim();
        MyHttpUtils.build()//构建myhttputils
                .url("http://ip.taobao.com/service/getIpInfo.php")//获取ip的url
                .addParam("ip", ip)//请求参数
                .setJavaBean(IPBean.class)//设置请求结果对应的java对象
                .onExecute(new CommCallback<IPBean>() {
                    @Override
                    public void onSucceed(IPBean ipBean) {
                        showResult(ipBean);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        ToastUtils.showToast(IPQueryActivity.this, FailedMsgUtils.getErrMsgStr(throwable.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        mProgressDialog.dismiss();
                    }
                });
    }

    /**
     * 显示结果
     * @param ipBean
     */
    private void showResult(IPBean ipBean) {
        IPBean.DataBean ipInfo = ipBean.getData();
        tvIp.setText(etIp.getText().toString().trim());
        tvCountry.setText(ipInfo.getCountry());
        tvArea.setText(ipInfo.getArea());
        tvCity.setText(ipInfo.getCity());
        tvIsp.setText(ipInfo.getIsp());
        tvRegion.setText(ipInfo.getRegion());
    }
}
