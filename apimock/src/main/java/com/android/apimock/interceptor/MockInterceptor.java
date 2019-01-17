package com.android.apimock.interceptor;

import android.text.TextUtils;


import com.android.apimock.data.MockDataManager;
import com.android.apimock.data.ResponseInfo;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Response;

/**
 * @ClassName:  MockInterceptor
 * @author: liujc
 * @date: 2019/1/15
 * @Description:  模拟拦截器
 */
public class MockInterceptor implements Interceptor {
    /**
     * 拦截对应请求，将本地模拟数据返回
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        String path = chain.request().url().encodedPath();
        Map<String, ResponseInfo> infoMap = MockDataManager.get().getInfoMap();
        if (infoMap.containsKey(path)) {
            ResponseInfo responseInfo = infoMap.get(path);
            String protocol = responseInfo.getProtocol();
            if (TextUtils.isEmpty(protocol)){
                protocol = "http/1.1";
            }
            Response.Builder builder = new Response.Builder()
                    .message(responseInfo.getMessage())
                    .code(responseInfo.getCode())
                    .protocol(Protocol.get(protocol))
                    .request(chain.request())
                    .body(new MockResponseBody(chain.request()));
            Map<String, String> header = responseInfo.getHeader();
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    builder.header(entry.getKey(), entry.getValue());
                }
            }
            return builder.build();
        } else {
            return new Response.Builder()
                    .request(chain.request())
                    .code(200)
                    .protocol(Protocol.HTTP_1_1)
                    .body(new MockResponseBody(chain.request()))
                    .message("")
                    .build();
        }
    }
}
