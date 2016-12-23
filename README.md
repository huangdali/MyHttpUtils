##[倾力之作]android轻量级网络请求框架MyHttputils2.1.6

>尊重原创，转载请注明出处: http://blog.csdn.net/qq137722697

###**一、前言**

>本版代码大换血，使用了策略模式和构造模式来组织代码，增加了更加人性化的请求构造，代码质量提高、效率显著提升。（但是使用风格基本没变哦）

2.0.2版本的基本的用法在[《android网络请求框架》一个轻量级的异步网络请求框架MyHttpUtils（新版更新记录，版本号：2.X）](http://blog.csdn.net/qq137722697/article/details/52843336)中有过详细的介绍。下面是对2.1.5版的详细使用介绍，对源码感兴趣的伙伴可以移步[github](https://github.com/huangdali/myhttputils)了解更多的消息。
##**二、功能介绍**

 - **1、支持get、post请求；**

 - **2、支持http和https的协议；**
 
 - **3、支持设置连接、读取超时时间（可选）；**
 
 - **4、支持json格式的请求结果（无论json格式多复杂，都能搞定）；**
 
 -  **5、支持传入JavaBean对象（解析之后的javabean对象）；**

 - **6、支持回调方法中反应传入javabean对象，这样可以在回调方法中直接拿到解析过后的javabean对象；**

 - **7、支持回调方法中更新UI（所以叫异步请求了）；**

 - **8、支持文件下载；---->带下载进度回调方法**

 - **9、支持单文件上传；**

 - **10、支持多文件上传；**

 - **11、支持请求完成后回调--->onComplete方法；**

 - **12、支持直接获取字符串；**

 - **13、支持参数与文件同时上传；**

 - **14、提供错误信息处理类；**

>MyHttpUtils满足了大部分的网络请求了，而且非常轻量级哦（大小：20.76k）

##**三、快速使用**

**使用gradle添加依赖（添加完之后Sync一下）：**

```
 compile 'com.huangdali:myhttputils:2.1.6'
```

**当然了网络权限可别忘加哦**

```
 <uses-permission android:name="android.permission.INTERNET" />
```

**文件上传和下载也需要添加权限（不要这个功能的就不用加）**

```
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

##**四、入门案例**
###**4.1、简单的一个网络请求（直接获取请求的结果--->字符串）**

```
MyHttpUtils.build()//构建myhttputils
                .url("http://192.168.2.153:8080/MyHttpUtilsServer/string.action")//请求的url
                .onExecute(new StringCallBack() {//开始执行，并有一个回调（异步的哦---->直接可以更新ui）
                    @Override
                    public void onSucceed(String result) {//请求成功之后会调用这个方法
                        //成功之后的拿到结果result进行处理
                    }
                    @Override
                    public void onFailed(Throwable throwable) {//请求失败的时候会调用这个方法
                        //失败之后的拿到错误结果throwable进行处理
                    }
                });
```

**通过上面的代码就能发起get请求；**

下面结合一个案例类演示：用户输入字母，点击转换即可得到对应的大写字母。**（小写转大写案例）**

**接口：**http://192.168.2.153:8080/MyHttpUtilsServer/string.action

**参数：**  content

**请求方式：** get

先看效果：

![这里写图片描述](http://img.blog.csdn.net/20161223225933221?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

来看怎么实现：

```
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
                    public void onSucceed(String result) {//请求成功之后会调用这个方法----显示结果
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
```

**ToastUtils是简单封装的Toast显示工具，FailedMsgUtils是myhttputils提供的一个用于得到错误信息的工具**

对于get请求，还可以这样搞------>直接拼接即可（参数含中文时不建议使用此方法）

   

```
     MyHttpUtils.build()//构建myhttputils
                .url("http://192.168.2.153:8080/MyHttpUtilsServer/string.action?content=" + content)
                .onExecute(new StringCallBack() {//开始执行，并有一个回调（异步的哦---->直接可以更新ui）
                 //此处省略回调处理
                    );
    }
```


参数多的情况怎么搞？加很多addParam()？继续往下看


###**4.2、get请求案例之Ip地址查询**

**接口：**http://ip.taobao.com/service/getIpInfo.php

**参数：** ip

**请求方式：** get

**返回结果：**

```
{
    "code":0,
    "data":{
        "country":"中国",
        "country_id":"CN",
        "area":"华南",
        "area_id":"800000",
        "region":"广东省",
        "region_id":"440000",
        "city":"广州市",
        "city_id":"440100",
        "county":"",
        "county_id":"-1",
        "isp":"腾讯网络",
        "isp_id":"1000153",
        "ip":"182.254.34.74"
    }
}
```

根据json就可以得到相对应的java对象，以前你是手动一个一个敲上去的吗？那看看[《\[android插件篇\]如何快速通过json构建javabean对象(GsonFormat使用教程)》](http://blog.csdn.net/qq137722697/article/details/52852804)你就会如鱼得水了（知道的请忽略）。

```
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
```

效果：

![这里写图片描述](http://img.blog.csdn.net/20161223225850986?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

**通过myhttputils配置一些基础请求的参数即可完成android中复杂的网络请求，自动解析json为对象，即可拿到结果对应的java对象，链式的编程使得代码结构清晰的同时也简化了网络请求过程。**


加点反馈效果----显示进度条对话框（可选回调方法***onComplete***的应用）：

![这里写图片描述](http://img.blog.csdn.net/20161223230029503?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
   

```
 /**
     * 查询按钮单击事件
     *
     * @param view
     */
    public void onQuery(View view) {
        mProgressDialog.show();//点击的时候显示进度条
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
                    public void onComplete() {//完成之后回调
                        mProgressDialog.dismiss();//完成之后就关闭进度条
                    }
                });
    }
