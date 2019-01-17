package com.liujc.retrofitmock;

import android.app.Application;

import com.android.apimock.ApiMock;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        ApiMock.init(this, "mockdata.json");
        ApiMock.init(this, "mockdata.json","http://10.181.52.38:8081/html/mockdata.json");
    }

}
