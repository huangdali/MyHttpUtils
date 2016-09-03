# 《Android网络请求篇》MyHttpUtils一个非常好用的异步网络请求框架
##一、能做什么
>你只需要传**url**，**JavaBean**就可以在**回调方法**里面得到想要的结果，你会发现你的代码里面没有了子线程、没有了handle，链式的变成使得代码更加清晰。

###1.1 功能

 1. 支持get、post请求；
 2. 支持http和https的协议；
 3. 支持设置连接、读取超时时间（可选）；
 4. 支持json格式的请求结果（无论json格式多复杂，都能搞定）；
 5.  支持传入JavaBean对象（解析之后的javabean对象）；
 6. 支持回调方法中反应传入javabean对象，这样可以在回调方法中直接拿到解析过后的javabean对象；
 7. 支持回调方法中更新UI（所以叫异步请求了）。  
 
>**说明**：java中一切皆对象，这里的JavaBean对象就是你请求接口之后返回的json数据所对应的实体。

###1.2 使用场景
大部分的网络请求都是返回json格式的数据，秉承java中一切皆对象的原则，这个json格式的数据必定对应一个JavaBean。你只要能通过json格式构造出相应的javabean对象（文章的最后会介绍如何快速构造JavaBean对象），那么用几行代码就可以帮你解析出来。（如果你的项目中不能使用Retrofit，OkHttp那么你是找对地方了）。所以，只要请求接口返回的数据格式是json的都可以用。
>备注：目前还不支持文件上传和下载，后续将跟进，敬请关注

##二、怎么用
**方法一**：gradle导入（**推荐**）
```
compile 'com.hdl:myhttputils:1.0'
```

