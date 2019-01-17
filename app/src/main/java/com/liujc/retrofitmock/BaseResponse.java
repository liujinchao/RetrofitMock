package com.liujc.retrofitmock;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @ClassName:  BaseResponse
 * @author: liujc
 * @date: 2019/1/16
 * @Description: response基类
 */
public class BaseResponse<T> implements Serializable {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("resultMessage")
    public String resultMessage;
    @SerializedName("data")
    public T data;

}