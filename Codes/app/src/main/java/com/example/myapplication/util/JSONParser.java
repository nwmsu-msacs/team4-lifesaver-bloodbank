package com.example.myapplication.util;


import android.content.Context;
import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JSONParser {

    private static String responseStr = "";


   
    private static InputStream inputStream = null;
    private static HttpParams httpParams;
    public static HttpClient httpClient;
    private static HttpResponse httpResponse;
    private static HttpConnectionParams httpConnectionParams;
    private static String SERVICE_UNAVAILABLE ="Could not connect to the server";

    private static Context cntx;

    static String response = null;

    public static String makeHttpRequest(Context context, String url, String method, List<NameValuePair> params) {

        try {
            Log.v("anand>>", ">>>>>>>>>>>>>>>00000000000 " + params);
            HttpConnectionParams httpConnectionParams =  null;
            HttpParams httpParams = new BasicHttpParams();
            httpConnectionParams.setSoTimeout(httpParams, 20000);
            httpConnectionParams.setConnectionTimeout(httpParams, 20000);
            HttpClientParams.setRedirecting(httpParams, false);
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;


            // Checking http request method type
            if (method == "POST") {
                Log.v(Utill.LOG, "anand>>>>>>>>>>>>>>>" + url);
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    Log.v(Utill.LOG, "anand>>>>>>>>>>>>>>>" +context.getClass().getName()+ url+params);
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == "GET") {
                // appending params to url
                Log.v(Utill.LOG, "anand>>>>>>>>>>>>>>>" + url);
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
            Log.v(Utill.LOG, "anand>>>>>>>>>>>>>>>" + response);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }



}

