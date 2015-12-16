package com.ogg.crm.network.logic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.ogg.crm.entity.Appointment;
import com.ogg.crm.network.config.MsgResult;
import com.ogg.crm.network.config.RequestUrl;
import com.ogg.crm.network.utils.OkHttpUtil;
import com.ogg.crm.utils.JsonUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class NewAppointmentLogic {

    public static final int NET_ERROR = 0;

    public static final int LIST_GET_SUC = NET_ERROR + 1;

    public static final int LIST_GET_FAIL = LIST_GET_SUC + 1;

    public static final int LIST_GET_EXCEPTION = LIST_GET_FAIL + 1;

    public static final int STATE_SET_SUC = LIST_GET_EXCEPTION + 1;

    public static final int STATE_SET_FAIL = STATE_SET_SUC + 1;

    public static final int STATE_SET_EXCEPTION = STATE_SET_FAIL + 1;

    public static void getList(final Context context, final Handler handler,
                               final String userId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = RequestUrl.HOST_URL + RequestUrl.appointment.list;

                RequestBody formBody = new FormEncodingBuilder()
                        .add("userId", userId)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                Response response = null;
                try {
                    response = OkHttpUtil.execute(request);
                    if (null != response && !TextUtils.isEmpty(response.body().string())) {
                        Log.e("xxx_NewAppointment_list", ":" + response.body().string());
                        parseListData(response.body().string(), handler);
                    } else {
                        handler.sendEmptyMessage(LIST_GET_FAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormEncodingBuilder()
                .add("user", "Jurassic Park")
                .add("pass", "asasa")
                .add("time", "12132")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
