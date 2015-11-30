package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.Customer;
import com.ogg.crm.network.logic.AppointmentLogic;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.utils.ActivitiyInfoManager;

public class CustomerDetailActivity extends Activity implements OnClickListener {

    public static final String CUSTOMER_KEY = "customer_key";

    public static final String MY_ACTION = "my_action";

    public static final String PUBLIC_ACTION = "public_action";

    private Context mContext;

    private ImageView mBackIv;

    private TextView mNameTv;
    private TextView mJobPostionTv;
    private TextView mMobilePhoneTv;
    private TextView mTelPhoneTv;
    private TextView mQQTv;
    private TextView mEmailTv;
    private TextView mTypeTv;
    private TextView mLevelTv;

    private TextView mCompanyNameTv;
    private TextView mCompanyAddressTv;
    private TextView mMainProductTv;
    private TextView mCompanyNetTv;
    private TextView mInboundChannelTv;
    private TextView mProviceCityTv;
    private TextView mCompanyTypeTv;

    private TextView mPreBuyProductTv;
    private TextView mProducingAreaTv;
    private TextView mStandardTv;
    private TextView mNumberTv;
    private TextView mSettlementTypeTv;
    private TextView mCustomerAccountTv;
    private TextView mIsHasLogTv;
    private TextView mRemarkTv;
    private TextView mLastSettlementTimeTv;
    private TextView mFollowStateTv;
    private TextView mFollowRecordTv;

    private Button mChangeStateBtn;

    private CustomProgressDialog mProgressDialog;

    private Customer mCustomer;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case AppointmentLogic.STATE_SET_SUC: {
                    Toast.makeText(mContext, "已完成!", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
                case AppointmentLogic.STATE_SET_FAIL: {
                    break;
                }
                case AppointmentLogic.STATE_SET_EXCEPTION: {
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
        setContentView(R.layout.customer_detail);
        mContext = CustomerDetailActivity.this;
        if (!ActivitiyInfoManager.activitityMap
                .containsKey(ActivitiyInfoManager
                        .getCurrentActivityName(mContext))) {
            ActivitiyInfoManager.activitityMap
                    .put(ActivitiyInfoManager.getCurrentActivityName(mContext),
                            this);
        }
        initView();
        initData();

    }

    private void initView() {
        mBackIv = (ImageView) findViewById(R.id.customer_detail_back_iv);
        mBackIv.setOnClickListener(this);

        mNameTv = (TextView) findViewById(R.id.customer_detail_name_tv);
        mMobilePhoneTv = (TextView) findViewById(R.id.customer_detail_mobilephone_tv);
        mTelPhoneTv = (TextView) findViewById(R.id.customer_detail_telephone_tv);
        mQQTv = (TextView) findViewById(R.id.customer_detail_qq_tv);
        mEmailTv = (TextView) findViewById(R.id.customer_detail_email_tv);
        mTypeTv = (TextView) findViewById(R.id.customer_detail_customer_type_tv);
        mLevelTv = (TextView) findViewById(R.id.customer_detail_customer_level_tv);

        mCompanyNameTv = (TextView) findViewById(R.id.customer_detail_company_name_tv);
        mCompanyAddressTv = (TextView) findViewById(R.id.customer_detail_company_address_tv);
        mMainProductTv = (TextView) findViewById(R.id.customer_detail_main_product_tv);
        mCompanyNetTv = (TextView) findViewById(R.id.customer_detail_company_net_tv);
        mInboundChannelTv = (TextView) findViewById(R.id.customer_detail_inbound_channel_tv);
        mProviceCityTv = (TextView) findViewById(R.id.customer_detail_province_city_tv);
        mCompanyTypeTv = (TextView) findViewById(R.id.customer_detail_company_type_tv);

        mPreBuyProductTv = (TextView) findViewById(R.id.customer_detail_pre_buy_product_tv);
        mProducingAreaTv = (TextView) findViewById(R.id.customer_detail_producing_area_tv);
        mStandardTv = (TextView) findViewById(R.id.customer_detail_standard_tv);
        mNumberTv = (TextView) findViewById(R.id.customer_detail_number_tv);
        mSettlementTypeTv = (TextView) findViewById(R.id.customer_detail_settlement_type_tv);
        mCustomerAccountTv = (TextView) findViewById(R.id.customer_detail_customer_account_tv);
        mIsHasLogTv = (TextView) findViewById(R.id.customer_detail_is_has_log_tv);
        mLastSettlementTimeTv = (TextView) findViewById(R.id.customer_detail_last_settlement_time_tv);
        mFollowStateTv = (TextView) findViewById(R.id.customer_detail_follow_state_tv);
        mFollowRecordTv = (TextView) findViewById(R.id.customer_detail_follow_record_tv);
    }

    private void initData() {
        mProgressDialog = new CustomProgressDialog(mContext);
        //mProgressDialog.show();
        mCustomer = (Customer) getIntent().getSerializableExtra(CUSTOMER_KEY);
        if (null != mCustomer) {
            fillUpData();
        }
    }

    private void fillUpData() {
        mNameTv.setText(mCustomer.getName());
        mMobilePhoneTv.setText(mCustomer.getMobile());
        mTelPhoneTv.setText(mCustomer.getTel());
        mQQTv.setText(mCustomer.getQq());
        mEmailTv.setText(mCustomer.getEmail());
        mTypeTv.setText(mCustomer.getCustomerType());
        mLevelTv.setText(mCustomer.getCusLevel());

        mCompanyNameTv.setText(mCustomer.getCompanyName());
        mCompanyAddressTv.setText(mCustomer.getAddress());
        mMainProductTv.setText(mCustomer.getMainProduct());
        mCompanyNetTv.setText(mCustomer.getUrl());
        mInboundChannelTv.setText(mCustomer.getStockWay());
        mProviceCityTv.setText(mCustomer.getProvince() + mCustomer.getCity());
        mCompanyTypeTv.setText(mCustomer.getCompanyType());

        mPreBuyProductTv.setText(mCustomer.getKind());
        mProducingAreaTv.setText(mCustomer.getPlace());
        mStandardTv.setText(mCustomer.getNorms());
        mNumberTv.setText(mCustomer.getAmount());
        mSettlementTypeTv.setText(mCustomer.getAccount());
        mCustomerAccountTv.setText(mCustomer.getAccountNum());
        mIsHasLogTv.setText(mCustomer.getIsExpire());
        mLastSettlementTimeTv.setText(mCustomer.getLastestTradeTime());
        mFollowStateTv.setText(mCustomer.getFollowStatus());
        mFollowRecordTv.setText(mCustomer.getTotalFollowStatus());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customer_detail_back_iv: {
                finish();
                break;
            }

            default: {
                break;
            }
        }
    }
}
