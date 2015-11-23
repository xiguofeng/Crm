package com.ogg.crm.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
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

    private ImageView mSeePwdIv;

    private EditText mNameEt;
    private EditText mJobPostionEt;
    private EditText mMobilePhoneEt;
    private EditText mTelPhoneEt;
    private EditText mQQEt;
    private EditText mEmailEt;
    private EditText mTypeEt;

    private EditText mPreBuyProductEt;
    private EditText mProducingAreaEt;
    private EditText mStandardEt;
    private EditText mNumberEt;
    private EditText mSettlementTypeEt;
    private EditText mCustomerAccountEt;
    private EditText mIsHasLogEt;
    private EditText mLastSettlementTimeEt;
    private EditText mRemarkEt;

    private EditText mCompanyNameEt;
    private EditText mCompanyAddressEt;
    private EditText mCompanyTypeEt;
    private EditText mMainProductEt;
    private EditText mCustomerAreaEt;
    private EditText mCompanyNetEt;
    private EditText mInboundChannelEt;


    private Button mNextBtn;

    private String mNowAction = ORIGIN_FROM_NULL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info_add);
        mContext = CustomerAddActivity.this;
        initView();

    }

    protected void initView() {
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
        mNextBtn.setClickable(false);

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
                Intent intent = new Intent(CustomerAddActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
