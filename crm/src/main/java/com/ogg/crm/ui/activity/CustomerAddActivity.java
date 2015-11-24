package com.ogg.crm.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.CustomerInfoCategory;
import com.ogg.crm.network.logic.CustomerLogic;
import com.ogg.crm.ui.view.CustomProgressDialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * 登录界面
 */
public class CustomerAddActivity extends Activity implements OnClickListener,
        TextWatcher {
    private Context mContext;

    private ImageView mBackIv;

    private View mLayout1, mLayout2, mLayout3;

    private EditText mNameEt;
    private EditText mJobPostionEt;
    private EditText mMobilePhoneEt;
    private EditText mTelPhoneEt;
    private EditText mQQEt;
    private EditText mEmailEt;
    private RelativeLayout mTypeRl;
    private TextView mTypeTv;

    private EditText mCompanyNameEt;
    private EditText mCompanyAddressEt;
    private EditText mMainProductEt;
    private EditText mCompanyNetEt;
    private EditText mInboundChannelEt;
    private RelativeLayout mProviceCityRl;
    private TextView mProviceCityTv;
    private RelativeLayout mCompanyTypeRl;
    private TextView mCompanyTypeTv;

    private EditText mPreBuyProductEt;
    private EditText mProducingAreaEt;
    private EditText mStandardEt;
    private EditText mNumberEt;
    private EditText mSettlementTypeEt;
    private EditText mCustomerAccountEt;
    private EditText mIsHasLogEt;
    private EditText mRemarkEt;
    private RelativeLayout mLastSettlementTimeRl;
    private TextView mLastSettlementTimeTv;

    private Button mNextBtn;

    private boolean isView1Load = false;
    private boolean isView2Load = false;
    private boolean isView3Load = false;

    private int mNowPage = 0;

    private String[] mCategorys = {"CUSTOMER_TYPE_B", "COMPANY_TYPE_B", "FOLLOW_STATUS", "CUS_LEVEL", "TRADE_FLG"};
    private HashMap<String, ArrayList<CustomerInfoCategory>> mCategoryInfoMap = new HashMap<>();

    private String mProviceCode;
    private String mCityCode;

    private CustomProgressDialog mProgressDialog;

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
                            mCategoryInfoMap.put(categoryKey, ciCateList);
                        }
                    }
                    break;
                }
                case CustomerLogic.CONF_INFO_GET_FAIL: {
                    Toast.makeText(mContext, "获取数据失败!",
                            Toast.LENGTH_SHORT).show();
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
            if (null != mProgressDialog && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = CustomerAddActivity.this;
        initView();
        initData();
    }

    protected void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLayout1 = inflater.inflate(R.layout.customer_info_add, null);
        mLayout2 = inflater.inflate(R.layout.customer_company_info_add, null);
        mLayout3 = inflater.inflate(R.layout.customer_settlement_info_add, null);

        setView1();
    }

    private void setView1() {
        setContentView(mLayout1);

        mBackIv = (ImageView) findViewById(R.id.customer_info_add_back_iv);
        mBackIv.setOnClickListener(this);

        mNameEt = (EditText) findViewById(R.id.customer_info_add_name_et);
        mJobPostionEt = (EditText) findViewById(R.id.customer_info_add_job_position_et);
        mMobilePhoneEt = (EditText) findViewById(R.id.customer_info_add_mphone_et);
        mTelPhoneEt = (EditText) findViewById(R.id.customer_info_add_telephone_et);
        mQQEt = (EditText) findViewById(R.id.customer_info_add_user_qq_et);
        mEmailEt = (EditText) findViewById(R.id.customer_info_add_user_email_et);
        mTypeRl = (RelativeLayout) findViewById(R.id.customer_info_add_type_rl);
        mTypeTv = (TextView) findViewById(R.id.customer_info_add_type_tv);

        mNameEt.addTextChangedListener(this);
        mJobPostionEt.addTextChangedListener(this);
        mMobilePhoneEt.addTextChangedListener(this);
        mTelPhoneEt.addTextChangedListener(this);
        mQQEt.addTextChangedListener(this);
        mEmailEt.addTextChangedListener(this);
        mTypeRl.setOnClickListener(this);


        mNextBtn = (Button) findViewById(R.id.customer_info_add_next_btn);
        mNextBtn.setOnClickListener(this);
        //mNextBtn.setClickable(false);

        isView1Load = true;
    }

    private void setView2() {
        setContentView(mLayout2);

        mBackIv = (ImageView) findViewById(R.id.customer_add_company_back_iv);
        mBackIv.setOnClickListener(this);

        mCompanyNameEt = (EditText) findViewById(R.id.customer_add_company_name_et);
        mCompanyAddressEt = (EditText) findViewById(R.id.customer_add_company_address_et);
        mMainProductEt = (EditText) findViewById(R.id.customer_add_main_product_et);
        mCompanyNetEt = (EditText) findViewById(R.id.customer_add_company_net_et);
        mInboundChannelEt = (EditText) findViewById(R.id.customer_add_inbound_channel_et);
        mCompanyTypeRl = (RelativeLayout) findViewById(R.id.customer_add_company_type_rl);
        mCompanyTypeTv = (TextView) findViewById(R.id.customer_add_company_type_tv);
        mProviceCityRl = (RelativeLayout) findViewById(R.id.customer_add_company_area_rl);
        mProviceCityTv = (TextView) findViewById(R.id.customer_add_company_area_tv);

        mCompanyNameEt.addTextChangedListener(this);
        mCompanyAddressEt.addTextChangedListener(this);
        mMainProductEt.addTextChangedListener(this);
        mCompanyNetEt.addTextChangedListener(this);
        mInboundChannelEt.addTextChangedListener(this);

        mCompanyTypeRl.setOnClickListener(this);
        mProviceCityRl.setOnClickListener(this);

        mNextBtn = (Button) findViewById(R.id.customer_company_info_add_next_btn);
        mNextBtn.setOnClickListener(this);
        //mNextBtn.setClickable(false);

        isView3Load = true;
    }

    private void setView3() {
        setContentView(mLayout3);

        mBackIv = (ImageView) findViewById(R.id.customer_add_settlement_back_iv);
        mBackIv.setOnClickListener(this);

        mPreBuyProductEt = (EditText) findViewById(R.id.customer_add_pre_buy_product_et);
        mProducingAreaEt = (EditText) findViewById(R.id.customer_add_producing_area_et);
        mStandardEt = (EditText) findViewById(R.id.customer_add_standard_et);
        mNumberEt = (EditText) findViewById(R.id.customer_add_number_et);
        mSettlementTypeEt = (EditText) findViewById(R.id.customer_add_settlement_type_et);
        mCustomerAccountEt = (EditText) findViewById(R.id.customer_add_customer_account_et);
        mIsHasLogEt = (EditText) findViewById(R.id.customer_add_is_has_log_et);
        mRemarkEt = (EditText) findViewById(R.id.customer_add_remark_et);
        mLastSettlementTimeRl = (RelativeLayout) findViewById(R.id.customer_add_last_settlement_time_rl);
        mLastSettlementTimeTv = (TextView) findViewById(R.id.customer_add_last_settlement_time_tv);

        mPreBuyProductEt.addTextChangedListener(this);
        mProducingAreaEt.addTextChangedListener(this);
        mStandardEt.addTextChangedListener(this);
        mNumberEt.addTextChangedListener(this);
        mSettlementTypeEt.addTextChangedListener(this);
        mCustomerAccountEt.addTextChangedListener(this);
        mIsHasLogEt.addTextChangedListener(this);
        mRemarkEt.addTextChangedListener(this);
        mLastSettlementTimeRl.setOnClickListener(this);

        mNextBtn = (Button) findViewById(R.id.customer_settlement_info_add_next_btn);
        mNextBtn.setOnClickListener(this);
        //mNextBtn.setClickable(false);

        isView2Load = true;
    }

    private void initData() {
        for (int i = 0; i < mCategorys.length; i++) {
            CustomerLogic.getConfInfo(mContext, mCategoryHandler, mCategorys[i]);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @SuppressLint("NewApi")
    @Override
    public void afterTextChanged(Editable s) {
        String name = mNameEt.getText().toString().trim();
        String tel = mTelPhoneEt.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(tel)) {
            mNextBtn.setClickable(true);
            mNextBtn.setBackgroundResource(R.drawable.corners_bg_red_all);
        } else {
            mNextBtn.setClickable(false);
            mNextBtn.setBackgroundResource(R.drawable.corners_bg_gray_all);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 500: {
                    mTypeTv.setText(data.getStringExtra("area"));
                    mProviceCode = data.getStringExtra("proviceCode");
                    mCityCode = data.getStringExtra("cityCode");
                    break;
                }
                case 501: {
                    mLastSettlementTimeTv.setText(data.getStringExtra("date"));
                    break;
                }
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customer_info_add_back_iv: {
                finish();
                break;
            }

            case R.id.customer_info_add_next_btn: {
                setView2();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }

            case R.id.customer_company_info_add_next_btn: {
                setView3();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }

            case R.id.customer_settlement_info_add_next_btn: {

                break;
            }
            case R.id.customer_info_add_type_rl: {
                break;
            }
            case R.id.customer_add_company_area_rl: {
                //            AddressLogic.getAddressData(mContext,mCategoryHandler);
                Intent intent = new Intent(CustomerAddActivity.this, AddressEditSelectActivity.class);
                startActivityForResult(intent, 500);
                break;
            }
            case R.id.customer_add_last_settlement_time_rl: {
                //            AddressLogic.getAddressData(mContext,mCategoryHandler);
                Intent intent = new Intent(CustomerAddActivity.this, DateSelectActivity.class);
                startActivityForResult(intent, 501);
                break;
            }


            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            CustomerAddActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
