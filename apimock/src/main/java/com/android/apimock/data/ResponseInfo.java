package com.android.apimock.data;

import com.google.gson.JsonObject;

import java.util.Map;

/**
 * @ClassName:  ResponseInfo
 * @author: liujc
 * @date: 2019/1/15
 * @Description: 模拟接口返回对象
 */
public class ResponseInfo {

    private int code;
    private String message;
    private JsonObject data;
    private String resultMessage;
    private String protocol;
    private String contentType;
    private Map<String, String> header;

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBodyAsString() {
        if (data == null) {
            return "";
        }
        return data.toString();
    }

    @Override
    public String toString() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code",code);
        jsonObject.addProperty("message",message);
        jsonObject.addProperty("resultMessage",resultMessage);
        jsonObject.add("data",data);
        return jsonObject.toString();
    }
}
