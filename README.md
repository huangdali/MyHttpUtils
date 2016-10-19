>尊重原创，转载请注明出处：[原文查看惊喜更多](http://blog.csdn.net/qq137722697) http://blog.csdn.net/qq137722697

#《android网络请求框架》一个轻量级的异步网络请求框架MyHttpUtils（新版更新记录，版本号：2.X）
##一、前言
基本的用法在[《Android网络请求篇》MyHttpUtils一个非常好用的异步网络请求框架](http://blog.csdn.net/qq137722697/article/details/52414372)中有过详细的介绍。下面是对2.x版的详细使用介绍，对源码感兴趣的伙伴可以移步[github](https://github.com/huangdali/myhttputils)了解更多的消息。适用场景：1、项目中应该各种原因不能适用okhttp、retrofit的，2、想摆脱Volley、xUtils（功能太多，很多用不上）的，3、学习使用的。看过源码的伙伴应该知道MyHttpUtils底层就是通过HtttpUrlConnection实现的，用Android亲儿子实现的，不需要添加任何第三方的库。
##二、功能介绍
1、支持get、post请求；

2、支持http和https的协议；

3、支持设置连接、读取超时时间（可选）；

4、支持json格式的请求结果（无论json格式多复杂，都能搞定）；

5、支持传入JavaBean对象（解析之后的javabean对象）；

6、支持回调方法中反应传入javabean对象，这样可以在回调方法中直接拿到解析过后的javabean对象；

7、支持回调方法中更新UI（所以叫异步请求了）；

（------------------上面是1.X版本的功能，下面是2.x版本新增的功能---------------）

***8、支持文件下载；（必须得带下载进度回调呀）***

***9、支持单文件上传；***

***10、支持多文件上传。***
>MyHttpUtils满足了大部分的网络请求了，而且非常轻量级哦

##三、使用方法
使用gradle添加依赖：
```
 compile 'com.huangdali:myhttputils:2.0.2'
```

当然了网络权限可别忘了加哦
```
 <uses-permission android:name="android.permission.INTERNET" />
```

文件上传和下载也需要添加权限（不要这个功能的就不要加了）
```
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
##四、get请求
对于get请求，如果有请求的参数，直接拼接到接口的后边即可。

下面通过一个查询ip地址信息的demo来介绍get方式的使用（先来看运行的效果图）：

![博客](http://img.blog.csdn.net/20160902153934466)
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
                .onExecute(new CommCallback<IPBean>() {//异步,泛型为解析后的javabean对象
                    @Override
                    public void onSucess(IPBean ipBean) {//成功之后回调                      
                        ToastUtils.showMsg(MainActivity.this,ipBean.toString());
                    }
                    @Override
                    public void onFailed(String msg) {//失败时候回调
                        Log.e("MyHttpUtilsDemo",msg);                       
                    }
                });
    }
```
方法及参数说明：

***1、url（）：***设置请求的接口地址，参数类型为String。(**必填**)

***2、setJavaBean（）：***设置解析之后的JavaBean对象，记得加.class。（**必填**）

***3、onExecute（）：***设置开始请求（get）接口，请求结果在回调方法中，参数为CommCallback，可加泛型。（**必填**）

***4、setReadTimeout（）：***设置读取超时时间（不设置时默认30s），参数为整型，单位：毫秒。（**可选**）

***5、setConnTimeout（）：***设置连接超时时间（不设置时默认5s），参数为整型，单位：毫秒。（**可选**）

>**特别说明**：如果请求参数带有中文的，最好将该字符串编码（UTF-8）再拼接。
        `String text="";
        text= URLEncoder.encode(text,"UTF-8");`\\\会抛一个异常哦


##五、post请求

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
                    public void onSucess(RemarkBean remarkBean) {//成功之后回调
                       ToastUtils.showMsg(MainActivity.this,ipBean.toString());                      
                    }
                    @Override
                    public void onFailed(String msg) {//失败时候回调
                       Log.e("MyHttpUtilsDemo",msg);   
                    }
                });
    }
}
```

方法及参数说明：

***1、url（）：***设置请求的接口地址，参数类型为String。(**必填**)

***2、setJavaBean（）：***设置解析之后的JavaBean对象，记得加.class。（**必填**）

***3、addParam():***设置post请求的参数,参数为hashmap类型。（**必填**）

***4、onExecuteByPost（）：***设置开始请求（post）接口，请求结果在回调方法中，参数为CommCallback，可加泛型。（**必填**）

***5、setReadTimeout（）：***设置读取超时时间（不设置即为默认30s），参数为整型，单位：毫秒。（**可选**）

***6、setConnTimeout（）：***设置连接超时时间（不设置即为默认5s），参数为整型，单位：毫秒。（**可选**）


>通过上面的两个例子是不是觉得这个框架很好用，只需要传url，javabean就可以在回调方法里面得到想要的结果，你会发现你的代码里面没有了子线程、没有了handle，链式编程使得代码结构更加清晰。如果对Rxjava，Retrofit，OkHttp熟悉的朋友肯定觉得这种方式似曾相识吧。
##六、如何快速通过json构建javabean对象
说这么多，你可能会自己慢慢敲javabean对象，看本节标题就知道要干嘛了，这节讲教你如何快速通过json构建javabean对象（知道的略过）。

本节已经单独抽取为一篇博客（因为好几篇博客都要讲解这个），请查看[\[android插件篇\]如何快速通过json构建javabean对象(GsonFormat使用教程)](http://blog.csdn.net/qq137722697/article/details/52852804)  http://blog.csdn.net/qq137722697/article/details/52852804

##七、文件下载
这里我用自己搭的本地服务器接口作为演示：上代码
```      
        String url = "http://192.168.0.107:8080/UpLoadDemo/fg.exe";
        new MyHttpUtils()
                .url(url)
                .setFileSavePath("/sdcard/downloadtest")//不要这里只是填写文件保存的路径，不包括文件名哦
                .setReadTimeout(5 * 60 * 1000)//由于下载文件耗时比较大，所以设置读取时间为5分钟
                .downloadFile(new CommCallback<String>() {
                    @Override
                    public void onSucess(String msg) {
                        ToastUtils.showMsg(MainActivity.this, msg);
                    }

                    @Override
                    public void onFailed(String s) {

                    }

                    /**
                     * 可以重写进度回调方法
                     * @param total
                     * @param current
                     */
                    @Override
                    public void onDownloading(long total, long current) {
                        tvProgress.setText("当前进度：" + new DecimalFormat("######0.00").format(((double) current / total) * 100) + "%");
                    }
                });
