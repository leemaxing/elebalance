package org.jdiy.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jdiy.core.ex.JDiyUnSupportException;

import android.annotation.SuppressLint;

import com.liicon.utils.log.LLog;
import com.liicon.utils.version.SdkVersion;
import com.liicon.utils.version.SdkVersionCodes;

/**
 * JDiy URL地址资源操作工具类.
 * <p/>
 * 此类主要处理URL资源相关的信息, 包括地址中的参数变量设置、获取；
 * 网页POST/GET请求、URL资源抓取/保存,获取URL资源请求响应头等等.
 *
 * @author 子秋(ziquee)  http://www.jdiy.org
 */
public class Url {
    private String url;
    private String bookmark = "";
    private String charset;
    private Map<String, String> postData;
    private Map<String, String> headData;

    private boolean connected = false;
    private Map<String, List<String>> responseHeadData;
    private String responseHtml;

    @SuppressWarnings("unused")
    private Url() {
    }

    /**
     * 以默认的utf-8编码的形式构建一个Url类的实例.
     *
     * @param url 要处理的URL资源地址．（必须是包含协议名称(如:http://)的绝对地址）.
     * @see #Url(String, String) 。
     */
    public Url(String url) {
        this(url, "utf-8");
    }

    /**
     * 以指定的编码构建一个Url类的实例.
     *
     * @param url     要处理的URL资源地址. （必须是包含协议名称(如:http://)的绝对地址）.
     * @param charset 该URL资源的编码类型(如果为空字符串或null,将使用默认的utf-8编码)
     * @see #Url(String)
     */
    public Url(String url, String charset) {
        if (url == null) {
            this.url = "./";
        } else {
            int at = url.indexOf("#");
            if (at == -1) {
                this.url = url;
            } else {
                bookmark = url.substring(at);
                this.url = url.substring(0, at);
            }
            if (charset == null || "".equals(charset = charset.trim())) charset = "utf-8";
            this.charset = charset;
        }
    }

    /**
     * 下载URL资源并保存为文件. <br />
     * 例如：<br />
     * new Url("http://www.jdiy.org/").download(new File("c:/jdiy.html"));  //下载保存JDiy网站首页.<br />
     * 又如:<br />
     * new Url("http://www.jdiy.org/img/logo.gif").download(new File("c:/jdiy-logo.gif"));//下载图片资源.<br/>
     *
     * @param file 要保存的文件.
     * @return File 下载保存之后的文件对象。
     * @throws java.io.IOException 如果指定的URL资源不可访问,将报出此异常.
     * @see #getHtml()
     */
    @SuppressWarnings("unused")
    public File download(File file) throws IOException {
        LLog.i(this.toString() + " > " + file.getAbsolutePath());
        long bTime = System.currentTimeMillis();
        URL urlX = new URL(this.toString());
        BufferedInputStream bis = new BufferedInputStream(urlX.openStream());
        byte[] bytes = new byte[4*1024];//4k buffer
        FileOutputStream bos = new FileOutputStream(file);
        int len;
        while ((len = bis.read(bytes)) > 0) {
            bos.write(bytes, 0, len);
        }
        bis.close();
        bos.flush();
        bos.close();
        long time = System.currentTimeMillis() - bTime;
        LLog.i(time + " ms. OK");
        return file;
    }


    /**
     * 将URL资源读取并输出到指定的(OutputStream)输出流.  <br/>
     * 即以二进制的方式输出，例如读取远程图片并作为本地的请求响应结果输出： <br/>
     * new Url("http://www.abc.com/xxx.jpg").outTo(response.getOutputStream());
     * @param out　输出流.
     * @exception java.io.IOException 如果发生 I/O 错误.
     * @since 这是JDiy-2.1及后续版本新增的方法.
     */
    @SuppressWarnings("unused")
    public void outTo(OutputStream out) throws IOException {
        URL url = new URL(this.toString());
        InputStream in = url.openStream();
        byte[] buf = new byte[4 * 1024];  // 4K buffer
        int bytesRead;
        while ((bytesRead = in.read(buf)) != -1) {
            out.write(buf, 0, bytesRead);
        }
    }

