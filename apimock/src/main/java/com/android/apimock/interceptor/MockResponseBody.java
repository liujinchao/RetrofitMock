package com.android.apimock.interceptor;


import android.text.TextUtils;

import com.android.apimock.data.MockDataManager;
import com.android.apimock.data.ResponseInfo;
import com.android.apimock.utils.MockUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;

/**
 * @ClassName:  MockResponseBody
 * @author: liujc
 * @date: 2019/1/15
 * @Description: 模拟返回结果对象
 */
public class MockResponseBody extends ResponseBody {

    private static final String DEFAULT_RESPONSE = "{\"code\":200}";
    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.parse("application/json;charset=UTF-8");

    private final Request request;

    public MockResponseBody(Request request) {
        this.request = request;
    }

    @Override
    public MediaType contentType() {
        String path = getApiPath();
        Map<String, ResponseInfo> infoMap = MockDataManager.get().getInfoMap();
        if (infoMap.containsKey(path)
                && !TextUtils.isEmpty(infoMap.get(path).getContentType())) {
            return MediaType.parse(infoMap.get(path).getContentType());
        } else {
            return DEFAULT_MEDIA_TYPE;
        }
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(Okio.source(inputStream()));
    }

    private InputStream inputStream() {
        String path = getApiPath();
        Map<String, ResponseInfo> infoMap = MockDataManager.get().getInfoMap();
        if (infoMap.containsKey(path)) {
            return new ByteArrayInputStream(infoMap.get(path).toString().getBytes());
        } else {
            return new ByteArrayInputStream(DEFAULT_RESPONSE.getBytes());
        }
    }

    private String getApiPath(){
        String path = request.url().encodedPath();
        String basePath ="";//baseUrl的path
        if (!TextUtils.isEmpty(MockUtil.getBaseUrl())){
            basePath = HttpUrl.parse(MockUtil.getBaseUrl()).url().getPath();
        }
        if (!TextUtils.isEmpty(basePath) && !TextUtils.isEmpty(path)){ //移除baseUrl中的path
            path = path.substring(basePath.length(), path.length());
        }
        return path;
    }
}
