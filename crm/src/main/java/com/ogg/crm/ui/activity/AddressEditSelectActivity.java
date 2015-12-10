package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.AddressData;
import com.ogg.crm.network.logic.AddressLogic;
import com.ogg.crm.service.ConfigInfoService;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.ui.view.wheel.widget.OnWheelChangedListener;
import com.ogg.crm.ui.view.wheel.widget.WheelView;
import com.ogg.crm.ui.view.wheel.widget.adapters.ArrayWheelAdapter;
import com.ogg.crm.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddressEditSelectActivity extends Activity implements
        OnClickListener, OnWheelChangedListener {
    private WheelView mViewProvince;
    private WheelView mViewCity;

    private TextView mCancelTv;
    private TextView mConfirmTv;

    private Context mContext;

    /**
     * 所有省
     */
    protected AddressData[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<AddressData, AddressData[]> mCitisDatasMap = new HashMap<AddressData, AddressData[]>();


    /**
     * key - 区 values - 邮编
     */
    protected Map<AddressData, AddressData> mZipcodeDatasMap = new HashMap<AddressData, AddressData>();

    /**
     * 当前省的名称
     */
    protected AddressData mCurrentProvice;
    /**
     * 当前市的名称
     */
    protected AddressData mCurrentCity;


    private String mAddressData;

    private CustomProgressDialog mProgressDialog;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case AddressLogic.ANDRESS_DATA_GET_SUC: {
                    if (null != msg.obj) {
                        mAddressData = (String) msg.obj;
                        parserData(mAddressData);
                    }
                    break;
                }
                case AddressLogic.ANDRESS_DATA_GET_FAIL: {
                    break;
                }
                case AddressLogic.ANDRESS_DATA_GET_EXCEPTION: {
                    break;
                }
                case AddressLogic.NET_ERROR: {
                    break;
                }
                default:
                    break;
            }
            if (null != mProgressDialog && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_select);
        mContext = AddressEditSelectActivity.this;
        mProgressDialog = new CustomProgressDialog(mContext);
        initData();
    }

    private void setUpViews() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.5
        p.width = (int) (d.getWidth()); // 宽度设置为屏幕的宽度
        // p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.6f;
        getWindow().addFlags(LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(p);
        getWindow().setGravity(Gravity.BOTTOM); // 设置靠底部

        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);

        mConfirmTv = (TextView) findViewById(R.id.address_select_confirm_tv);
        mCancelTv = (TextView) findViewById(R.id.address_select_cancel_tv);
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加onclick事件
        mConfirmTv.setOnClickListener(this);
        mCancelTv.setOnClickListener(this);
    }

    private void initData() {
//        String addressData = getIntent().getStringExtra("addressData");
//        if (!TextUtils.isEmpty(addressData)) {
//            parserData(addressData);
//        }
        if (!TextUtils.isEmpty(ConfigInfoService.sAddressData)) {
            mAddressData = ConfigInfoService.sAddressData;
            parserData(mAddressData);
        } else {
            mProgressDialog.show();
            AddressLogic.getAddressData(mContext, mHandler);
        }
    }

    private void parserData(String data) {
        try {
            JSONArray provinceJsonArray = new JSONArray(data);
            mProvinceDatas = new AddressData[provinceJsonArray.length()];
            for (int i = 0; i < provinceJsonArray.length(); i++) {
                JSONObject allDataJsonObject = provinceJsonArray
                        .getJSONObject(i);

                JSONObject provinceJsonObject = allDataJsonObject.getJSONObject("province");
                AddressData provinceAddressData = (AddressData) JsonUtils
                        .fromJsonToJava(provinceJsonObject, AddressData.class);
                mProvinceDatas[i] = provinceAddressData;

                JSONArray citisJsonArray = allDataJsonObject
                        .getJSONArray("city");
                AddressData[] citisDatas = new AddressData[citisJsonArray
                        .length()];
                for (int j = 0; j < citisJsonArray.length(); j++) {
                    JSONObject cityJsonObject = citisJsonArray.getJSONObject(j);
                    AddressData cityAddressData = (AddressData) JsonUtils
                            .fromJsonToJava(cityJsonObject, AddressData.class);
                    citisDatas[j] = cityAddressData;
                }
                mCitisDatasMap.put(provinceAddressData, citisDatas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUpViews();
        setUpListener();
        setUpData();
    }

    private void setUpData() {
        mCurrentProvice = mProvinceDatas[0];
        if (mCurrentProvice != null) {
            mCurrentCity = mCitisDatasMap.get(mCurrentProvice)[0];
        }

        // initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<AddressData>(
                AddressEditSelectActivity.this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCity = mCitisDatasMap.get(mCurrentProvice)[pCurrent];
        //AddressData[] areas = mDistrictDatasMap.get(mCurrentCity);
//        if (areas == null) {
//            areas = new AddressData[]{};
//        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProvice = mProvinceDatas[pCurrent];
        AddressData[] cities = mCitisDatasMap.get(mCurrentProvice);
        if (cities == null) {
            cities = new AddressData[]{};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<AddressData>(this,
                cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_select_confirm_tv: {
                Intent intent = new Intent();
                intent.putExtra("area", "" + mCurrentProvice.getRegionName() + ","
                        + mCurrentCity.getRegionName());
                intent.putExtra("provice", "" + mCurrentProvice.getRegionName());
                intent.putExtra("city", "" + mCurrentCity.getRegionName());
                intent.putExtra("proviceCode", mCurrentProvice.getRegionId());
                intent.putExtra("cityCode", mCurrentCity.getRegionId());
                setResult(RESULT_OK, intent);
                finish();
                showSelectedResult();
                break;
            }
            case R.id.address_select_cancel_tv: {
                finish();
                break;
            }
            default:
                break;
        }
    }

    private void showSelectedResult() {
        Toast.makeText(
                AddressEditSelectActivity.this,
                "当前选中:" + mCurrentProvice.getRegionName() + ","
                        + mCurrentCity.getRegionName(), Toast.LENGTH_SHORT)
                .show();
    }
}