    /**
     * 将URL地址资源读取并输出到Writer流 (例如一个PrintWriter 或 JspWriter) <br/>
     * 即以字符串的方式输出，例如读取远程地址的内容输出到本地的响应结果中(可结合js做ajax跨域请求)： <br/>
     * new Url("http://www.abc.com/xxx.do?xxx=xxx").outTo(response.getWriter());
     *
     * @param out 要输出到的 Writer内容.
     * @exception java.io.IOException 如果发生 I/O 错误.
     * @since 这是JDiy-2.1及后续版本新增的方法.
     */
    @SuppressWarnings("unused")
    public void outTo(Writer out) throws IOException {
        URL url = new URL(this.toString());
        URLConnection con = url.openConnection();
        con.connect();
        String encoding = con.getContentEncoding();

        // Construct a Reader appropriate for that encoding
        BufferedReader in;
        if (encoding == null) {
            in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
        }
        else {
            in = new BufferedReader(
                    new InputStreamReader(url.openStream(), encoding));
        }
        char[] buf = new char[4 * 1024];  // 4K char buffer
        int charsRead;
        while ((charsRead = in.read(buf)) != -1) {
            out.write(buf, 0, charsRead);
        }
    }

    /**
     * 从URL地址中删除一个或多个参数变量. <br />
     * 注：参数变量是指跟在URL地址字符串问号(?)后面的查询参数.
     *
     * @param args 要删除的参数变量的名称，如果要删除多个变量，也可以只传入一个字符串参数（要删除的变量名称之间用逗号，分号或空隔分隔即可)。
     *             <br /><br />
     *             下面是几种用法的示例性代码:
     *             <ul>
     *             <li>url.del("page");//从url对象中删除page变量.</li>
     *             <li>url.del("page", "key","id");//从url对象中删除page, key和id. </li>
     *             <li>url.del("page key,id");//注意这种写法，与上行效果一样.</li>
     *             </ul>
     * @return Url 当前的Url对象
     * @see #get(String)
     * @see #set(String, String)
     */
    public Url del(String... args) {
        for (String names : args) {
            List<String> nameA = Txt.split(names.replaceAll(" ", ",").replaceAll(";", ","), ",");
            for (String n : nameA) {
                if (n == null || n.equals("")) continue;
                String s = n + "=";
                if (url.contains("?" + s)) {
                    int iEnd = url.indexOf("&", url.indexOf("?" + s));
                    if (iEnd == -1) url = url.substring(0, url.indexOf("?"));
                    else url = url.substring(0, url.indexOf("?") + 1) + url.substring(iEnd + 1);
                    connected = false;

                } else if (url.contains("&" + s)) {
                    int iEnd = url.indexOf("&", url.indexOf("&" + s) + s.length() + 1);
                    if (iEnd == -1) url = url.substring(0, url.indexOf("&" + s));
                    else url = url.substring(0, url.indexOf("&" + s)) + url.substring(iEnd);
                    connected = false;
                }
            }
        }
        return this;
    }

    /**
     * 获取URLConnection已设置的请求头部信息. <br/>
     * 注意是刚设置过的请求头,而非响应头，如要获取响应头信息请使用{@link #getResponseHeader(String)}方法.
     *
     * @param name 请求头名称(不区分大小写)
     * @return 请求头值
     * @see #setHeader(String, String)
     * @see #getResponseHeader(String)
     * @see #getResponseHeaders()
     */
    @SuppressWarnings("unused")
    public String getHeader(String name) {
        if(name==null) return headData.get(null);
        for(String s: headData.keySet()){
            if(s.toLowerCase().equals(name)) return headData.get(s);
        }
        return null;
    }

    /**
     * 在发送URL请求之前，预先设置URLConnection请求头部信息.
     *
     * @param name  请求头名称
     * @param value 请求头值
     * @return 当前的Url对象本身.
     * @see #getHeader(String)
     * @see #getResponseHeader(String)
     */
    public synchronized Url setHeader(String name, String value) {
        if (headData == null) {
            headData = new java.util.HashMap<String, String>();
        }
        headData.put(name, value);
        connected = false;
        return this;
    }


