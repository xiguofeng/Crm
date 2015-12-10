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
import com.ogg.crm.network.config.MsgResult;
import com.ogg.crm.network.config.RequestUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddressLogic {

    public static final int NET_ERROR = 0;

    public static final int ANDRESS_DATA_GET_SUC = NET_ERROR + 1;

    public static final int ANDRESS_DATA_GET_FAIL = ANDRESS_DATA_GET_SUC + 1;

    public static final int ANDRESS_DATA_GET_EXCEPTION = ANDRESS_DATA_GET_FAIL + 1;

    public static void getAddressData(final Context context, final Handler handler) {

        String url = RequestUrl.HOST_URL + RequestUrl.address.getData;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx_getAddressData", ":" + response);
                parseAddressDataData(response, handler);
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

                return map;
            }
        };

        BaseApplication.getInstanceRequestQueue().add(stringRequest);
        BaseApplication.getInstanceRequestQueue().start();
    }

    private static void parseAddressDataData(String responseStr, Handler handler) {
        try {
            JSONObject response = new JSONObject(responseStr);
            String sucResult = response.getString("state").trim();
            if (sucResult.equals(MsgResult.RESULT_SUCCESS)) {
                JSONArray jsonArray = response.getJSONArray("model");

                Message message = new Message();
                message.what = ANDRESS_DATA_GET_SUC;
                message.obj = jsonArray.toString();
                handler.sendMessage(message);
            } else {
                handler.sendEmptyMessage(ANDRESS_DATA_GET_FAIL);
            }
        } catch (JSONException e) {
            handler.sendEmptyMessage(ANDRESS_DATA_GET_EXCEPTION);
        }
    }
}
