package com.ogg.crm.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

import com.ogg.crm.R;

/**
 * 登录界面
 */
public class CustomerAddActivity extends Activity implements OnClickListener,
        TextWatcher {
    public static final String ORIGIN_FROM_NULL = "com.null";

    public static final String ORIGIN_FROM_REG_KEY = "com.reg";

    public static final String ORIGIN_FROM_CART_KEY = "com.cart";

    public static final String ORIGIN_FROM_GOODS_DETAIL_KEY = "com.goods.detail";

    public static final String ORIGIN_FROM_ORDER_KEY = "com.order";

    public static final String ORIGIN_FROM_USER_KEY = "com.user";

    private Context mContext;

    private ImageView mBackIv;

    private View mLayout1, mLayout2, mLayout3;

    private EditText mNameEt;
    private EditText mJobPostionEt;
    private EditText mMobilePhoneEt;
    private EditText mTelPhoneEt;
    private EditText mQQEt;
    private EditText mEmailEt;
    private EditText mTypeEt;

    private EditText mCompanyNameEt;
    private EditText mCompanyAddressEt;
    private EditText mCompanyTypeEt;
    private EditText mMainProductEt;
    private EditText mCustomerAreaEt;
    private EditText mCompanyNetEt;
    private EditText mInboundChannelEt;

    private EditText mPreBuyProductEt;
    private EditText mProducingAreaEt;
    private EditText mStandardEt;
    private EditText mNumberEt;
    private EditText mSettlementTypeEt;
    private EditText mCustomerAccountEt;
    private EditText mIsHasLogEt;
    private EditText mLastSettlementTimeEt;
    private EditText mRemarkEt;

    private Button mNextBtn;

    private boolean isView1Load = false;
    private boolean isView2Load = false;
    private boolean isView3Load = false;

    private int mNowPage = 0;

    private String mNowAction = ORIGIN_FROM_NULL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = CustomerAddActivity.this;
        initView();

    }

    protected void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        //以上两行功能一样
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
        mTypeEt = (EditText) findViewById(R.id.customer_info_add_type_et);

        mNameEt.addTextChangedListener(this);
        mJobPostionEt.addTextChangedListener(this);
        mMobilePhoneEt.addTextChangedListener(this);
        mTelPhoneEt.addTextChangedListener(this);
        mQQEt.addTextChangedListener(this);
        mEmailEt.addTextChangedListener(this);
        mTypeEt.addTextChangedListener(this);


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
        mCompanyTypeEt = (EditText) findViewById(R.id.customer_add_company_type_et);
        mMainProductEt = (EditText) findViewById(R.id.customer_add_main_product_et);
        mCustomerAreaEt = (EditText) findViewById(R.id.customer_add_customer_area_et);
        mCompanyNetEt = (EditText) findViewById(R.id.customer_add_company_net_et);
        mInboundChannelEt = (EditText) findViewById(R.id.customer_add_inbound_channel_et);

        mCompanyNameEt.addTextChangedListener(this);
        mCompanyAddressEt.addTextChangedListener(this);
        mCompanyTypeEt.addTextChangedListener(this);
        mMainProductEt.addTextChangedListener(this);
        mCustomerAreaEt.addTextChangedListener(this);
        mCompanyNetEt.addTextChangedListener(this);
        mInboundChannelEt.addTextChangedListener(this);

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
        mLastSettlementTimeEt = (EditText) findViewById(R.id.customer_add_last_settlement_time_et);
        mRemarkEt = (EditText) findViewById(R.id.customer_add_remark_et);

        mPreBuyProductEt.addTextChangedListener(this);
        mProducingAreaEt.addTextChangedListener(this);
        mStandardEt.addTextChangedListener(this);
        mNumberEt.addTextChangedListener(this);
        mSettlementTypeEt.addTextChangedListener(this);
        mCustomerAccountEt.addTextChangedListener(this);
        mIsHasLogEt.addTextChangedListener(this);
        mLastSettlementTimeEt.addTextChangedListener(this);
        mRemarkEt.addTextChangedListener(this);

        mNextBtn = (Button) findViewById(R.id.customer_settlement_info_add_next_btn);
        mNextBtn.setOnClickListener(this);
        //mNextBtn.setClickable(false);

        isView2Load = true;
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