    /**
     * 获取URL中的指定参数变量的值. <br />
     * 注：参数变量是指跟在URL地址字符串问号(?)后面的查询参数.
     *
     * @param name 要获取的变量值，如果该变量不存在，将返回null. 如果该变量存在但未设置其值，将返回""
     * @return String 要获取的变量字符串名称。
     * @see #set(String, String)
     * @see #del(String...)
     */
    public String get(String name) {
        String rtn;
        String s = name + "=";
        String b;
        if (url.contains("?" + s)) b = "?" + s;
        else if (url.contains("&" + s)) b = "&" + s;
        else return null;
        int iEnd = url.indexOf("&", url.indexOf(b) + b.length());
        rtn = iEnd == -1 ? url.substring(url.indexOf(b) + b.length()) : url.substring(url.indexOf(b) + b.length(), iEnd) + "";
        try {
            return java.net.URLDecoder.decode(rtn, charset);
        } catch (UnsupportedEncodingException e) {
            throw new JDiyUnSupportException("EE0036", charset);
        }
    }

    /**
     * 获取URL地址中的锚信息. 如果未找到锚,将返回空字符串("").
     *
     * @return String 锚字符串（“锚”是指URL地址“#”后面的字符）
     * @see #setAnchor(String)
     */
    @SuppressWarnings("unused")
    public String getAnchor() {
        return bookmark.replaceAll("^#+", "");
    }

    /**
     * 设置URL地址中的锚信息.
     *
     * @param anchor 锚字符串（“锚”是指URL地址“#”后面的字符）
     * @return 当前的Url对象本身.
     * @see #getAnchor()
     * @since 这是JDiy-1.9及后续版本新增的方法.
     */
    public Url setAnchor(String anchor) {
        bookmark = anchor == null || "".equals(anchor.trim()) ? "" : "#" + anchor.trim().replaceAll("^#+", "");
        connected = false;
        return this;
    }

    /**
     * 添加将要以POST方式发送到URL地址的数据. <br />
     *
     * @param name  要发送数据的变量名称.
     * @param value 要发送数据的字符串值. 该值将以当前Url对象指定的charset进行编码并发送.
     * @return 当前的Url对象本身.
     * @see #set(String, String)
     * @see #getPost()
     * @see #getHtml()
     * @see #connect()
     */
    public synchronized Url post(String name, String value) {
        if (postData == null) postData = new java.util.HashMap<String, String>();
        try {
            postData.put(name, java.net.URLEncoder.encode(value, charset));
        } catch (UnsupportedEncodingException e) {
            throw new JDiyUnSupportException("EE0036", charset);
        }
        connected = false;
        return this;
    }

    /**
     * 添加或修改URL地址中的参数变量的值. <br />
     * 如果指定的参数变量存在，将修改其值，如果不存在，将添加该参数变量. <br />
     * 注：参数变量是指跟在URL地址字符串问号(?)后面的查询参数.
     *
     * @param name  要设置或添加的参数变量名称.
     * @param value 要设置的参数变量的值.
     * @return 当前的Url对象本身.
     */
    public Url set(String name, String value) {
        try {
            value = java.net.URLEncoder.encode(value, charset);
        } catch (UnsupportedEncodingException e) {
            throw new JDiyUnSupportException("EE0036", charset);
        }
        String s = name + "=";
        String b = null;
        if (url.contains("?" + s)) b = "?" + s;
        else if (url.contains("&" + s)) b = "&" + s;
        if (b == null) {
            url += (!url.contains("?") ? "?" : "&") + s + value;
        } else {
            int iEnd = url.indexOf("&", url.indexOf(b) + b.length());
            if (iEnd == -1) url = url.substring(0, url.indexOf(b) + b.length()) + value;
            else url = url.substring(0, url.indexOf(b) + b.length()) + value + url.substring(iEnd);

        }
        connected = false;
        return this;
    }

