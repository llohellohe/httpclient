###基本的接口和类

####HttoClient
接口`HttpClient`，它只表示HTTP 请求执行的基础部分，并不限制请求的处理细节，而是将这些特殊的细节交给具体的实现。
定义的方法有：

1.	 HttpParams getParams();
2.	 ClientConnectionManager getConnectionManager();
3.	 HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException;
4.	 HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException, ClientProtocolException;
5.	 HttpResponse execute(HttpHost target, HttpRequest request) throws IOException, ClientProtocolException;
6.	 HttpResponse execute(HttpHost target, HttpRequest request,HttpContext context)throws IOException, ClientProtocolException;
7.	 <T> T execute(HttpUriRequest request,ResponseHandler<? extends T> responseHandler)throws IOException, ClientProtocolException;
8.	 <T> T execute(
            HttpUriRequest request,
            ResponseHandler<? extends T> responseHandler,
            HttpContext context)
        throws IOException, ClientProtocolException;
9.	<T> T execute(
            HttpHost target,
            HttpRequest request,
            ResponseHandler<? extends T> responseHandler)
        throws IOException, ClientProtocolException;
10.	<T> T execute(
            HttpHost target,
            HttpRequest request,
            ResponseHandler<? extends T> responseHandler,
            HttpContext context)
        throws IOException, ClientProtocolException;
        

####HttpContext
`HttpContext`代表HTTP处理时的执行状态，它存储逻辑相关的组件的一些上下文信息，基本提供key-value的属性值和属性名。

定义的方法有：

1.	 Object getAttribute(String id);
2.	 void setAttribute(String id, Object obj);
3.	 Object removeAttribute(String id);

它有个基本的实现 `BasicHttpContext`,内部使用HashMap来保存属性，同时有个parentContext。

当按照属性名无法获得属性值时，会从parentContext中获取。


`ExecutionContext`中定义了5个基本的属性名：

1.	HTTP_CONNECTION：用于获得Connection对象
2.	HTTP_REQUEST：用于获得Request对象
3.	HTTP_RESPONSE:用于获得Response对象
4.	HTTP_TARGET_HOST
5.	HTTP_PROXY_HOST




####HttpParams
该类是一些值不可变的参数的集合，它定义了Http组件的运行时行为，该类期望被实现成一次写入，多次读取的模式。

定义的方法有：

1.	Object getParameter(String name);
2.	HttpParams setParameter(String name, Object value);
3.	boolean removeParameter(String name);
4.	其它int\long\double\boolean类型的写入和读取方法;


类SyncBasicHttpParams是个线程安全的HttpParams实现，它的所有方法都是同步的。

SyncBasicHttpParams继承了BasicHttpParams，BasicHttpParams内部使用HashMap保存参数。

####HttpMessage
HttpMessage有两部分组成：

1.	客户端到服务端的请求
2.	服务端到客户端的响应

按照协议规定,HttpMesage的协议格式为：

	      generic-message = start-line
                         *(message-header CRLF)                       
                         CRLF
                         [ message-body ]
          start-line      = Request-Line | Status-Line
          
它定义了下面的方法：

1.	获取版本信息:ProtocolVersion getProtocolVersion();
2.	判断头是否存在:boolean containsHeader(String name);
3.	获得header的系列方法;
4.	设置header的系列方法;
5.	移除header的系列方法;
6.	HttpParams getParams();
7.	void setParams(HttpParams params);

####HttpResponse
HttpResponse代表服务端到客户端的一个响应，它的协议格式为：

     Response      = Status-Line
                     *(( general-header
                      | response-header
                      | entity-header ) CRLF)
                      CRLF
                      [ message-body ]
                      
 其中Status-Line用StatusLine对象表示，包含协议版本号、状态码、原因短语三部分。
 
 三部分之间用空格(SP)分割。
 
 比如
 
 	HTTP/1.1 200 OK
 	
 它的协议版本号为HTTP/1.1，状态码为200，原因短语为OK 。
 
 因此HttpResponse接口定义的方法为：
 
 1.	设置和获得status line的相关方法
 2.	设置和获得HttpEntity的相关方法
 3.	设置和获得Locale的相关方法
 
####HttpEntity
 
HttpEnity用于请求时的内容实体，或者响应时的内容实体，但是它不是必须的。

比如请求时，只有POST和PUT会带有Entity，响应时，204 No Content或者304 Not Modified 就没有响应实体。

 
 它定义的方法有：
 
 1.	boolean isRepeatable();
 2.	boolean isChunked();
 3.	获得内容长度：long getContentLength();
 4.	获得ContentType:Header getContentType();
 5.	获得ContentEncoding:Header getContentEncoding();
 6.	获得内容：InputStream getContent()
 7.	void writeTo(OutputStream outstream) throws IOException;
 8.	boolean isStreaming(); 
 
######HttpFileEntity 
HttpFileEntity用于表示文件的实体，比如可以在提交一个文件时使用。

	FileEntity entity = new FileEntity(file, ContentType.create("text/plain", "UTF-8"));
######UrlEncodedFormEntity
用于表单的提交，可以设置字段的key value值 。


####ResponseHandler
ResponseHandler用于方便的处理响应信息。

 
 
####URIBuilder
用于构造URI的辅助类，可以设置schema参数等，由于方法定义的返回值为this，因此可以循环设置。

如：

		URIBuilder builder = new URIBuilder();
	
通过`builder.toBuild`可以获得URI。	
￼