package com.jt;


import com.jt.util.HttpClientService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class httpClientTest {
    @Autowired
    private HttpClientService httpClientService;
    @Test
    public void testUtil(){
        String url="http://www.baidu.com";
        String s = httpClientService.doGet(url);
        System.out.println(s);

    }



    @Autowired
    private CloseableHttpClient httpClient;
    /**
     * 1实例化httpClient对象
     *定义请求路径 String
     * 定义请求的对象 get/post。。。
     * 发起请求，获取响应对象
     * 判断返回值的状态,200...
     * 如果返回值正确，动态的获取响应数据，有可能图片，json，
     */
    @Test
    public void doGet() throws ClientProtocolException {
//     CloseableHttpClient  httpClient= HttpClients.createDefault();
//        CloseableHttpClient httpClient=httpClientBuilder.build();
        String url="http://www.baidu.com";
        HttpGet get=new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(get);
            if(200==response.getStatusLine().getStatusCode()){
                System.out.println("请求正确!!!");
               HttpEntity entity =response.getEntity();
               //将entity中携带的信息转化为字符串
                String result = EntityUtils.toString(entity,"utf-8");
                System.out.println(result);

            }else {
                System.out.println("请求异常");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