    /**
     * 添加或修改URL中的变量的(整型)值. <br />
     * 如果指定的变量存在，将修改其值，如果不存在，将添加该变量. <br />
     * 注：参数变量是指跟在URL地址字符串问号(?)后面的查询参数.
     *
     * @param name  要设置或添加的变量名称.
     * @param value 要设置的变量的整型值.
     * @return Url 当前的Url对象。
     * @see #set(String, String)
     */
    public Url set(String name, int value) throws JDiyUnSupportException {
        return set(name, String.valueOf(value));
    }

    /**
     * 用于设置URL地址的文件路径信息. 即改变URL的问号(?)前面的字符串. <br />
     * 注意:URL文件路径必须是包含协议名称(如:http://)的绝对URL地址.
     *
     * @param fileVPath 要设置的URL文件路径信息.如果该参数中含有问号(?),将取问号前面的字符串进行操作.
     * @return Url 当前的Url对象。
     */
    @SuppressWarnings("unused")
    public Url setF(String fileVPath) {
        if (fileVPath.contains("?")) fileVPath = fileVPath.substring(0, fileVPath.indexOf("?"));
        url = !url.contains("?") ? fileVPath : fileVPath + url.substring(url.indexOf("?"));
        connected = false;
        return this;
    }

    /**
     * 获取URL请求响应返回的HTML文本数据(即抓取网页内容). <br/><br />
     * <strong>提示:</strong><br/>
     * 　　这儿所说的返回"HTML文本",并不代表此方法只能抓取网页代码,
     * 对于其它任何只要是可以字符串化的资源都可以抓取到,例如:CSS样式资源, js脚本资源, XML, 普通文本文件等.<br />
     * 例如获取某个脚本资源的内容: <br/>String str = new Url("http://www.jdiy.org/JDiy_bin/js/class/JSer.js").getHtml();<br/><br/>
     * <strong>发送请求的方式</strong>：<br />
     * 　　如果在调用此方法之前已经调用过{@link #post(String, String)}方法设置过Post数据,那么系统将以POST方式发送请求,
     * 否则将使用默认的GET方式发送请求. <br/><br/>
     * <strong>特别说明:</strong><br />
     * 　　由于{@link #getHtml()}, {@link #getResponseHeader(String)}, {@link #getResponseHeaders()}
     * 这些方法都需要发送URL连接请求(Request)并等待响应(Response)之后才能得到我们需要的数据,
     * 而这种操作是很费时的一件事情.<br />
     * 　　因此,针对同一个Url对象,用户在多次调用上述方法时,JDiy并不会每次都建立新的连接,
     * 而是仅在首次调用(上述方法)时；或者当Url对象状态值发生改变(即通过相关set方法修改了Url对象的请求头,或Post/Get参数)后,
     * 才会再次发送一个连接请求.<br />
     * 　　如果您想在每次调用上述法时,都产生新的URL连接,请在调用这些方法之前先显式的调用{@link #connect()}方法.
     *
     * @return String 所请求URL资源的HTML内容.
     *         如果返回的内容出现乱码字符,请按下面方式查找原因:
     *         <ol>
     *         <li>检查构造此Url对象时所使用的charset参数值是否与所请求的URL资源所使用的编码一致.</li>
     *         <li>检查资源的类型(Content-Type)是否可以被串化(例如请求的资源为一个图片,则调用此方法肯定会乱码.)</li>
     *         </ol>
     * @throws java.io.IOException 如果指定的URL资源不可访问,将报出此异常.
     * @see #download(java.io.File)
     */
    public String getHtml() throws IOException {
        net();
        return responseHtml;
    }

