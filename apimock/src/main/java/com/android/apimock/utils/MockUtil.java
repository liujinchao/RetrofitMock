package com.android.apimock.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.apimock.data.MockDataManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ClassName: MockUtil
 * @author: liujc
 * @date: 2019/1/16
 * @Description: 工具类
 */
public class MockUtil {
    public static String KEY_MOCK_API_BASEURL = "mock_api_baseurl";//模拟api的baseUrl
    public static String KEY_MOCK_API_DATA_URL = "mock_api_data_url";//模拟api数据源服务地址
    /**
     * 获取api的baseUrl
     * @return
     */
    public static String getBaseUrl(){
        return PreferenceManager.getDefaultSharedPreferences(MockDataManager.get().getContext().getApplicationContext())
                .getString(KEY_MOCK_API_BASEURL,"http://cc.com/");
    }

    /**
     * 获取mock数据源的服务地址
     * @return
     */
    public static String getMockDataUrl(){
        return PreferenceManager.getDefaultSharedPreferences(MockDataManager.get().getContext().getApplicationContext())
                .getString(KEY_MOCK_API_DATA_URL,"");
    }

    public static void setMockDataUrl(String mockDataUrl){
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(MockDataManager.get().getContext().getApplicationContext());
        mPreferences.edit().putString(MockUtil.KEY_MOCK_API_DATA_URL,mockDataUrl).commit();
    }

    /**
     * 获取远程mockapi数据
     * @param mockdataUrl
     * @param jsonCallBack
     */
    public static void getRemoteMockApiData(final String mockdataUrl, final JsonCallBack jsonCallBack){
        final StringBuilder stringBuilder = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //构建URL地址
                    URL url = new URL(mockdataUrl);
                    try {
                        HttpURLConnection hcont = (HttpURLConnection) url.openConnection();
                        hcont.setRequestProperty("Content-Type", "application/json");
                        hcont.setDoOutput(true);
                        hcont.setRequestMethod("GET");
                        hcont.setRequestProperty("Accept-Charset", "utf-8");
                        hcont.setRequestProperty("contentType", "utf-8");

                        if (hcont.getResponseCode() == 200) {
                            InputStreamReader in = null;
                            in = new InputStreamReader(hcont.getInputStream(),"utf-8");
                            BufferedReader bufferedReader = new BufferedReader(in);
                            String line = null;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line);
                            }
                        }
                        if (jsonCallBack != null){
                            jsonCallBack.getRemoteMockData(stringBuilder.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (jsonCallBack != null){
                            jsonCallBack.getRemoteMockData("");
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    if (jsonCallBack != null){
                        jsonCallBack.getRemoteMockData("");
                    }
                }
            }
        }).start();
    }

    public interface JsonCallBack{
        void getRemoteMockData(String mockdata);
    }
}

