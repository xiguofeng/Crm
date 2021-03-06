package com.ogg.crm.network.logic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ogg.crm.BaseApplication;
import com.ogg.crm.entity.Sms;
import com.ogg.crm.network.config.MsgResult;
import com.ogg.crm.network.config.RequestUrl;
import com.ogg.crm.network.volley.Request;
import com.ogg.crm.network.volley.Response;
import com.ogg.crm.network.volley.VolleyError;
import com.ogg.crm.network.volley.toolbox.StringRequest;
import com.ogg.crm.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmsLogic {

    public static final int NET_ERROR = 0;

    public static final int LIST_GET_SUC = NET_ERROR + 1;

    public static final int LIST_GET_FAIL = LIST_GET_SUC + 1;

    public static final int LIST_GET_EXCEPTION = LIST_GET_FAIL + 1;

    public static final int SEND_SUC = LIST_GET_EXCEPTION + 1;

    public static final int SEND_FAIL = SEND_SUC + 1;

    public static final int SEND_EXCEPTION = SEND_FAIL + 1;

    public static void list(final Context context, final Handler handler) {

        String url = RequestUrl.HOST_URL + RequestUrl.sms.list;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx_sms_list", ":" + response);
                parseListData(response, handler);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // 在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                try {
                    map.put("businessCode",
                            URLEncoder.encode("B", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return map;
            }
        };

        BaseApplication.getInstanceRequestQueue().add(stringRequest);
        BaseApplication.getInstanceRequestQueue().start();
    }

    private static void parseListData(String responseStr, Handler handler) {
        try {
            JSONObject response = new JSONObject(responseStr);
            String sucResult = response.getString("state").trim();
            if (sucResult.equals(MsgResult.RESULT_SUCCESS)) {

                JSONArray jsonArray = response.getJSONArray("model");
                ArrayList<Sms> smss = new ArrayList<Sms>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Sms sms = (Sms) JsonUtils.fromJsonToJava(jsonObject, Sms.class);
                    smss.add(sms);
                }

                Message message = new Message();
                message.what = LIST_GET_SUC;
                message.obj = smss;
                handler.sendMessage(message);
            } else {
                handler.sendEmptyMessage(LIST_GET_FAIL);
            }
        } catch (JSONException e) {
            handler.sendEmptyMessage(LIST_GET_EXCEPTION);
        }
    }


    public static void send(final Context context, final Handler handler, final String userId,
                            final String mobiles, final String messageContent) {

        String url = RequestUrl.HOST_URL + RequestUrl.sms.send;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx_send", ":" + response);
                parseSendData(response, handler);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // 在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                try {
                    map.put("userId",
                            URLEncoder.encode(userId, "UTF-8"));
                    map.put("mobiles",
                            URLEncoder.encode(mobiles, "UTF-8"));
                    map.put("messageContent",
                            URLEncoder.encode(messageContent, "UTF-8"));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return map;
            }
        };

        BaseApplication.getInstanceRequestQueue().add(stringRequest);
        BaseApplication.getInstanceRequestQueue().start();
    }


    private static void parseSendData(String responseStr, Handler handler) {
        try {
            JSONObject response = new JSONObject(responseStr);
            String sucResult = response.getString("state").trim();
            if (sucResult.equals(MsgResult.RESULT_SUCCESS)) {
                handler.sendEmptyMessage(SEND_SUC);
            } else {
                String failReason = response.getString("msg").trim();
                Message message = new Message();
                message.what = SEND_FAIL;
                message.obj = failReason;
                handler.sendMessage(message);
            }
        } catch (JSONException e) {
            handler.sendEmptyMessage(SEND_EXCEPTION);
        }
    }


}