    /**
     * 获取URL资源的请求结果响应头部数据. <br/>
     * <strong>示例:</strong><br />
     * Url u = new Url("http://www.jdiy.org/");<br/>
     * System.out.println(u.getResponseHeader("Server"));<span style="color:gray">//获取JDiy官网的服务器名称.</span> <br/><br />
     * <strong>发送请求的方式</strong>：<br />
     * 　　如果在调用此方法之前已经调用过{@link #post(String, String)}方法设置过Post数据,那么系统将以POST方式发送请求,
     * 否则将使用默认的GET方式发送请求. <br/><br/>
     * <strong>特别说明:</strong><br />
     * 　　由于{@link #getHtml()}, {@link #getResponseHeader(String)}, {@link #getResponseHeaders()}
     * 这些方法都需要发送URL连接请求(Request)并等待响应(Response)之后才能得到我们需要的数据,
     * 而这种操作是很费时的一件事情.<br />
     * 　　因此,针对同一个Url对象,用户在多次调用上述方法时,JDiy并不会每次都建立新的连接,
     * 而是仅在首次调用(上述方法)时；或者当Url对象状态值发生改变(即通过相关set方法修改了Url对象的请求头,或Post/Get参数)后,
     * 才会再次发送一个连接请求.<br />
     * 　　如果您想在每次调用上述法时,都产生新的URL连接,请在调用这些方法之前先显式的调用{@link #connect()}方法.
     *
     * @param name 指定要获取的响应头变量名称(大小写敏感).
     * @return 该响应头的字符串值. 如果指定的响应头不存在,则返回null.
     * @throws java.io.IOException 如果指定的URL资源不可访问,将报出此异常.
     * @see #getResponseHeaders()
     * @see #getHeader(String)
     * @see #setHeader(String, String)
     * @since 这是JDiy-1.10及后续版本新增的方法.
     */
    public String getResponseHeader(String name) throws IOException {
        net();
        List<String> r = responseHeadData.get(name);
        return r == null || r.size() < 1 ? null : r.get(0);
    }

    /**
     * 获取URL资源的全部响应头内容. <br/>
     * <strong>说明:</strong><br/>
     * 　　此方法返回的是一个Map对象. Map对象的键名对应每个响应头的名称； <br/>
     * 　　因同一名称的响应头,可能存在多个值项
     * (例如在java web开发中,javax.servlet.http.HttpServletResponse.addHeader(String,String)
     * 方法便可添加多项同名的响应头值.) <br/>
     * 因此,Map对象的键值是一个List对象,分别记录这些值项. (不过在通常情况下,键值List中仅有一个元素).<br/> <br/>
     * <strong>示例:</strong><br/>
     * <span style="color:gray">(下面示例演示了如何输出全部的URL响应头内容) </span><pre>
     * Url u = new Url("http://www.jdiy.org/");
     * Map&lt;String, List&lt;String&gt;&gt; headers = u.getResponseHeaders();
     * for (String name : headers.keySet()) {
     *     List&lt;String&gt; values = headers.getConnection(name);
     *     String value = values.size() == 1 ? values.getConnection(0) : values.toString();
     *     System.out.println(name + " = " + value);
     * }</pre>
     *
     * @return Map&lt;String, List&lt;String&gt;&gt; 见上面的说明.
     * @throws java.io.IOException 如果指定的URL资源不可访问,将报出此异常.
     * @see #getResponseHeader(String)
     * @see #getHtml()
     * @see #setHeader(String, String)
     * @see #getHeader(String)
     * @since 这是JDiy-1.10及后续版本新增的方法.
     */
    public Map<String, List<String>> getResponseHeaders() throws IOException {
        net();
        return responseHeadData;
    }