**方法二**：导入arr包（上面方法失败的话就用这个吧）
进入项目的github主页，下载该项目，arr文件就在.\MyHttpUtils\myhttputils1.0\build\outputs\aar文件夹下面（[点击这里进入github](https://github.com/huangdali/MyHttpUtils)）。你要是嫌麻烦的话[点击这里直接下载arr文件](https://dl.bintray.com/huangdali/myhttputils1.0/com/hdl/myhttputils1.0/1.0/myhttputils1.0-1.0.aar)。

由于使用到了由于框架中使用到了gson，所以也需要加入gson的依赖。
```
compile 'com.google.code.gson:gson:2.2.4'
```
##三、应用举例
###3.1 get请求
下面通过一个查询ip地址信息的demo来介绍get方式的使用（先来看运行的效果图）：
![这里写图片描述](http://img.blog.csdn.net/20160902153934466)

上代码：
```
/**
     * 获取IP地址的监听事件
     *
     * @param view
     */
    public void onGetIP(View view) {
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=182.254.34.74";//请求的接口
        new MyHttpUtils()
                .url(url)//请求的url
                .setJavaBean(IPBean.class)//设置需要解析成的javabean对象
                .setReadTimeout(60000)//设置读取超时时间,不设置的话默认为30s(30000)
                .setConnTimeout(6000)//设置连接超时时间,不设置的话默认5s(5000)
                .onExecute(new CommCallback<IPBean>() {//开始执行异步请求,传入一个通用回调对象,泛型为返回的javabean对象
                    @Override
                    public void onSucess(IPBean ipBean) {//成功之后回调
                        KLog.e("测试:" + ipBean.toString());
                        Toast.makeText(MainActivity.this, ipBean.toString(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailed(String msg) {//失败时候回调
                        KLog.e(msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
```

方法及参数说明：
***-url（）：***设置请求的接口地址，参数类型为String。(**必填**)
***-setJavaBean（）：***设置解析之后的JavaBean对象，记得加.class。（**必填**）
***-onExecute（）：***设置开始请求（get）接口，请求结果在回调方法中，参数为CommCallback，可加泛型。（**必填**）
***-setReadTimeout（）：***设置读取超时时间（默认30s），参数为整型，单位：毫秒。（**可选**）
***-setConnTimeout（）：***设置连接超时时间（默认5s），参数为整型，单位：毫秒。（**可选**）

###3.2 post请求
下面通过一个获取用户备注、和跟踪信息的例子来说明post的用法（先看效果图）：
![这里写图片描述](http://img.blog.csdn.net/20160902154311596)

上代码：
```
  public void onGetRemark(View view) {
        String remarkUrl = "http://192.168.1.161：8080/Test/userInfoController/updateUser.action";
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", "7cf6871beeed856df47eb189");
        param.put("uid", "8011bddb58588ab54");
        new MyHttpUtils()
                .url(remarkUrl)
                .addParam(param)
                .setJavaBean(RemarkBean.class)
                .onExecuteByPost(new CommCallback<RemarkBean>() {
                    @Override
                    public void onSucess(RemarkBean remarkBean) {
                        Toast.makeText(MainActivity.this, remarkBean.toString(), Toast.LENGTH_SHORT).show();
                        KLog.e("解析出来的对象为:" + remarkBean.toString());
                    }
                    @Override
                    public void onFailed(String msg) {
                        KLog.e("错误消息:" + msg);
                    }
                });
    }
}
```

方法及参数说明：
***-url（）：***设置请求的接口地址，参数类型为String。(**必填**)
***-setJavaBean（）：***设置解析之后的JavaBean对象，记得加.class。（**必填**）
***-addParam():***设置post请求的参数,参数为hashmap类型。（**必填**）
***-onExecuteByPost（）：***设置开始请求（post）接口，请求结果在回调方法中，参数为CommCallback，可加泛型。（**必填**）
***-setReadTimeout（）：***设置读取超时时间（默认30s），参数为整型，单位：毫秒。（**可选**）
***-setConnTimeout（）：***设置连接超时时间（默认5s），参数为整型，单位：毫秒。（**可选**）

>通过上面的两个例子是不是觉得这个框架很好用，只需要传url，javabean就可以在回调方法里面得到想要的结果，你会发现你的代码里面没有了子线程、没有了handle，链式编程使得代码结构更加清晰。如果对Rxjava，Retrofit，OkHttp熟悉的朋友肯定觉得这种方式似曾相识，的确这种链式+回调有很多的好处。


##四、如何快速通过json构建javabean对象
>推荐一个非常好用的AS插件***GsonFormat***。（当然，你也可以通过http://www.jsonschema2pojo.org/直接生成javabean对象，不是很喜欢，因为没有GsonFormat好用）

###4.1 什么是GsonFormt
就是直接将json数据格式转换为javabean对象的as插件。
###4.2 安装步骤
settings-->Plugins—>输入GsonFormat—>Browse—>Install—>重启as即可
来个图看看：
![这里写图片描述](http://img.blog.csdn.net/20160902155517130)
###4.3 怎么用GsonFormat
1）、比如你请求接口之后，返回的json数据是：
```
{
  "username":"hdl",
  "pwd":"L23LK4J3LJLKJL436LKJKL7LJLGKK4"
}
```
2）、先新建一个与json数据对应类，这个类名随意，在类中使用快捷键***alt+Insert***（右键-->Generate也可以）会弹出一个对话框，第一个就是***GsonFormat***插件，打开之后让你***输入Json数据***，点击***确定***—>***确定***即可自动生成。最后实现***Serializable*** 接口即可（为了能更好地测试数据，你最后重写toString方法）。再来个图

![这里写图片描述](http://img.blog.csdn.net/20160902155956778)
3）、这样你就可以得到框架中setJavaBean（）中的JavaBean了。你只需要传url，javabean就可以在回调方法里面得到想要的结果，是不是很简单？。
> 有人可能会说这是简单的一个javabean对象，复杂的json怎么搞？带json数组的又咋搞。


那我可以负责的告诉你，方法一样的。只要你的json格式正确就能生成对应的javabean对象。来看一个复杂的json。（豆瓣Top250的电影，属性几十个呢）
下面是请求豆瓣排名第一的电影（只是一条哦）：https://api.douban.com/v2/movie/top250?start=0&count=1
![这里写图片描述](http://img.blog.csdn.net/20160902160231465)
是不是很长很长，用gsonformat管理多长照样搞定。***复制json—>粘贴—>确定—>实现Serializable接口***，四步搞定。 
![这里写图片描述](http://img.blog.csdn.net/20160902160409972)
>***温馨提示：***里面的属性名千万不要改哦，必须要跟json数据生成的保持一致。要获取list数据，通过类似于new javabean().getData()的方法就可以得到了。

##五、封装思路
使用讲完了，来讲讲怎么封装的吧（感兴趣的可以下载源码，共同学习）。封装过程其实很简单，用到了三个东西线程、handler、httpurlconnection，回调思想。
###5.1 回调思想
其他三个大家都懂，就只说说***回调思想***：说白了就是我调用某个方法，成功还是失败，你总得告诉我一声（是不是很直白）。来看看框架里面用到的通用回调对象怎么定义的。
```
public interface CommCallback<T> {
    void onSucess(T t);//成功了就调用，返回的类型得到的类型就是T（传入的JavaBean对象）
    void onFailed(String msg);//失败了就调用，接收的就是错误信息。
}
```
就是这么简单。
###5.2 get请求的封装
```
 try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(readTimeout);
                    conn.setConnectTimeout(connectTimeout);
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        int len = 0;
                        byte[] buf = new byte[1024 * 1024];
                        StringBuilder json = new StringBuilder();
                        while ((len = is.read(buf)) != -1) {
                            json.append(new String(buf, 0, len));
                        }
                        is.close();
                        Message msg = mHanler.obtainMessage();
                        msg.what = WHAT_REQSUCCESS;
                        msg.obj = json.toString();
                        mHanler.sendMessage(msg);
                    } else {
                        mHanler.sendEmptyMessage(WHAT_REQFAILED);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    mHanler.sendEmptyMessage(WHAT_URLFAILED);
                } catch (IOException e) {
                    mHanler.sendEmptyMessage(WHAT_IOFAILED);
                    e.printStackTrace();
                }
```
这简直就是标准的HttpurlConnection请求方式嘛。我就不一一说了。post请求类似。
###5.3 封装的经过
来说说为啥要使用HttpUrlConnection来封装。***一方面***，它是官方推荐的网络请求方式，但是请求过程代码太累赘（看看上面的代码就知道了），于是就像封装一下，用几行代码就搞定，提高工作效率；***还有一个原因***就是在我维护的一个项目中，之前的那个人用的是OKHttp2.X开发的，在新增功能的时候不想用okhttp2.X了，听过有bug。但是升级到okhttp3.X的话要改的又太多。于是我就想到了Retrofit，但是其实Retrofit底层也是用okhttp实现的，虽然导入retrofit依赖的时候不会报错，但是运行的时候就错了（估计是包冲突了），于是我又想到了以前用过的vollery和xUtils，额，还是算了，它们封装的东西太多了，我就简简单单的发个网络请求而已。怎么办呢，就只用httpurlconnection了，直接用这个呢要写的代码真的是太冗余了，于是就有了这个封装的网络请求框架了，基本上可以满足大部分的网络请求了，目前还不支持文件上传、下载，后续会跟进，敬请关注。
##六、下载地址
源码及demo下载地址：https://github.com/huangdali/MyHttpUtils
