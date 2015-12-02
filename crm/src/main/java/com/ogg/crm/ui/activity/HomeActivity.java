package com.ogg.crm.ui.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.ui.utils.OtherActivityClickHelp;

public class HomeActivity extends TabActivity implements OnClickListener {

    public static final String TAB_MAIN = "MAIN";
    public static final String TAB_CUS = "CUS";
    public static final String TAB_USER = "USER";
    public static final String TAB_SMS = "SMS";

    private static TabHost mTabHost;

    private FrameLayout mMainFl, mCustomerFl, mSmsFl, mUserFl;

    private static ImageView mMainIv;
    private static ImageView mCustomerIv;
    private static ImageView mSmsIv;
    private static ImageView mUserIv;
    private TextView tab_home_text_click, tab_home_text, tab_bang_text,
            tab_bang_text_click;

    public static OtherActivityClickHelp mOtherActivityClickHelp;

    public static OtherActivityClickHelp getmOtherActivityClickHelp() {
        return mOtherActivityClickHelp;
    }

    public static void setmOtherActivityClickHelp(
            OtherActivityClickHelp mOtherActivityClickHelp) {
        HomeActivity.mOtherActivityClickHelp = mOtherActivityClickHelp;
    }

    public static CheckBox mCheckAllIb;
    public static boolean mIsCancelAll;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initView();
        initData();
    }

    private void initView() {
        // TODO Auto-generated method stub
        mTabHost = getTabHost();

        Intent i_home = new Intent(this, MainActivity.class);
        Intent i_customer = new Intent(this, CustomerActivity.class);
        Intent i_sms = new Intent(this, SmsActivity.class);
        Intent i_user = new Intent(this, UserActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
                .setContent(i_home));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CUS)
                .setIndicator(TAB_CUS).setContent(i_customer));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_SMS)
                .setIndicator(TAB_SMS).setContent(i_sms));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_USER).setIndicator(TAB_USER)
                .setContent(i_user));

        mMainFl = (FrameLayout) findViewById(R.id.home_main_fl);
        mCustomerFl = (FrameLayout) findViewById(R.id.home_customer_fl);
        mSmsFl = (FrameLayout) findViewById(R.id.home_sms_fl);
        mUserFl = (FrameLayout) findViewById(R.id.home_user_fl);

        mMainFl.setOnClickListener(this);
        mCustomerFl.setOnClickListener(this);
        mSmsFl.setOnClickListener(this);
        mUserFl.setOnClickListener(this);

        mMainIv = (ImageView) findViewById(R.id.home_main_iv);
        mCustomerIv = (ImageView) findViewById(R.id.home_customer_iv);
        mSmsIv = (ImageView) findViewById(R.id.home_sms_iv);
        mUserIv = (ImageView) findViewById(R.id.home_user_iv);

    }

    private void initData() {
        mTabHost.setCurrentTabByTag(TAB_MAIN);
        mMainIv.setImageResource(R.drawable.tab_main_pressed);

    }

    private static void reset() {
        mMainIv.setImageResource(R.drawable.tab_main_normal);
        mCustomerIv.setImageResource(R.drawable.tab_customer_normal);
        mSmsIv.setImageResource(R.drawable.tab_sms_normal);
        mUserIv.setImageResource(R.drawable.tab_user_normal);
    }

    public static void showMainByOnkey() {
        mTabHost.setCurrentTabByTag(TAB_MAIN);
        reset();
        mMainIv.setImageResource(R.drawable.tab_main_pressed);
    }

    public static void setTab(String tab) {
        mTabHost.setCurrentTabByTag(tab);
        if (TAB_MAIN.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_MAIN);
            reset();
            mMainIv.setImageResource(R.drawable.tab_main_pressed);
        } else if (TAB_CUS.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_CUS);
            reset();
            mCustomerIv.setImageResource(R.drawable.tab_customer_pressed);
        } else if (TAB_SMS.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_SMS);
            reset();
            mUserIv.setImageResource(R.drawable.tab_sms_pressed);
        } else if (TAB_USER.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_USER);
            reset();
            mSmsIv.setImageResource(R.drawable.tab_user_pressed);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_main_fl: {
                mTabHost.setCurrentTabByTag(TAB_MAIN);
                reset();
                mMainIv.setImageResource(R.drawable.tab_main_pressed);
                break;
            }
            case R.id.home_customer_fl: {
                mTabHost.setCurrentTabByTag(TAB_CUS);
                reset();
                mCustomerIv.setImageResource(R.drawable.tab_customer_pressed);
                break;
            }
            case R.id.home_sms_fl: {
                mTabHost.setCurrentTabByTag(TAB_SMS);
                reset();
                mSmsIv.setImageResource(R.drawable.tab_sms_pressed);
                break;
            }
            case R.id.home_user_fl: {
                mTabHost.setCurrentTabByTag(TAB_USER);
                reset();
                mUserIv.setImageResource(R.drawable.tab_user_pressed);
                break;
            }

            default:
                break;
        }
    }
}