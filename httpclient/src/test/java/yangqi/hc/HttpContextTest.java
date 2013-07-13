/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package yangqi.hc;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

/**
 * 类HttpContextTest.java的实现描述：TODO 类实现描述 
 * @author yangqi 2013-7-13 下午9:46:11
 */
public class HttpContextTest {

    /**
     * @param args
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static void main(String[] args) throws ClientProtocolException, IOException {
        // TODO Auto-generated method stub
        HttpClient httpclient = new DefaultHttpClient();

        HttpContext context = new BasicHttpContext();

        // Prepare a request object
        HttpGet httpget = new HttpGet("http://www.apache.org/");

        // Execute the request
        HttpResponse response = httpclient.execute(httpget, context);

        showAttr(ExecutionContext.HTTP_CONNECTION, context);
        showAttr(ExecutionContext.HTTP_PROXY_HOST, context);
        showAttr(ExecutionContext.HTTP_REQ_SENT, context);
        showAttr(ExecutionContext.HTTP_RESPONSE, context);
        showAttr(ExecutionContext.HTTP_REQUEST, context);
        showAttr(ExecutionContext.HTTP_TARGET_HOST, context);




    }

    private static void showAttr(String attrname, HttpContext context) {
        System.out.println(attrname + " : " + context.getAttribute(attrname));
    }

}
