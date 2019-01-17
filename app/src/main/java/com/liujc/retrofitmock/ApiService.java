package com.liujc.retrofitmock;

import com.liujc.retrofitmock.mock.VersionInfoEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    //mock数据测试
    @GET("common/getversion")
    Observable<BaseResponse<VersionInfoEntity>> getversion();
}

