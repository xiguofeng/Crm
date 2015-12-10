package com.ogg.crm.network.utils;

import com.ogg.crm.network.volley.AuthFailureError;
import com.ogg.crm.network.volley.NetworkResponse;
import com.ogg.crm.network.volley.ParseError;
import com.ogg.crm.network.volley.Response;
import com.ogg.crm.network.volley.Response.ErrorListener;
import com.ogg.crm.network.volley.Response.Listener;
import com.ogg.crm.network.volley.toolbox.HttpHeaderParser;
import com.ogg.crm.network.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CookieRequest extends JsonObjectRequest {
    private Map mHeaders = new HashMap(1);

    public CookieRequest(String url, JSONObject jsonRequest, Listener listener, ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    public CookieRequest(int method, String url, JSONObject jsonRequest, Listener listener,
                         ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public void setCookie(String cookie) {
        mHeaders.put("Cookie", cookie);
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            // String jsonString = new String(response.data,
            // HttpHeaderParser.parseCharset(response.headers));

            String jsonString = new String(response.data, "UTF-8");
            //Log.e("xxx_jsonString", "b" + jsonString);
            int index = jsonString.indexOf("{");
            jsonString = jsonString.substring(index);

            // jsonString=StringUtils.replaceBlank(jsonString);
            //Log.e("xxx_jsonString", "a" + jsonString);

            JSONObject json = new JSONObject(jsonString);
            Map<String, String> responseHeaders = response.headers;
            String rawCookies = responseHeaders.get("Set-Cookie");
            json.put("Set-Cookie", rawCookies);

            return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

}