    /**
     * 指示程序将向URL资源发送连接请求. <br />
     * 　　在首次调用{@link #getHtml()}, {@link #getResponseHeader(String)}, {@link #getResponseHeaders()}
     * 这些方法时,程序会自动发送连接请求. 因此,通常情况下您并不需要调用当前这个方法来指示URL连接. 只有在获取数据时,
     * 您需要让程序每次都重新发送连接请求时,才需要用到此方法(详情请参见{@link #getHtml()}中的特别说明.<br/><br/>
     * <strong>示例:</strong><br/>
     * 1. 下面示例性的代码演示了每次获取网页内容时,都将重新向URL资源请求连接: <br/> <i>
     * Url u = new Url("http://www.jdiy.org/");<br/>
     * String str1 = u.getHtml();<span style="color:gray">//首次请求</span><br/>
     * String str2 = u.connect().getHtml();<span style="color:gray">//第二次请求</span>  <br/>
     * String str3 = u.connect().getHtml();<span style="color:gray">//第三次请求</span> <br/>
     * u.set("key","test");<span style="color:gray">//Url对象的状态发生改变</span><br/>
     * String str4 = u.getHtml();<span style="color:gray">//第四次请求</span> </i> <br/><br/>
     * 2. 而下面的代码(在Url对象状态发生改变之前)多次获取资源内容时,只会向URL资源请求一次:<br/><i>
     * Url u = new Url("http://www.jdiy.org/");<br/>
     * String str1 = u.getHtml();<span style="color:gray">//首次访问,发送请求</span><br/>
     * String str2 = u.getHtml();<span style="color:gray"></span>  <br/>
     * String str3 = u.getHtml();<span style="color:gray"></span> <br/>
     * u.set("key", "test");<span style="color:gray">//Url对象的状态发生改变</span><br/>
     * String str4 = u.getHtml();<span style="color:gray">//状态改变之后请求一次</span>
     * </i><br/><br/>
     * 　　请注意上面两个示例性的代码,程序实际请求次数的区别,对于示例1,总共产生了四次请求；
     * 而示例2,只产生了两次(即程序仅在对象状态改变时请求一次).
     *
     * @return 当前的Url对象本身.
     * @see #getHtml()
     * @see #getResponseHeader(String)
     * @see #getResponseHeaders()
     * @since 这是JDiy-1.10及后续版本新增的方法.
     */
    public Url connect() {
        connected = false;
        return this;
    }

    /**
     * 返回将要(或者已经)向URL地址POST过去的数据内容.
     *
     * @return 形如：name1=value1&name2=value2 ... 格式组成的字符串.
     *         其中的value值已经根据构造方法所指定的charset进行了编码处理.
     * @see #post(String, String)
     */
    public String getPost() {
        if (postData == null) return "";
        String s = postData.toString().replaceAll(", ", "&");
        return s.substring(1, s.length() - 1);
    }

    /**
     * 返回当前Url对象所表示的URL地址字符串.
     *
     * @return String URL地址字符串.
     */
    @Override
    public String toString() {
        return url + bookmark;
    }

    @SuppressLint("NewApi")
	private synchronized void net() throws IOException {
        if (connected) return;

        URLConnection conn;
        String address = this.toString();
        if(address.toLowerCase().startsWith("https://")){
            SSLSocketFactory sf=null;
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                        new java.security.SecureRandom());
                sf = sc.getSocketFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
            URL console = new URL(address);
            HttpsURLConnection conn1 = (HttpsURLConnection) console.openConnection();
            conn1.setSSLSocketFactory(sf);
            conn1.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn=conn1;
        }else{
            URL urlX = new URL(address);
            conn = urlX.openConnection();
        }

        conn.setUseCaches(false);
        conn.setDoInput(true);
        long bTime = System.currentTimeMillis();
        if (headData != null) for (String k : headData.keySet()) conn.addRequestProperty(k, headData.get(k));
        if (postData != null) {
        	LLog.i("POST " + address + " " + getPost());
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), charset);
            out.write(getPost());
            out.flush();
            out.close();
        } else {
        	LLog.i("GET " +address);
        }
        InputStream in = null;
        String ce = conn.getHeaderField("Content-Encoding");

        try {
            if ("gzip".equals(ce)){
            	in = new GZIPInputStream(conn.getInputStream());
        	} else if ("deflater".equals(ce)) {
            	if (SdkVersion.isSupport(SdkVersionCodes.API_9)) {
            		in = new java.util.zip.DeflaterInputStream(conn.getInputStream());
            	} else {
            		in = new com.common.utils.zip.DeflaterInputStream(in);
            	}
            }
        } catch (Exception ignore) {
            in = conn.getInputStream();
        }
        if (in == null) in = conn.getInputStream();

        StringBuilder s = new StringBuilder();
        String rLine;
        BufferedReader bReader = new BufferedReader(new InputStreamReader(in, charset));

        while ((rLine = bReader.readLine()) != null) if (rLine.length() > 0) s.append(rLine).append(System.getProperty("line.separator"));
        in.close();

        responseHtml = s.toString();
        responseHeadData = conn.getHeaderFields();
        connected = true;
        long time = System.currentTimeMillis() - bTime;
        LLog.i(time + " ms. " + conn.getHeaderField(null));
    }



    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
