package com.geekband.moran.api;

import java.util.TreeMap;


public class HttpManager {

    public static String mGeekBandApi = "http://moran.chinacloudapp.cn/moran/web";

    public static String mRegister = "/user/register";
    public static String gid = "G2015020152";

    public static TreeMap<String, String> sign;

//    public static void initHttpClientHeader(AsyncHttpClient client) {
//        if (client != null) {
//            client.addHeader("Connection", "Keep-Alive");
//            client.addHeader("Charset", "UTF-8");
////            client.addHeader("inWatch-base", initBaseHeader());
//
//        }
//    }

//    private static String initBaseHeader() {
//        JSONObject json = new JSONObject();
//
//        // {"os_type":0,"time_stamp":1408547336,"api_version":"1.0.0","app_version":"1.0.9","app_key":85695696,"device_id":"wjdkdflf213"}
//        try {
//            long date = new Date().getTime();
//            json.put("os_type", 2);
//            // json.put("access_token", kAccessToken);
//            json.put("access_token", "ea6f1872ea779d0a1c141585e5f240a27b539c98");
//            json.put("time_stamp", date);
//            json.put("api_version", API_VERSION);
//            json.put("app_version", BuildUtil.getAppVersionName());
//            json.put("app_key", APP_KEY);
//            json.put("device_id", BuildUtil.getIMEI());
//
//            sign = new TreeMap<String, String>();
//            sign.put("os_type", "2");
//            sign.put("access_token", "ea6f1872ea779d0a1c141585e5f240a27b539c98");
//            sign.put("api_version", API_VERSION);
//            sign.put("app_version", BuildUtil.getAppVersionName());
//            sign.put("app_key", APP_KEY);
//            sign.put("device_id", BuildUtil.getIMEI());
//            sign.put("time_stamp", date + "");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return json.toString();
//    }

//    public static AsyncHttpClient getHttpClientInstance(Context context) {
//        AsyncHttpClient client = new AsyncHttpClient();
//        initHttpClientHeader(client);
//        return client;
//    }
//
//    public static RequestParams createBaseRequestParams() {
//        RequestParams params = new RequestParams();
//
//        return params;
//    }
}
