package com.ogg.crm.network.utils;

import com.ogg.crm.network.volley.NetworkResponse;
import com.ogg.crm.network.volley.ParseError;
import com.ogg.crm.network.volley.Response;
import com.ogg.crm.network.volley.Response.ErrorListener;
import com.ogg.crm.network.volley.Response.Listener;
import com.ogg.crm.network.volley.toolbox.HttpHeaderParser;
import com.ogg.crm.network.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JsonObjectRequestUtf extends JsonObjectRequest {

    public JsonObjectRequestUtf(String url, JSONObject jsonRequest, Listener listener, ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    public JsonObjectRequestUtf(int method, String url, JSONObject jsonRequest, Listener listener,
                                ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

        try {
            JSONObject jsonObject = new JSONObject(new String(response.data, "UTF-8"));
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            return Response.error(new ParseError(je));
        }
    }

}