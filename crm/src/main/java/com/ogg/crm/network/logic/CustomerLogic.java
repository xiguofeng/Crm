package com.ogg.crm.network.logic;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ogg.crm.BaseApplication;
import com.ogg.crm.entity.Customer;
import com.ogg.crm.entity.CustomerInfoCategory;
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

public class CustomerLogic {

    public static final int NET_ERROR = 0;

    public static final int CONF_INFO_GET_SUC = NET_ERROR + 1;

    public static final int CONF_INFO_GET_FAIL = CONF_INFO_GET_SUC + 1;

    public static final int CONF_INFO_GET_EXCEPTION = CONF_INFO_GET_FAIL + 1;

    public static final int LIST_GET_SUC = CONF_INFO_GET_EXCEPTION + 1;

    public static final int LIST_GET_FAIL = LIST_GET_SUC + 1;

    public static final int LIST_GET_EXCEPTION = LIST_GET_FAIL + 1;

    public static void getConfInfo(final Context context, final Handler handler,
                                   final String category) {

        String url = RequestUrl.HOST_URL + RequestUrl.customer.getConfInfo;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx_getConfInfo", ":" + response);
                parseConfInfoData(response, handler, category);
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
                    map.put("category",
                            URLEncoder.encode(category, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return map;
            }
        };

        BaseApplication.getInstanceRequestQueue().add(stringRequest);
        BaseApplication.getInstanceRequestQueue().start();
    }

    private static void parseConfInfoData(String responseStr, Handler handler, String category) {
        try {
            JSONObject response = new JSONObject(responseStr);
            String sucResult = response.getString("state").trim();
            if (sucResult.equals(MsgResult.RESULT_SUCCESS)) {

                JSONArray jsonArray = response.getJSONArray("model");
                ArrayList<CustomerInfoCategory> categoryList = new ArrayList<CustomerInfoCategory>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CustomerInfoCategory cICate = (CustomerInfoCategory) JsonUtils.fromJsonToJava(jsonObject, CustomerInfoCategory.class);
                    categoryList.add(cICate);
                }

                Message message = new Message();
                message.what = CONF_INFO_GET_SUC;
                message.obj = categoryList;
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                message.setData(bundle);
                handler.sendMessage(message);
            } else {
                handler.sendEmptyMessage(CONF_INFO_GET_FAIL);
            }
        } catch (JSONException e) {
            handler.sendEmptyMessage(CONF_INFO_GET_EXCEPTION);
        }
    }

    public static void list(final Context context, final Handler handler,
                            final String userId, final String page, final String rows) {

        String url = RequestUrl.HOST_URL + RequestUrl.customer.list;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx_custom_list", ":" + response);
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
                    map.put("page",
                            URLEncoder.encode(page, "UTF-8"));
                    map.put("rows",
                            URLEncoder.encode(rows, "UTF-8"));
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

                JSONArray jsonArray = response.getJSONArray("rows");
                ArrayList<Customer> customers = new ArrayList<Customer>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Customer customer = (Customer) JsonUtils.fromJsonToJava(jsonObject, Customer.class);
                    customers.add(customer);
                }

                Message message = new Message();
                message.what = LIST_GET_SUC;
                message.obj = customers;
                handler.sendMessage(message);
            } else {
                handler.sendEmptyMessage(LIST_GET_FAIL);
            }
        } catch (JSONException e) {
            handler.sendEmptyMessage(LIST_GET_EXCEPTION);
        }
    }


    public static void filterList(final Context context, final Handler handler,
                                  final String userId, final String page, final String rows, final String cusLevel, final String customerType, final String tradeFlg, final String followStatus) {

        String url = RequestUrl.HOST_URL + RequestUrl.customer.list;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx_custom_filter_list", ":" + response);
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
                    map.put("page",
                            URLEncoder.encode(page, "UTF-8"));
                    map.put("rows",
                            URLEncoder.encode(rows, "UTF-8"));
                    map.put("cusLevel",
                            URLEncoder.encode(cusLevel, "UTF-8"));
                    map.put("customerType",
                            URLEncoder.encode(customerType, "UTF-8"));
                    map.put("tradeFlg",
                            URLEncoder.encode(tradeFlg, "UTF-8"));
                    map.put("followStatus",
                            URLEncoder.encode(followStatus, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return map;
            }
        };

        BaseApplication.getInstanceRequestQueue().add(stringRequest);
        BaseApplication.getInstanceRequestQueue().start();
    }


}
