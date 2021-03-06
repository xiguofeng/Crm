package com.ogg.crm.network.logic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ogg.crm.network.volley.Request;
import com.ogg.crm.network.volley.Response;
import com.ogg.crm.network.volley.VolleyError;
import com.ogg.crm.network.volley.toolbox.StringRequest;
import com.ogg.crm.BaseApplication;
import com.ogg.crm.entity.Appointment;
import com.ogg.crm.network.config.MsgResult;
import com.ogg.crm.network.config.RequestUrl;
import com.ogg.crm.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppointmentLogic {

    public static final int NET_ERROR = 0;

    public static final int LIST_GET_SUC = NET_ERROR + 1;

    public static final int LIST_GET_FAIL = LIST_GET_SUC + 1;

    public static final int LIST_GET_EXCEPTION = LIST_GET_FAIL + 1;

    public static final int STATE_SET_SUC = LIST_GET_EXCEPTION + 1;

    public static final int STATE_SET_FAIL = STATE_SET_SUC + 1;

    public static final int STATE_SET_EXCEPTION = STATE_SET_FAIL + 1;

    public static void getList(final Context context, final Handler handler,
                               final String userId) {

        String url = RequestUrl.HOST_URL + RequestUrl.appointment.list;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx_appointlist", ":" + response);
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
                    map.put("userId",
                            URLEncoder.encode(userId, "UTF-8"));
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
                ArrayList<Appointment> apointmentList = new ArrayList<Appointment>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Appointment appointment = (Appointment) JsonUtils.fromJsonToJava(jsonObject, Appointment.class);
                    apointmentList.add(appointment);
                }

                Message message = new Message();
                message.what = LIST_GET_SUC;
                message.obj = apointmentList;
                handler.sendMessage(message);
            } else {
                handler.sendEmptyMessage(LIST_GET_FAIL);
            }
        } catch (JSONException e) {
            handler.sendEmptyMessage(LIST_GET_EXCEPTION);
        }
    }

    public static void setState(final Context context, final Handler handler,
                                final String id) {

        String url = RequestUrl.HOST_URL + RequestUrl.appointment.setState;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx_setState", ":" + response);
                parseSetStateData(response, handler);
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
                    map.put("remindIds",
                            URLEncoder.encode(id, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return map;
            }
        };

        BaseApplication.getInstanceRequestQueue().add(stringRequest);
        BaseApplication.getInstanceRequestQueue().start();
    }

    private static void parseSetStateData(String responseStr, Handler handler) {
        try {
            JSONObject response = new JSONObject(responseStr);
            String sucResult = response.getString("state").trim();
            if (sucResult.equals(MsgResult.RESULT_SUCCESS)) {
                handler.sendEmptyMessage(STATE_SET_SUC);
            } else {
                handler.sendEmptyMessage(STATE_SET_FAIL);
            }
        } catch (JSONException e) {
            handler.sendEmptyMessage(STATE_SET_EXCEPTION);
        }
    }

}
