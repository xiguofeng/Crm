package com.ogg.crm.network.logic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ogg.crm.BaseApplication;
import com.ogg.crm.entity.User;
import com.ogg.crm.network.config.MsgResult;
import com.ogg.crm.network.config.RequestUrl;
import com.ogg.crm.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UserLogic {

    public static final int NET_ERROR = 0;

    public static final int REGIS_SUC = NET_ERROR + 1;

    public static final int REGIS_FAIL = REGIS_SUC + 1;

    public static final int REGIS_EXCEPTION = REGIS_FAIL + 1;

    public static final int LOGIN_SUC = REGIS_EXCEPTION + 1;

    public static final int LOGIN_FAIL = LOGIN_SUC + 1;

    public static final int LOGIN_EXCEPTION = LOGIN_FAIL + 1;

    public static final int MODIFY_PWD_SUC = LOGIN_EXCEPTION + 1;

    public static final int MODIFY_PWD_FAIL = MODIFY_PWD_SUC + 1;

    public static final int MODIFY_PWD_EXCEPTION = MODIFY_PWD_FAIL + 1;

    public static final int USER_INFO_GET_SUC = MODIFY_PWD_EXCEPTION + 1;

    public static final int USER_INFO_GET_FAIL = USER_INFO_GET_SUC + 1;

    public static final int USER_INFO_GET_EXCEPTION = USER_INFO_GET_FAIL + 1;

    public static final int SEND_AUTHCODE_SUC = MODIFY_PWD_EXCEPTION + 1;

    public static final int SEND_AUTHCODE_FAIL = SEND_AUTHCODE_SUC + 1;

    public static final int SEND_AUTHCODE_EXCEPTION = SEND_AUTHCODE_FAIL + 1;

    public static final int SET_REAL_SUC = SEND_AUTHCODE_EXCEPTION + 1;

    public static void login(final Context context, final Handler handler,
                             final User user) {

        String url = RequestUrl.HOST_URL + RequestUrl.account.login;
        // String url =
        // "http://218.2.105.13:20001/CRM1.0/b2bUser/loginUser.do?logonName=b2aadmin&logonPass=admin12345";
        Log.e("xxx_url", url);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx_login", "" + response.toString());
                parseLoginData(response, handler);
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
                    map.put("logonName",
                            URLEncoder.encode(user.getLogonName(), "UTF-8"));
                    map.put("logonPass",
                            URLEncoder.encode(user.getLogonPass(), "UTF-8"));
//                    map.put("logonName",
//                            URLEncoder.encode("b2badmin", "UTF-8"));
//                    map.put("logonPass",
//                            URLEncoder.encode("cggol", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return map;
            }
        };

        BaseApplication.getInstanceRequestQueue().add(stringRequest);
        BaseApplication.getInstanceRequestQueue().start();
    }

    private static void parseLoginData(String responseStr, Handler handler) {
        try {
            // Log.e("xxx_login_suc", response.toString());
            JSONObject response = new JSONObject(responseStr);
            String sucResult = response.getString("state").trim();
            if (sucResult.equals(MsgResult.RESULT_SUCCESS)) {
                JSONObject jsonObject = response
                        .getJSONObject("model");
                User user = (User) JsonUtils.fromJsonToJava(jsonObject, User.class);
                Message message = new Message();
                message.what = LOGIN_SUC;
                message.obj = user;
                handler.sendMessage(message);
            } else {
                handler.sendEmptyMessage(LOGIN_FAIL);
            }
        } catch (JSONException e) {
            handler.sendEmptyMessage(LOGIN_EXCEPTION);
        }
    }

}
