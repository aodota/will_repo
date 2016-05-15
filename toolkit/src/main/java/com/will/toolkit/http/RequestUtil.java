package com.will.toolkit.http;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WILL on 2016/5/14.
 */
public final class RequestUtil {
    private RequestUtil() {
    }

    /** 默认超时时间 - 5s */
    public static final int DEFAULT_TIMEOUT = 5000;

    /**
     * 发送GET请求
     *
     * @param url URL地址
     * @return 响应对象
     * @
     */
    public static String sendGet(String url) {
        return send(url, "GET", (Map<String, String>) null, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送GET请求
     *
     * @param url URL地址
     * @return 响应对象
     * @
     */
    public static String sendGet(String url, int timeout) {
        return send(url, "GET", (Map<String, String>) null, null, timeout);
    }

    /**
     * 发送GET请求
     *
     * @param url    URL地址
     * @param params 参数集合
     * @return 响应对象
     * @
     */
    public static String sendGet(String url, Map<String, String> params) {
        return send(url, "GET", params, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送GET请求
     *
     * @param url    URL地址
     * @param params 参数集合
     * @return 响应对象
     * @
     */
    public static String sendGet(String url, Map<String, String> params, int timeout) {
        return send(url, "GET", params, null, timeout);
    }

    /**
     * 发送GET请求
     *
     * @param url     URL地址
     * @param params  参数集合
     * @param headers 请求属性
     * @return 响应对象
     * @
     */
    public static String sendGet(String url, Map<String, String> params, Map<String, String> headers) {
        return send(url, "GET", params, headers, DEFAULT_TIMEOUT);
    }

    /**
     * 发送GET请求
     *
     * @param url     URL地址
     * @param params  参数集合
     * @param headers 请求属性
     * @return 响应对象
     * @
     */
    public static String sendGet(String url, Map<String, String> params, Map<String, String> headers, int timeout) {
        return send(url, "GET", params, headers, timeout);
    }

    /**
     * 发送GET请求
     *
     * @param url URL地址
     * @return 响应对象
     * @
     */
    public static String sendSSLGet(String url) {
        return sendSSL(url, "GET", (Map<String, String>) null, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送GET请求
     *
     * @param url URL地址
     * @return 响应对象
     * @
     */
    public static String sendSSLGet(String url, int timeout) {
        return sendSSL(url, "GET", (Map<String, String>) null, null, timeout);
    }

    /**
     * 发送GET请求
     *
     * @param url    URL地址
     * @param params 参数集合
     * @return 响应对象
     * @
     */
    public static String sendSSLGet(String url, Map<String, String> params) {
        return sendSSL(url, "GET", params, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送GET请求
     *
     * @param url    URL地址
     * @param params 参数集合
     * @return 响应对象
     * @
     */
    public static String sendSSLGet(String url, Map<String, String> params, int timeout) {
        return sendSSL(url, "GET", params, null, timeout);
    }

    /**
     * 发送GET请求
     *
     * @param url     URL地址
     * @param params  参数集合
     * @param headers 请求属性
     * @return 响应对象
     * @
     */
    public static String sendSSLGet(String url, Map<String, String> params, Map<String, String> headers) {
        return sendSSL(url, "GET", params, headers, DEFAULT_TIMEOUT);
    }

    /**
     * 发送GET请求
     *
     * @param url     URL地址
     * @param params  参数集合
     * @param headers 请求属性
     * @return 响应对象
     * @
     */
    public static String sendSSLGet(String url, Map<String, String> params, Map<String, String> headers, int timeout) {
        return sendSSL(url, "GET", params, headers, timeout);
    }

    /**
     * 发送POST请求
     *
     * @param url URL地址
     * @return 响应对象
     * @
     */
    public static String sendPost(String url) {
        return send(url, "POST", (Map<String, String>) null, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url URL地址
     * @return 响应对象
     * @
     */
    public static String sendPost(String url, int timeout) {
        return send(url, "POST", (Map<String, String>) null, null, timeout);
    }

    /**
     * 发送POST请求
     *
     * @param url    URL地址
     * @param params 参数集合
     * @return 响应对象
     * @
     */
    public static String sendPost(String url, Map<String, String> params) {
        return send(url, "POST", params, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url    URL地址
     * @param params 参数集合
     * @return 响应对象
     * @
     */
    public static String sendPost(String url, Map<String, String> params, int timeout) {
        return send(url, "POST", params, null, timeout);
    }

    /**
     * 发送POST请求
     *
     * @param url   URL地址
     * @param bytes 字节
     * @return 响应对象
     * @
     */
    public static String sendPost(String url, byte[] bytes) {
        return send(url, "POST", bytes, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url   URL地址
     * @param bytes 字节
     * @return 响应对象
     * @
     */
    public static String sendPost(String url, byte[] bytes, int timeout) {
        return send(url, "POST", bytes, null, timeout);
    }

    /**
     * 发送POST请求
     *
     * @param url     URL地址
     * @param params  参数集合
     * @param headers 请求属性
     * @return 响应对象
     * @
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> headers) {
        return send(url, "POST", params, headers, DEFAULT_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url     URL地址
     * @param params  参数集合
     * @param headers 请求属性
     * @return 响应对象
     * @
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> headers, int timeout) {
        return send(url, "POST", params, headers, timeout);
    }

    /**
     * 发送POST请求
     *
     * @param url URL地址
     * @return 响应对象
     * @
     */
    public static String sendSSLPost(String url) {
        return sendSSL(url, "POST", (Map<String, String>) null, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url URL地址
     * @return 响应对象
     * @
     */
    public static String sendSSLPost(String url, int timeout) {
        return sendSSL(url, "POST", (Map<String, String>) null, null, timeout);
    }

    /**
     * 发送POST请求
     *
     * @param url    URL地址
     * @param params 参数集合
     * @return 响应对象
     * @
     */
    public static String sendSSLPost(String url, Map<String, String> params) {
        return sendSSL(url, "POST", params, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url    URL地址
     * @param params 参数集合
     * @return 响应对象
     * @
     */
    public static String sendSSLPost(String url, Map<String, String> params, int timeout) {
        return sendSSL(url, "POST", params, null, timeout);
    }

    /**
     * 发送POST请求
     *
     * @param url   URL地址
     * @param bytes 字节
     * @return 响应对象
     * @
     */
    public static String sendSSLPost(String url, byte[] bytes) {
        return sendSSL(url, "POST", bytes, null, DEFAULT_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url   URL地址
     * @param bytes 字节
     * @return 响应对象
     * @
     */
    public static String sendSSLPost(String url, byte[] bytes, int timeout) {
        return sendSSL(url, "POST", bytes, null, timeout);
    }

    /**
     * 发送POST请求
     *
     * @param url     URL地址
     * @param params  参数集合
     * @param headers 请求属性
     * @return 响应对象
     * @
     */
    public static String sendSSLPost(String url, Map<String, String> params, Map<String, String> headers) {
        return sendSSL(url, "POST", params, headers, DEFAULT_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url     URL地址
     * @param params  参数集合
     * @param headers 请求属性
     * @return 响应对象
     * @
     */
    public static String sendSSLPost(String url, Map<String, String> params, Map<String, String> headers, int timeout) {
        return sendSSL(url, "POST", params, headers, timeout);
    }

    /**
     * 构建URL
     * @param url
     * @param paramsMap
     * @return
     */
    public static String buildURL(String url, Map<String, String> paramsMap) {
        if (null == paramsMap || paramsMap.isEmpty()) {
            return url;
        }
        String param = parseParam(paramsMap);
        if (StringUtils.isNotBlank(param)) {
            return url + "?" + param;
        } else {
            return url;
        }
    }

    /**
     * 发送HTTP请求
     *
     * @param urlStr
     * @return 响映对象
     * @
     */
    private static String send(String urlStr, String method, Map<String, String> params,
                               Map<String, String> headers, int timeout) {
        try {
            // 构造URL
            boolean isPost = "POST".equalsIgnoreCase(method) ? true : false;
            timeout = (timeout <= 0) ? DEFAULT_TIMEOUT : timeout;
            HttpURLConnection urlConnection = null;
            if (!isPost && params != null) {
                urlStr = buildURL(urlStr, params);
            }
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);

            // 解析参数
            if (isPost && params != null) {
                String param = parseParam(params);

                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(param.getBytes());
                urlConnection.getOutputStream().flush();
                urlConnection.getOutputStream().close();
            }

            // 处理Headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 解析回响
            return parseEcho(urlConnection);
        } catch (Throwable e) {
            throw new RuntimeException("send request error", e);
        }
    }

    /**
     * 发送HTTP请求
     *
     * @param urlStr
     * @return 响映对象
     * @
     */
    private static String sendSSL(String urlStr, String method, Map<String, String> params,
                               Map<String, String> headers, int timeout) {
        try {
            SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
            sc.init(null, new TrustManager[]{new MyX509TrustManager()}, new SecureRandom());

            // 构造URL
            boolean isPost = "POST".equalsIgnoreCase(method) ? true : false;
            timeout = (timeout <= 0) ? DEFAULT_TIMEOUT : timeout;
            HttpsURLConnection urlConnection = null;
            if (!isPost && params != null) {
                urlStr = buildURL(urlStr, params);
            }
            URL url = new URL(urlStr);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(sc.getSocketFactory());
            urlConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            urlConnection.setRequestMethod(method);
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);

            // 解析参数
            if (isPost && params != null) {
                String param = parseParam(params);

                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(param.getBytes());
                urlConnection.getOutputStream().flush();
                urlConnection.getOutputStream().close();
            }

            // 处理Headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 解析回响
            return parseEcho(urlConnection);
        } catch (Throwable e) {
            throw new RuntimeException("send request error", e);
        }
    }

    /**
     * 发送HTTP请求
     *
     * @param urlStr
     * @return 响映对象
     * @
     */
    private static String send(String urlStr, String method, byte[] bytes,
                               Map<String, String> headers, int timeout) {

        try {
            // 构造URL
            timeout = (timeout <= 0) ? DEFAULT_TIMEOUT : timeout;
            HttpURLConnection urlConnection = null;
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);
            urlConnection.setRequestProperty("Content-Type", "application/octet-stream");

            if (bytes != null) {
                urlConnection.getOutputStream().write(bytes);
                urlConnection.getOutputStream().flush();
                urlConnection.getOutputStream().close();
            }

            // 处理Headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 解析回响
            return parseEcho(urlConnection);
        } catch (Throwable e) {
            throw new RuntimeException("send request error", e);
        }
    }

    /**
     * 发送HTTP请求
     *
     * @param urlStr
     * @return 响映对象
     * @
     */
    private static String sendSSL(String urlStr, String method, byte[] bytes,
                               Map<String, String> headers, int timeout) {

        try {
            SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
            sc.init(null, new TrustManager[]{new MyX509TrustManager()}, new SecureRandom());

            // 构造URL
            timeout = (timeout <= 0) ? DEFAULT_TIMEOUT : timeout;
            HttpsURLConnection urlConnection = null;
            URL url = new URL(urlStr);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(sc.getSocketFactory());
            urlConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            urlConnection.setRequestMethod(method);
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);
            urlConnection.setRequestProperty("Content-Type", "application/octet-stream");

            if (bytes != null) {
                urlConnection.getOutputStream().write(bytes);
                urlConnection.getOutputStream().flush();
                urlConnection.getOutputStream().close();
            }

            // 处理Headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 解析回响
            return parseEcho(urlConnection);
        } catch (Throwable e) {
            throw new RuntimeException("send request error", e);
        }
    }

    /**
     * 解析参数
     *
     * @param parameters
     * @return
     * @version 1.0.0.0 2012-5-20 下午5:36:02
     */
    private static String parseParam(Map<String, String> parameters) {
        try {
            StringBuffer builder = new StringBuffer();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                builder.append(URLEncoder.encode(key, "utf-8")).append("=").append(URLEncoder.encode(value, "utf-8"));
                builder.append("&");
            }

            if (parameters.size() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }

            return builder.toString();
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }

        return null;
    }

    /**
     * 得到响应对象
     *
     * @param urlConnection
     * @return 响应对象
     * @
     */
    private static String parseEcho(HttpURLConnection urlConnection) throws IOException {
        try {
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                InputStream in = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                bufferedReader.close();

                return builder.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }



    /**
     * TrustAnyTrustManager
     */
    private static class MyX509TrustManager implements X509TrustManager {

        /*
     * The default X509TrustManager returned by SunX509.  We'll delegate
     * decisions to it, and fall back to the logic in this class if the
     * default X509TrustManager doesn't trust it.
     */
        X509TrustManager sunJSSEX509TrustManager;

        MyX509TrustManager() throws Exception {
            // create a "default" JSSE X509TrustManager.
//            KeyStore ks = KeyStore.getInstance("JKS");
//            ks.load(new FileInputStream("trustedCerts"), "passphrase".toCharArray());
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
//            tmf.init(ks);
//            TrustManager tms[] = tmf.getTrustManagers();

            /*
             * Iterate over the returned trustmanagers, look
             * for an instance of X509TrustManager.  If found,
             * use that as our "default" trust manager.
             */
//            for (int i = 0; i < tms.length; i++) {
//                if (tms[i] instanceof X509TrustManager) {
//                    sunJSSEX509TrustManager = (X509TrustManager) tms[i];
//                    return;
//                }
//            }
        /*
         * Find some other way to initialize, or else we have to fail the
         * constructor.
         */
//            throw new Exception("Couldn't initialize");
        }

        /*
         * Delegate to the default trust manager.
         */
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
//            try {
//                sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
//            } catch (CertificateException excep) {
//                // do any special handling here, or rethrow exception.
//            }
        }

        /*
         * Delegate to the default trust manager.
         */
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
//            try {
//                sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
//            } catch (CertificateException excep) {
//                /*
//                 * Possibly pop up a dialog box asking whether to trust the
//                 * cert chain.
//                 */
//            }
        }

        /*
         * Merely pass this through.
         */
        public X509Certificate[] getAcceptedIssuers() {
//            return sunJSSEX509TrustManager.getAcceptedIssuers();
            return null;
        }
    }

    /** TrustAnyHostnameVerifier */
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static void main(String[] args) {
        // 测试Get请求
        Map<String, String> map  = new HashMap<String, String>();
        map.put("p", "1957");
        String echo = sendSSLGet("https://docs.gradle.org/current/userguide/tutorial_java_projects.html");
        System.out.println(echo);
    }


}
