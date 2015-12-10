package com.ogg.crm.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.Customer;
import com.ogg.crm.entity.CustomerInfoCategory;
import com.ogg.crm.network.logic.CustomerLogic;
import com.ogg.crm.service.ConfigInfoService;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.utils.JsonUtils;
import com.ogg.crm.utils.PhoneUtils;
import com.ogg.crm.utils.TimeUtils;
import com.ogg.crm.utils.UserInfoManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;


public class CustomerAddActivity extends Activity implements OnClickListener,
        TextWatcher {

    public static final String ORIGIN_FROM_ADD_KEY = "com.add";

    public static final String ORIGIN_FROM_UPDATE_KEY = "com.update";

    private TextView mTitleTv;

    private Context mContext;

    private ImageView mBackIv;

    private View mLayout1, mLayout2, mLayout3;

    private TextView mPhoneStarHintTv;
    private TextView mMobileStarHintTv;
    private TextView mQQStarHintTv;

    private EditText mNameEt;
    private EditText mJobPostionEt;
    private EditText mMobilePhoneEt;
    private EditText mTelPhoneEt;
    private EditText mQQEt;
    private EditText mEmailEt;
    private RelativeLayout mTypeRl;
    private TextView mTypeTv;
    private RelativeLayout mLevelRl;
    private TextView mLevelTv;

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
    private EditText mRemarkEt;
    private RelativeLayout mLastSettlementTimeRl;
    private TextView mLastSettlementTimeTv;
    private CheckBox mIsHasLogCb;

    private Button mNextBtn;
    private Button mCompanyPreBtn;
    private Button mCompanyNextBtn;
    private Button mSettlementPreBtn;
    private Button mSaveBtn;

    private boolean isView1Load = false;
    private boolean isView2Load = false;
    private boolean isView3Load = false;

    private int mNowPage = 0;

    private String[] mCategorys = {"CUSTOMER_TYPE_B", "COMPANY_TYPE_B", "FOLLOW_STATUS", "CUS_LEVEL", "TRADE_FLG"};
    public static HashMap<String, ArrayList<CustomerInfoCategory>> sCategoryInfoMap = new HashMap<>();

    private String mProviceCode;
    private String mCityCode;
    private String mLevel;
    private String mType;
    private String mCompanyType;
    private String mIsHasLog = "0";
    private String mLastTradeTime;
    private String mProvice;
    private String mCity;
    private Date mLastTradeDate;

    private Customer mCustomer;

    private String mNowAction = ORIGIN_FROM_ADD_KEY;

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
                            sCategoryInfoMap.put(categoryKey, ciCateList);
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

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case CustomerLogic.SAVE_SET_SUC: {
                    CustomerListActivity.isRefresh = true;
                    Toast.makeText(mContext, "保存客户数据成功!",
                            Toast.LENGTH_SHORT).show();

                    CustomerDetailActivity.sCustomer = mCustomer;

                    //ActivitiyInfoManager.finishActivity("com.ogg.crm.ui.activity.CustomerDetailActivity");

//                    Intent intent = new Intent(CustomerAddActivity.this,
//                            CustomerDetailActivity.class);
//                    intent.setAction(CustomerDetailActivity.UPDATE_COMPLETE_ACTION);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable(CustomerDetailActivity.CUSTOMER_KEY,
//                            mCustomer);
//                    intent.putExtras(bundle);
//                    startActivity(intent);

                    //ActivitiyInfoManager.finishActivity("com.ogg.crm.ui.activity.CustomerAddActivity");
                    //overridePendingTransition(R.anim.push_right_in, R.anim.push_down_out);
                    CustomerAddActivity.this.finish();
                    break;
                }
                case CustomerLogic.SAVE_SET_FAIL: {
                    if (null != msg.obj) {
                        Toast.makeText(mContext, (String) msg.obj,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "保存客户数据失败!",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case CustomerLogic.SAVE_SET_EXCEPTION: {
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
//        if (!ActivitiyInfoManager.activitityMap
//                .containsKey(ActivitiyInfoManager
//                        .getCurrentActivityName(mContext))) {
//            ActivitiyInfoManager.activitityMap
//                    .put(ActivitiyInfoManager.getCurrentActivityName(mContext),
//                            this);
//        }
        mProgressDialog = new CustomProgressDialog(mContext);
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

        mTitleTv = (TextView) findViewById(R.id.customer_info_add_title_tv);
        if (mNowAction.equals(ORIGIN_FROM_UPDATE_KEY)) {
            mTitleTv.setText(getString(R.string.update_customer_info));
        }

        mBackIv = (ImageView) findViewById(R.id.customer_info_add_back_iv);
        mBackIv.setOnClickListener(this);

        mPhoneStarHintTv = (TextView) findViewById(R.id.customer_info_add_phone_star_tv);
        mMobileStarHintTv = (TextView) findViewById(R.id.customer_info_add_mphone_star_tv);
        mQQStarHintTv = (TextView) findViewById(R.id.customer_info_add_qq_star_tv);

        mNameEt = (EditText) findViewById(R.id.customer_info_add_name_et);
        mJobPostionEt = (EditText) findViewById(R.id.customer_info_add_job_position_et);
        mMobilePhoneEt = (EditText) findViewById(R.id.customer_info_add_mphone_et);
        mTelPhoneEt = (EditText) findViewById(R.id.customer_info_add_telephone_et);
        mQQEt = (EditText) findViewById(R.id.customer_info_add_user_qq_et);
        mEmailEt = (EditText) findViewById(R.id.customer_info_add_user_email_et);
        mTypeRl = (RelativeLayout) findViewById(R.id.customer_info_add_type_rl);
        mTypeTv = (TextView) findViewById(R.id.customer_info_add_type_tv);
        mLevelRl = (RelativeLayout) findViewById(R.id.customer_info_add_level_rl);
        mLevelTv = (TextView) findViewById(R.id.customer_info_add_level_tv);

        mNameEt.addTextChangedListener(this);
        mJobPostionEt.addTextChangedListener(this);
        mMobilePhoneEt.addTextChangedListener(this);
        mTelPhoneEt.addTextChangedListener(this);
        mQQEt.addTextChangedListener(this);
        mEmailEt.addTextChangedListener(this);
        mTypeRl.setOnClickListener(this);
        mLevelRl.setOnClickListener(this);

        mNextBtn = (Button) findViewById(R.id.customer_info_add_next_btn);
        mNextBtn.setOnClickListener(this);
        if (!isView1Load) {
            mNextBtn.setClickable(false);
        }
        isView1Load = true;
    }

    private void setView2() {
        setContentView(mLayout2);

        mTitleTv = (TextView) findViewById(R.id.customer_add_company_title_tv);
        if (mNowAction.equals(ORIGIN_FROM_UPDATE_KEY)) {
            mTitleTv.setText(getString(R.string.update_customer_info));
        }

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
        mProviceCityTv.addTextChangedListener(this);

        mCompanyTypeRl.setOnClickListener(this);
        mProviceCityRl.setOnClickListener(this);

        mCompanyNextBtn = (Button) findViewById(R.id.customer_company_info_add_next_btn);
        mCompanyNextBtn.setOnClickListener(this);

        if (!isView2Load) {
            mCompanyNextBtn.setClickable(false);
        }

        mCompanyPreBtn = (Button) findViewById(R.id.customer_company_info_add_previous_btn);
        mCompanyPreBtn.setOnClickListener(this);

        isView2Load = true;
    }

    private void setView3() {
        setContentView(mLayout3);

        mTitleTv = (TextView) findViewById(R.id.customer_add_settlement_title_tv);
        if (mNowAction.equals(ORIGIN_FROM_UPDATE_KEY)) {
            mTitleTv.setText(getString(R.string.update_customer_info));
        }

        mBackIv = (ImageView) findViewById(R.id.customer_add_settlement_back_iv);
        mBackIv.setOnClickListener(this);

        mPreBuyProductEt = (EditText) findViewById(R.id.customer_add_pre_buy_product_et);
        mProducingAreaEt = (EditText) findViewById(R.id.customer_add_producing_area_et);
        mStandardEt = (EditText) findViewById(R.id.customer_add_standard_et);
        mNumberEt = (EditText) findViewById(R.id.customer_add_number_et);
        mSettlementTypeEt = (EditText) findViewById(R.id.customer_add_settlement_type_et);
        mCustomerAccountEt = (EditText) findViewById(R.id.customer_add_customer_account_et);
        mRemarkEt = (EditText) findViewById(R.id.customer_add_remark_et);
        mLastSettlementTimeRl = (RelativeLayout) findViewById(R.id.customer_add_last_settlement_time_rl);
        mLastSettlementTimeTv = (TextView) findViewById(R.id.customer_add_last_settlement_time_tv);
        mIsHasLogCb = (CheckBox) findViewById(R.id.customer_add_number_cb);

        mIsHasLogCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView,
                                                                                boolean isChecked) {
                                                       if (isChecked) {
                                                           mIsHasLog = "1";
                                                       } else {
                                                           mIsHasLog = "0";
                                                       }
                                                   }
                                               }

        );
        mPreBuyProductEt.addTextChangedListener(this);
        mProducingAreaEt.addTextChangedListener(this);
        mStandardEt.addTextChangedListener(this);
        mNumberEt.addTextChangedListener(this);
        mSettlementTypeEt.addTextChangedListener(this);
        mCustomerAccountEt.addTextChangedListener(this);
        mRemarkEt.addTextChangedListener(this);
        mLastSettlementTimeRl.setOnClickListener(this);

        mSettlementPreBtn = (Button) findViewById(R.id.customer_settlement_info_add_previous_btn);

        mSettlementPreBtn.setOnClickListener(this);
        mSaveBtn = (Button) findViewById(R.id.customer_settlement_info_add_save_btn);

        mSaveBtn.setOnClickListener(this);

        isView3Load = true;
    }

    private void initData() {
        String action = getIntent().getAction();
        if (!TextUtils.isEmpty(action)) {
            mNowAction = action;
        }
        if (mNowAction.equals(ORIGIN_FROM_UPDATE_KEY)) {
            mCustomer = (Customer) getIntent().getSerializableExtra(CustomerDetailActivity.CUSTOMER_KEY);
            mTitleTv.setText(getString(R.string.update_customer_info));
            fillUpCustomerData();
        } else {
            mCustomer = new Customer();
            mCustomer.setUserId(UserInfoManager.getUserId(mContext));
            mCustomer.setServiceUserId(UserInfoManager.getUserId(mContext));
        }

        if (null != ConfigInfoService.sCustomerCategoryInfoMap && !ConfigInfoService.sCustomerCategoryInfoMap.isEmpty() && ConfigInfoService.sCustomerCategoryInfoMap.size() >= mCategorys.length) {
            sCategoryInfoMap.clear();
            sCategoryInfoMap.putAll(ConfigInfoService.sCustomerCategoryInfoMap);
        } else {
            mProgressDialog.show();
            for (int i = 0; i < mCategorys.length; i++) {
                CustomerLogic.getConfInfo(mContext, mCategoryHandler, mCategorys[i]);
            }
            Intent intentService = new Intent(mContext,
                    ConfigInfoService.class);
            mContext.startService(intentService);
        }


    }

    private void checkInput() {
        String name = mNameEt.getText().toString().trim();
        String tel = mTelPhoneEt.getText().toString().trim();
        String mobile = mMobilePhoneEt.getText().toString().trim();
        String email = mEmailEt.getText().toString().trim();
        String qq = mQQEt.getText().toString().trim();

        if (!TextUtils.isEmpty(mobile)) {
            if (!PhoneUtils.isMobile(mobile)) {
                CharSequence html = Html.fromHtml("<font color='red'>手机号格式不正确</font>");
                mMobilePhoneEt.setError(html);
            }
        }

        if (!TextUtils.isEmpty(tel)) {
            if (!PhoneUtils.isPhone(tel)) {
                CharSequence html = Html.fromHtml("<font color='red'>电话号码格式不正确</font>");
                mTelPhoneEt.setError(html);
            }
        }

        if (!TextUtils.isEmpty(email)) {
            if (!PhoneUtils.isEmail(email)) {
                CharSequence html = Html.fromHtml("<font color='red'>邮箱格式不正确</font>");
                mEmailEt.setError(html);
            }
        }

        mMobileStarHintTv.setVisibility(View.VISIBLE);
        mPhoneStarHintTv.setVisibility(View.VISIBLE);
        mQQStarHintTv.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(tel) || !TextUtils.isEmpty(mobile) || !TextUtils.isEmpty(qq)) {
            if (TextUtils.isEmpty(mobile)) {
                mMobileStarHintTv.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(tel)) {
                mPhoneStarHintTv.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(qq)) {
                mQQStarHintTv.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(name) && (!TextUtils.isEmpty(tel) || !TextUtils.isEmpty(mobile) || !TextUtils.isEmpty(qq))) {
            mNextBtn.setClickable(true);
            mNextBtn.setBackgroundResource(R.drawable.corners_bg_blue_all);
        } else {
            mNextBtn.setClickable(false);
            mNextBtn.setBackgroundResource(R.drawable.corners_bg_gray_all);
        }


        if (null != mCompanyNameEt && null != mProviceCityTv) {
            String companyName = mCompanyNameEt.getText().toString().trim();
            String proviceCity = mProviceCityTv.getText().toString().trim();
            if (!TextUtils.isEmpty(companyName) && !TextUtils.isEmpty(proviceCity)) {
                mCompanyNextBtn.setClickable(true);
                mCompanyNextBtn.setBackgroundResource(R.drawable.corners_bg_blue_all);
            } else {
                mCompanyNextBtn.setClickable(false);
                mCompanyNextBtn.setBackgroundResource(R.drawable.corners_bg_gray_all);
            }
        }
    }

    private void fillUpCustomerData() {
        mNameEt.setText(!"null".equals(mCustomer.getName()) ? mCustomer.getName().trim() : "");
        mJobPostionEt.setText(!"null".equals(mCustomer.getPosition()) ? mCustomer.getPosition().trim() : "");
        mMobilePhoneEt.setText(!"null".equals(mCustomer.getMobile()) ? mCustomer.getMobile().trim() : "");
        mTelPhoneEt.setText(!"null".equals(mCustomer.getTel()) ? mCustomer.getTel().trim() : "");
        mQQEt.setText(!"null".equals(mCustomer.getQq()) ? mCustomer.getQq().trim() : "");
        mEmailEt.setText(!"null".equals(mCustomer.getEmail()) ? mCustomer.getEmail().trim() : "");
        mTypeTv.setText(!"null".equals(mCustomer.getCustomerTypeDesc()) ? mCustomer.getCustomerTypeDesc().trim() : "");
        mLevelTv.setText(!"null".equals(mCustomer.getCusLevelDesc()) ? mCustomer.getCusLevelDesc().trim() : "");

        if (!"null".equals(mCustomer.getCusLevel())) {
            mLevel = mCustomer.getCusLevel();
        }
        if (!"null".equals(mCustomer.getCustomerType())) {
            mType = mCustomer.getCustomerType();
        }

        mNameEt.requestFocus();
    }

    private void fillUpCompanyData() {
        mCompanyNameEt.setText(!"null".equals(mCustomer.getCompanyName()) ? mCustomer.getCompanyName().trim() : "");
        mCompanyAddressEt.setText(!"null".equals(mCustomer.getAddress()) ? mCustomer.getAddress().trim() : "");
        mMainProductEt.setText(!"null".equals(mCustomer.getMainProduct()) ? mCustomer.getMainProduct().trim() : "");
        mCompanyNetEt.setText(!"null".equals(mCustomer.getUrl()) ? mCustomer.getUrl().trim() : "");
        mInboundChannelEt.setText(!"null".equals(mCustomer.getStockWay()) ? mCustomer.getStockWay().trim() : "");
        String province = !"null".equals(mCustomer.getProvinceName()) ? mCustomer.getProvinceName().trim() : "";
        String city = !"null".equals(mCustomer.getCityName()) ? mCustomer.getCityName().trim() : "";
        mProviceCityTv.setText(province + city);
        mCompanyTypeTv.setText(!"null".equals(mCustomer.getCompanyTypeDesc()) ? mCustomer.getCompanyTypeDesc().trim() : "");

        if (!"null".equals(mCustomer.getProvince())) {
            mProviceCode = mCustomer.getProvince();
        }
        if (!"null".equals(mCustomer.getCity())) {
            mCityCode = mCustomer.getCity();
        }
        if (!"null".equals(mCustomer.getCompanyType())) {
            mCompanyType = mCustomer.getCompanyType();
        }

        mCompanyNameEt.requestFocus();
    }

    private void fillUpTradeData() {
        mPreBuyProductEt.setText(!"null".equals(mCustomer.getKind()) ? mCustomer.getKind().trim() : "");
        mProducingAreaEt.setText(!"null".equals(mCustomer.getPlace()) ? mCustomer.getPlace().trim() : "");
        mStandardEt.setText(!"null".equals(mCustomer.getNorms()) ? mCustomer.getNorms().trim() : "");
        mNumberEt.setText(!"null".equals(mCustomer.getAmount()) ? mCustomer.getAmount().trim() : "");
        mSettlementTypeEt.setText(!"null".equals(mCustomer.getAccount()) ? mCustomer.getAccount().trim() : "");
        mCustomerAccountEt.setText(!"null".equals(mCustomer.getAccountNum()) ? mCustomer.getAccountNum().trim() : "");
        mRemarkEt.setText(!"null".equals(mCustomer.getRemarkContent()) ? mCustomer.getRemarkContent().trim() : "");

        String lastTradeTime = mCustomer.getLastestTradeTime();
        if (null != lastTradeTime && !"null".equals(lastTradeTime) && lastTradeTime.length() >= 10) {
            lastTradeTime = lastTradeTime.substring(0, 10);
            if (lastTradeTime.contains(".")) {
                int index = lastTradeTime.indexOf(".");
                lastTradeTime = lastTradeTime.substring(0, index);
            }
        }
        mLastSettlementTimeTv.setText(!"null".equals(lastTradeTime) ? lastTradeTime : "");
        String isHasLog = !"null".equals(mCustomer.getTradeFlg()) ? mCustomer.getTradeFlg().trim() : "";
        if (!TextUtils.isEmpty(isHasLog) && "1".equals(isHasLog)) {
            mIsHasLogCb.setChecked(true);
            mIsHasLog = "1";
        }

        if (!"null".equals(lastTradeTime)) {
            mLastTradeTime = "";
            mLastTradeTime = lastTradeTime + " 00:00:00";
        }

        mPreBuyProductEt.requestFocus();
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
        checkInput();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 500: {
                    mProviceCityTv.setText(data.getStringExtra("area"));
                    mProviceCode = data.getStringExtra("proviceCode");
                    mCityCode = data.getStringExtra("cityCode");
                    mProvice = data.getStringExtra("provice");
                    mCity = data.getStringExtra("city");
                    break;
                }
                case 501: {
                    data.getStringExtra("date");
                    mLastTradeTime = "";
                    mLastTradeTime = data.getStringExtra("date_value");
                    mLastSettlementTimeTv.setText(mLastTradeTime);
                    mLastTradeTime = mLastTradeTime + " 00:00:00";
                    mLastTradeDate = new Date(TimeUtils.dateToLong(mLastTradeTime, TimeUtils.FORMAT_PATTERN_DATE));
                    break;
                }
                case 502: {
                    mTypeTv.setText(data.getStringExtra("text"));
                    mType = data.getStringExtra("value");
                    if ("-1".equals(mType)) {
                        mTypeTv.setText("其它");
                    }
                    break;
                }
                case 503: {
                    mLevelTv.setText(data.getStringExtra("text"));
                    mLevel = data.getStringExtra("value");
                    if ("-1".equals(mLevel)) {
                        mLevelTv.setText("其它");
                    }
                    break;
                }

                case 504: {
                    mCompanyTypeTv.setText(data.getStringExtra("text"));
                    mCompanyType = data.getStringExtra("value");
                    if ("-1".equals(mCompanyType)) {
                        mCompanyTypeTv.setText("其它");
                    }
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
            case R.id.customer_info_add_back_iv:
            case R.id.customer_add_company_back_iv:
            case R.id.customer_add_settlement_back_iv: {
                finish();
                break;
            }

            case R.id.customer_info_add_next_btn: {
                mCustomer.setName(mNameEt.getText().toString());
                mCustomer.setPosition(mJobPostionEt.getText().toString());
                mCustomer.setMobile(mMobilePhoneEt.getText().toString());
                mCustomer.setTel(mTelPhoneEt.getText().toString());
                mCustomer.setQq(mQQEt.getText().toString());
                mCustomer.setEmail(mEmailEt.getText().toString());
                mCustomer.setCusLevel(mLevel);
                mCustomer.setCusLevel(mLevelTv.getText().toString());
                mCustomer.setCustomerType(mType);
                mCustomer.setCustomerTypeDesc(mTypeTv.getText().toString());

                setView2();
                if (mNowAction.equals(ORIGIN_FROM_UPDATE_KEY)) {
                    fillUpCompanyData();
                }
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }
            case R.id.customer_company_info_add_previous_btn: {
                setView1();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }
            case R.id.customer_company_info_add_next_btn: {
                mCustomer.setCompanyName(mCompanyNameEt.getText().toString());
                mCustomer.setAddress(mCompanyAddressEt.getText().toString());
                mCustomer.setMainProduct(mMainProductEt.getText().toString());
                mCustomer.setUrl(mCompanyNetEt.getText().toString());
                mCustomer.setStockWay(mInboundChannelEt.getText().toString());
                mCustomer.setCompanyType(mCompanyTypeTv.getText().toString());
                mCustomer.setProvince(mProviceCode);
                mCustomer.setProvinceName(mProvice);
                mCustomer.setCity(mCityCode);
                mCustomer.setCityName(mCity);
                mCustomer.setCompanyType(mCompanyType);
                mCustomer.setCompanyTypeDesc(mCompanyTypeTv.getText().toString());

                setView3();
                if (mNowAction.equals(ORIGIN_FROM_UPDATE_KEY)) {
                    fillUpTradeData();
                }
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }
            case R.id.customer_settlement_info_add_previous_btn: {
                setView2();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }
            case R.id.customer_settlement_info_add_save_btn: {
                mCustomer.setKind(mPreBuyProductEt.getText().toString());
                mCustomer.setPlace(mProducingAreaEt.getText().toString());
                mCustomer.setNorms(mStandardEt.getText().toString());
                mCustomer.setAmount(mNumberEt.getText().toString());
                mCustomer.setAccount(mSettlementTypeEt.getText().toString());
                mCustomer.setAccountNum(mCustomerAccountEt.getText().toString());
                mCustomer.setRemarkContent(mRemarkEt.getText().toString());
                mCustomer.setLastestTradeTime(mLastTradeTime);
                mCustomer.setTradeFlg(mIsHasLog);
                mCustomer.setRemarkContent(mRemarkEt.getText().toString());

                mProgressDialog.show();
                CustomerLogic.save(mContext, mHandler, UserInfoManager.getUserId(mContext), JsonUtils.Object2Json(mCustomer));
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
            case R.id.customer_info_add_type_rl: {
                Intent intent = new Intent(CustomerAddActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "CUSTOMER_TYPE_B");
                startActivityForResult(intent, 502);
                break;
            }
            case R.id.customer_info_add_level_rl: {
                Intent intent = new Intent(CustomerAddActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "CUS_LEVEL");
                startActivityForResult(intent, 503);
                break;
            }
            case R.id.customer_add_company_type_rl: {
                Intent intent = new Intent(CustomerAddActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "COMPANY_TYPE_B");
                startActivityForResult(intent, 504);
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
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
