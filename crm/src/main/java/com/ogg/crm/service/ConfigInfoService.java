package com.ogg.crm.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.ogg.crm.entity.CustomerInfoCategory;
import com.ogg.crm.network.logic.AddressLogic;
import com.ogg.crm.network.logic.CustomerLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ConfigInfoService extends Service {

    public static final int TIME_UPDATE = 1;

    private Context mContext;

    public static String[] mCategorys = {"CUSTOMER_TYPE_B", "COMPANY_TYPE_B", "FOLLOW_STATUS", "CUS_LEVEL", "TRADE_FLG"};
    public static HashMap<String, ArrayList<CustomerInfoCategory>> sCustomerCategoryInfoMap = new HashMap<>();

    public static String sAddressData;

    Handler mCategoryHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case CustomerLogic.CONF_INFO_GET_SUC: {
                    if (null != msg.obj) {
                        ArrayList<CustomerInfoCategory> ciCateList = new ArrayList<>();
                        ciCateList.addAll((Collection<? extends CustomerInfoCategory>) msg.obj);
                        if (null != msg.getData()) {
                            String categoryKey = msg.getData().getString("category");
                            sCustomerCategoryInfoMap.put(categoryKey, ciCateList);
                        }
                    }
                    break;
                }
                case CustomerLogic.CONF_INFO_GET_FAIL: {
                    break;
                }
                case CustomerLogic.CONF_INFO_GET_EXCEPTION: {
                    break;
                }
                case CustomerLogic.NET_ERROR: {
                    break;
                }
                default:
                    break;
            }

        }

    };

    Handler mAddressHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case AddressLogic.ANDRESS_DATA_GET_SUC: {
                    if (null != msg.obj) {
                        sAddressData = (String) msg.obj;
                    }
                    break;
                }
                case AddressLogic.ANDRESS_DATA_GET_FAIL: {
                    AddressLogic.getAddressData(mContext, mAddressHandler);
                    break;
                }
                case AddressLogic.ANDRESS_DATA_GET_EXCEPTION: {
                    AddressLogic.getAddressData(mContext, mAddressHandler);
                    break;
                }
                case AddressLogic.NET_ERROR: {
                    break;
                }
                default:
                    break;
            }


        }

    };


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sCustomerCategoryInfoMap.clear();
        for (int i = 0; i < mCategorys.length; i++) {
            CustomerLogic.getConfInfo(mContext, mCategoryHandler, mCategorys[i]);
        }

        sAddressData = "";
        AddressLogic.getAddressData(mContext, mAddressHandler);

        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
