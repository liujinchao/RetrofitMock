package com.android.apimock;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.apimock.data.MockDataManager;
import com.android.apimock.interceptor.MockInterceptor;
import com.android.apimock.utils.MockUtil;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.apimock.utils.MockUtil.KEY_MOCK_API_BASEURL;

/**
 * @ClassName:  ApiMock
 * @author: liujc
 * @date: 2019/1/15
 * @Description: Api模拟工具类
 */
public final class ApiMock {

    private ApiMock() {
    }

    /**
     * 初始化mock数据源
     * @param context
     * @param fileName  assets文件夹下文件名称
     */
    public static void init(Context context, String fileName) {
        MockDataManager.get().init(context.getApplicationContext(), fileName, null);
    }
    /**
     * 初始化mock数据源
     * @param context
     * @param fileName  assets文件夹下文件名称
     * @param mockDataUrl  远程mockdata文件地址
     */
    public static void init(Context context, String fileName, String mockDataUrl) {
        MockDataManager.get().init(context.getApplicationContext(), fileName, mockDataUrl);
    }

    public static Retrofit createMockRetrofit(String host) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new MockInterceptor())
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * @param baseUrl  api的baseUrl
     * @param apiClass 对应service类型
     * @return  所创建的对应service
     */
    public static  <T> T getApiService(String baseUrl, Class<T> apiClass) {
        saveBaseUrl(baseUrl);
        return createMockRetrofit(baseUrl).create(apiClass);
    }

    /**
     * 需要设置baseUrl或者使用默认url
     * @param apiClass 对应service类型
     * @return  所创建的对应service
     */
    public static  <T> T getApiService(Class<T> apiClass) {
        return createMockRetrofit(MockUtil.getBaseUrl()).create(apiClass);
    }

    private static void saveBaseUrl(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)){
            return;
        }
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(MockDataManager.get().getContext().getApplicationContext());
        mPreferences.edit().putString(KEY_MOCK_API_BASEURL,baseUrl).commit();
    }

}
