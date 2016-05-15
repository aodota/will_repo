package com.will.toolkit.http;

import java.util.HashMap;
import java.util.Map;

/**
 * RequestBuilder 构建Request
 * Created by WILL on 2016/5/14.
 */
public class RequestBuilder {
    /** 请求地址 */
    private String url;
    /** 参数 */
    private Map<String, String> paramsMap;
    /** 超时时间 */
    private int timeout = RequestUtil.DEFAULT_TIMEOUT;

    /**
     * 构造函数
     * @param url
     */
    public RequestBuilder(String url) {
        this.url = url;
    }

    /**
     * 构造函数
     * @param url
     * @param paramsMap
     */
    public RequestBuilder(String url, Map<String, String> paramsMap) {
        this.url = url;
        this.paramsMap = paramsMap;
    }

    /**
     * 构造函数
     * @param url
     */
    public RequestBuilder(String url, int timeout) {
        this.url = url;
        this.timeout = timeout;
    }

    /**
     * 构造函数
     * @param url
     * @param paramsMap
     */
    public RequestBuilder(String url, Map<String, String> paramsMap, int timeout) {
        this.url = url;
        this.paramsMap = paramsMap;
        this.timeout = timeout;
    }

    /**
     * 加入参数
     * @param key
     * @param value
     */
    public RequestBuilder append(String key, String value) {
        if (null == this.paramsMap) {
            this.paramsMap = new HashMap<String, String>();
        }
        this.paramsMap.put(key, value);
        return this;
    }

    /**
     * 设置超时
     * @param timeout
     * @return
     */
    public RequestBuilder setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * 发送Get请求
     * @return
     */
    public String sendGet() {
        return RequestUtil.sendGet(this.url, this.paramsMap, this.timeout);
    }

    /**
     * 发送Post请求
     * @return
     */
    public String sendPost() {
        return RequestUtil.sendPost(this.url, this.paramsMap, this.timeout);
    }

    /**
     * 发送Post请求
     * @param bytes
     * @return
     */
    public String sendPost(byte[] bytes) {
        return RequestUtil.sendPost(this.url, bytes, this.timeout);
    }

    /**
     * 发送Get请求
     * @return
     */
    public String sendSSLGet() {
        return RequestUtil.sendSSLGet(this.url, this.paramsMap, this.timeout);
    }

    /**
     * 发送Post请求
     * @return
     */
    public String sendSSLPost() {
        return RequestUtil.sendSSLPost(this.url, this.paramsMap, this.timeout);
    }

    /**
     * 发送Post请求
     * @param bytes
     * @return
     */
    public String sendSSLPost(byte[] bytes) {
        return RequestUtil.sendSSLPost(this.url, bytes, this.timeout);
    }

    /**
     * 构建URL
     * @return
     */
    public String buildURL() {
        return RequestUtil.buildURL(this.url, this.paramsMap);
    }

    /**
     * 获取请求URL
     * @return
     */
    public String getRequestURL() {
        return this.url;
    }

    /**
     * 获取请求参数
     * @return
     */
    public Map<String, String> getParamsMap() {
        return paramsMap;
    }
}