```

onComplete会在请求结束的时候回调，这样你可以在接收之后做出相应的友好提示，比如这里的关闭进度条；


###**4.3、Post请求之登录教程**

**接口：**http://192.168.2.153:8080/MyHttpUtilsServer/userlogin

**参数：** username（用户名）、 pwd（密码）

**请求方式：** post

**返回结果：**

```
{"response":"succeed","msg":"登录成功"}
```

看效果：

![这里写图片描述](http://img.blog.csdn.net/20161223230102410?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 
上代码：

```
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
```

多个参数的情况加建议使用map对象构造然后addPatams即可。

###**4.4 文件下载**
**接口：**http://192.168.2.153:8080/MyHttpUtilsServer/g3box_uesr_2.3.1.apk

**参数：**无

**请求方式：** get

**返回结果：**无

代码：

```
  /**
     * 开始下载按钮单击事件
     *
     * @param view
     */
    public void onDownload(View view) {
        MyHttpUtils.build()
                .url("http://192.168.2.153:8080/MyHttpUtilsServer/g3box_uesr_2.3.1.apk")
                .setConnTimeOut(6000)//设置连接超时时间[可选，默认5s]
                .setReadTimeOut(5*60*1000)//设置读取超时时间[可选，默认30s]
                .onExecuteDwonload(new CommCallback() {
                    @Override
                    public void onSucceed(Object o) {
                        ToastUtils.showToast(DownloadActivity.this, "下载完成");
                    }
                    @Override
                    public void onFailed(Throwable throwable) {
                        ToastUtils.showToast(DownloadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }
                    @Override
                    public void onDownloading(long total, long current) {
                        System.out.println(total+"-------"+current);
                        tvProgress.setText(new DecimalFormat("######0.00").format(((double) current / total) * 100)+"%");//保留两位小数
                    }
                });
```

这里引入了**setConnTimeOut** ***（设置连接超时时间）***，由于下载大文件的话比较耗时读取时间是30s可能不够 所以可以通过**setReadTimeOut**设置长时间，比如5分钟=5*60*1000【两个方法传入的都是时间的毫秒值】

>**温馨提示：** myhttputils模式会下载文件到**sdcard/download**目录下面，如需修改，使用 
**setFileSaveDir("/sdcard/myapp")** 即可


效果图：

![这里写图片描述](http://img.blog.csdn.net/20161223230135958?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



 
###**4.5、文件上传**
**接口：** http://192.168.2.153:8080/MyHttpUtilsServer/upload

**参数：** 无

**请求方式：** 无

**返回结果：** 无


#### **4.5.1、单文件上传**


效果图：

![这里写图片描述](http://img.blog.csdn.net/20161223230253490?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

上代码：

```
/**
 * 单文件上传
 *
 * @param view
 */
public void onUpload(View view) {
    mProgressDialog.show();
    MyHttpUtils.build()
            .uploadUrl("http://192.168.2.153:8080/MyHttpUtilsServer/upload")
            .addFile("sdcard/download/wifi.exe")
            .onExecuteUpLoad(new CommCallback() {
                @Override
                public void onComplete() {
                    mProgressDialog.dismiss();
                    ToastUtils.showToast(UploadActivity.this,"上传完成");
                }

                @Override
                public void onSucceed(Object o) {

                }

                @Override
                public void onFailed(Throwable throwable) {
                    ToastUtils.showToast(UploadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                }
            });
}
```

 
>**温馨提示：**添加文件可以通过addFile(文件的绝对路径)和addFile(new File（文件绝对路径）)，还有就是这里使用uploadUrl而不是url来设置请求接口哦

####**4.5.2、多文件上传**
  
  多文件上传有两种方式，即**addFile多次**和**addFiles一个集合**，addFile就不演示了，下面演示addFiles

 上代码：

```
/**
 * 多文件上传
 *
 * @param view
 */
public void onUploadMult(View view) {
    mProgressDialog.show();
    List<File>files=new ArrayList<>();
    files.add(new File("sdcard/download/wifi.exe"));
    files.add(new File("sdcard/download/g3box_uesr_2.3.1.apk"));
    MyHttpUtils.build()
            .uploadUrl("http://192.168.2.153:8080/MyHttpUtilsServer/upload")
            .addFiles(files)
            .onExecuteUpLoad(new CommCallback() {
                @Override
                public void onComplete() {
                    mProgressDialog.dismiss();
                    ToastUtils.showToast(UploadActivity.this,"上传完成");
                }

                @Override
                public void onSucceed(Object o) {

                }

                @Override
                public void onFailed(Throwable throwable) {
                    ToastUtils.showToast(UploadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                }
            });
}
```

###**4.6、参数与文件同时上传**
**接口：**http://192.168.2.153:8080/MyHttpUtilsServer/string.action   和 
                http://192.168.2.153:8080/MyHttpUtilsServer/upload
                
**参数：** content

**请求方式：** get

效果：

![这里写图片描述](http://img.blog.csdn.net/20161223230331063?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 

代码：

```
/**
     * 参数与文件同时上传
     *
     * @param view
     */
    public void onUploadParamFile(View view) {
        MyHttpUtils.build()//构建myhttputils
                .url("http://192.168.2.153:8080/MyHttpUtilsServer/string.action")//请求的url
                .uploadUrl("http://192.168.2.153:8080/MyHttpUtilsServer/upload")
                .addParam("content", "abc")
                .addFile("sdcard/download/wifi.exe")
                .onExecute(new StringCallBack() {//开始执行，并有一个回调（异步的哦---->直接可以更新ui）
                    @Override
                    public void onSucceed(String result) {//请求成功之后会调用这个方法
                        ToastUtils.showToast(UploadActivity.this, "转换成功-------->" + result);
                    }
                    @Override
                    public void onFailed(Throwable throwable) {//请求失败的时候会调用这个方法
                        ToastUtils.showToast(UploadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }
                })
                .onExecuteUpLoad(new CommCallback() {
                    @Override
                    public void onComplete() {
                        mProgressDialog.dismiss();
                        ToastUtils.showToast(UploadActivity.this, "上传完成");
                    }
                    @Override
                    public void onSucceed(Object o) {
                    }
                    @Override
                    public void onFailed(Throwable throwable) {
                        ToastUtils.showToast(UploadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }
                });
    }
```

>参数与文件同时上传，其实就是请求的组合


----------


##**五、使用小技巧**

###**5.1、日志打印 **

为了方便调试，myhttputils还会在logcat中打印i级别的json字符串日志，如日志太多可添加HttpRequester为过滤条件，看图（是不是很容易就看出来了）：

![这里写图片描述](http://img.blog.csdn.net/20161223230417523?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


上面是请求正常的情况下打印，同样错误情况下也会有e级别日志------->下面这个是url地址错误时的错误信息

![这里写图片描述](http://img.blog.csdn.net/20161223230438070?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 




###**5.2、错误信息对照表**
如果你不想使用myhttputils提供的FailedMsgUtils.getErrMsgStr（）方法，你可以自己根据返回的throwable进行处理，下面是myhttputils可能会截获的异常对照表：

![这里写图片描述](http://img.blog.csdn.net/20161223230456649?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXExMzc3MjI2OTc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

 
###**5.3、关于请求参数的配置**
如果你觉得在一条链上set方法太多不太好，myhttputils还提供了HttpBody方法，先构造完了，通过setHttpBody即可。（这种方式需要你自己判断文件是否存在哦）

下面以上面下载的案例来调整：

```
 /**
     * 开始下载按钮单击事件
     *
     * @param view
     */
    public void onDownload(View view) {
        HttpBody body = new HttpBody();
        body.setUrl("http://192.168.2.153:8080/MyHttpUtilsServer/wifi.exe")
                .setConnTimeOut(6000)
                .setFileSaveDir("/sdcard/myapp")
                .setReadTimeOut(5 * 60 * 1000);
        MyHttpUtils.build()
                .setHttpBody(body)
                .onExecuteDwonload(new CommCallback() {
                    @Override
                    public void onSucceed(Object o) {
                        ToastUtils.showToast(DownloadActivity.this, "下载完成");
                    }
                    @Override
                    public void onFailed(Throwable throwable) {
                        ToastUtils.showToast(DownloadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }
                    @Override
                    public void onDownloading(long total, long current) {
                        System.out.println(total + "-------" + current);
                        tvProgress.setText(new DecimalFormat("######0.00").format(((double) current / total) * 100) + "%");//保留两位小数
                    }
                });
    }
```

##最后

如反馈良好，可以考虑详解源码，留言告诉我吧

demo、服务器代码和源码地址：https://github.com/huangdali/myhttputils

源码和文档下载地址：https://bintray.com/huangdali/myhttputils/myhttputils1.0/2.1.5
 
如有bug、新功能建议请留言，我一定以最快的速度回复。

>尊重原创，转载请注明出处: http://blog.csdn.net/qq137722697
