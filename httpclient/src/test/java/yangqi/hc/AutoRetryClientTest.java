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
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AutoRetryHttpClient;
import org.apache.http.protocol.HttpContext;

/**
 * 类AutoRetryClientTest.java的实现描述：TODO 类实现描述 
 * @author yangqi 2013-7-13 下午10:54:10
 */
public class AutoRetryClientTest {

    /**
     * @param args
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static void main(String[] args) throws ClientProtocolException, IOException {
        HttpClient client = new AutoRetryHttpClient(new ServiceUnavailableRetryStrategy() {

            private long interval = 1000;
            public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
                System.out.println("excute time is " + executionCount);
                if (executionCount > 4) {
                    return false;
                }

                if (response.getStatusLine().getStatusCode() == 200) {
                    return true;
                }
                return false;
            }

            public long getRetryInterval() {
                System.out.println("sleep " + interval);
                return interval;
            }
        });

        HttpGet get = new HttpGet("http://www.google.com");

        HttpResponse response = client.execute(get);

        System.out.println(response.getStatusLine());
    }

}