```

看效果：

![这里写图片描述](http://img.blog.csdn.net/20161018230528084)

##八、文件上传

文件上传---支持单文件和多文件上传。不过在上传之前先给出一个用于接收文件的服务代码（java写的）

###8.1、服务端代码(已经有文件接收接口的略过)

服务端代码是一个非常简单的servlet，用了commons-fileupload来做文件接收。[jar包在这里下载](http://download.csdn.net/detail/qq137722697/9657409)
```
public class UpLoadServlet extends HttpServlet {
	private static final long serialVersionUID = -8705046949443366079L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("请求了。。。。。。。。");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
//		String userId = request.getParameter("userid");
		// 创建文件项目工厂对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置文件上传路径
		File uploadDir = new File(this.getServletContext().getRealPath(
				"/upload/"));// 设置文件上传的路径为项目名/upload/userid/
		if (!uploadDir.exists()) {// 如果改文件夹不存在就创建
			uploadDir.mkdirs();
		}
		// 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
		String temp = System.getProperty("java.io.tmpdir");
		// 设置缓冲区大小为 5M
		factory.setSizeThreshold(1024 * 1024 * 5);
		// 设置临时文件夹为temp
		factory.setRepository(new File(temp));
		// 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		try {
			List<FileItem> list = servletFileUpload.parseRequest(request);
			for (FileItem fileItem : list) {
				File file = new File(uploadDir, fileItem.getName());
				if (!file.exists()) {// 文件不存在才创建
					fileItem.write(file);// 保存文件
				}
			}
			pw.write("{\"message\":\"上传成功\"}");
			System.out.println("{\"message\":\"上传成功\"}");
		} catch (Exception e) {
			pw.write("{\"message\":\"上传失败\"}");
			System.out.println("{\"message\":\"上传失败\"}");
		}
	}
}
```
###8.2、单文件上传

下面以上传sd卡中的一张index.png（文件大小342KB）图片的demo演示当文件上传。上代码:
```
new MyHttpUtils()
                .url("http://192.168.0.107:8080/UpLoadDemo/upload")
                .setJavaBean(UploadResultBean.class)
                .addUploadFile(new File("/sdcard/index.png"))//设置需上传文件
                .uploadFile(new CommCallback<UploadResultBean>() {
                    @Override
                    public void onSucess(UploadResultBean uploadResultBean) {
                        ToastUtils.showMsg(MainActivity.this, uploadResultBean.getMessage());
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.showMsg(MainActivity.this, msg);
                    }
                });
```

看效果：

![这里写图片描述](http://img.blog.csdn.net/20161018232647737)

###8.3、多文件上传
上传两个demo.exe(8M)、mylog.png(247K)文件，上代码：
```
ArrayList<File>fileList=new ArrayList<>();//文件列表
        fileList.add(new File("/sdcard/demo.exe"));
        fileList.add(new File("/sdcard/mylog.png"));
        new MyHttpUtils()
                .url("http://192.168.0.107:8080/UpLoadDemo/upload")
                .setJavaBean(UploadResultBean.class)
                .addUploadFiles(fileList)//设置需上传的多个文件
                .uploadFileMult(new CommCallback<UploadResultBean>() {
                    @Override
                    public void onSucess(UploadResultBean uploadResultBean) {
                        ToastUtils.showMsg(MainActivity.this, uploadResultBean.getMessage());
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.showMsg(MainActivity.this, msg);
                    }
                });
```

看效果：

![这里写图片描述](http://img.blog.csdn.net/20161018234111648)


##九、下载地址

源码及demo下载地址：https://github.com/huangdali/MyHttpUtilsDemo（欢迎star）


>访问我的博客主页了解更多知识：http://blog.csdn.net/qq137722697


----------


>访问我的github主页了解更多开源框架：https://github.com/huangdali
