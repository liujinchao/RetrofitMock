package com.android.apimock.data;

import android.content.Context;
import android.text.TextUtils;

import com.android.apimock.utils.AssetsUtil;
import com.android.apimock.utils.MockUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @ClassName:  MockDataManager
 * @author: liujc
 * @date: 2019/1/15
 * @Description:  模拟数据管理类
 */
public class MockDataManager {

    private Context context;
    private static final String DEFAULT_DATA_PATH = "response_default.json";

    private static class Holder {
        private static final MockDataManager INSTANCE = new MockDataManager();
    }

    public static MockDataManager get() {
        return Holder.INSTANCE;
    }

    private Map<String, ResponseInfo> infoMap;

    private MockDataManager() {
    }

    public Context getContext(){
        return context;
    }

    /**
     * mock数据初始化
     * @param context
     * @param path
     * @param mockDataUrl
     */
    public void init(final Context context, final String path, String mockDataUrl) {
        this.context = context;
        final Gson gson = new Gson();
        final Type type = new TypeToken<Map<String, ResponseInfo>>() {
        }.getType();
        MockUtil.setMockDataUrl(mockDataUrl);
        if (!TextUtils.isEmpty(mockDataUrl)){
            MockUtil.getRemoteMockApiData(mockDataUrl, new MockUtil.JsonCallBack() {
                @Override
                public void getRemoteMockData(String mockdata) {
                    if (TextUtils.isEmpty(mockdata)){
                        mockdata = AssetsUtil.getAssetsAsString(context,
                                TextUtils.isEmpty(path) ? DEFAULT_DATA_PATH : path);
                    }
                    infoMap = gson.fromJson(mockdata, type);
                }
            });
        }else {
            String json = AssetsUtil.getAssetsAsString(context,
                    TextUtils.isEmpty(path) ? DEFAULT_DATA_PATH : path);
            infoMap = gson.fromJson(json, type);
        }

    }

    public void refreshMockData(){
        String mockDataUrl = MockUtil.getMockDataUrl();
        if (!TextUtils.isEmpty(mockDataUrl)){
            final Gson gson = new Gson();
            final Type type = new TypeToken<Map<String, ResponseInfo>>() {
            }.getType();
            MockUtil.getRemoteMockApiData(mockDataUrl, new MockUtil.JsonCallBack() {
                @Override
                public void getRemoteMockData(String mockdata) {
                    if (!TextUtils.isEmpty(mockdata)){
                        infoMap = gson.fromJson(mockdata, type);
                    }
                }
            });
        }
    }

    public Map<String, ResponseInfo> getInfoMap() {
        refreshMockData(); //测试使用，此处就不做实时刷新了
        return infoMap;
    }
}
