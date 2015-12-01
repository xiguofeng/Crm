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
    public static final String TAB_FORM = "FORM";
    public static final String TAB_SMS = "SMS";

    private static TabHost mTabHost;

    private FrameLayout mMainFl, mCategoryFl, mCartFl, mPersonFl;

    private static ImageView mMainIv;
    private static ImageView mCategoryIv;
    private static ImageView mCartIv;
    private static ImageView mPersonIv;
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
        Intent i_reportform = new Intent(this, ReportFormActivity.class);
        Intent i_sms = new Intent(this, SmsActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
                .setContent(i_home));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_FORM).setIndicator(TAB_FORM)
                .setContent(i_reportform));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CUS)
                .setIndicator(TAB_CUS).setContent(i_customer));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_SMS)
                .setIndicator(TAB_SMS).setContent(i_sms));

        mMainFl = (FrameLayout) findViewById(R.id.home_main_fl);
        mCategoryFl = (FrameLayout) findViewById(R.id.home_category_fl);
        mCartFl = (FrameLayout) findViewById(R.id.home_cart_fl);
        mPersonFl = (FrameLayout) findViewById(R.id.home_person_fl);

        mMainFl.setOnClickListener(this);
        mCategoryFl.setOnClickListener(this);
        mCartFl.setOnClickListener(this);
        mPersonFl.setOnClickListener(this);

        mMainIv = (ImageView) findViewById(R.id.home_main_iv);
        mCategoryIv = (ImageView) findViewById(R.id.home_category_iv);
        mCartIv = (ImageView) findViewById(R.id.home_cart_iv);
        mPersonIv = (ImageView) findViewById(R.id.home_person_iv);

    }

    private void initData() {
        mTabHost.setCurrentTabByTag(TAB_MAIN);
        mMainIv.setImageResource(R.drawable.tab_main_pressed);

    }

    private static void reset() {
        mMainIv.setImageResource(R.drawable.tab_main_normal);
        mCategoryIv.setImageResource(R.drawable.tab_category_normal);
        mCartIv.setImageResource(R.drawable.tab_cart_normal);
        mPersonIv.setImageResource(R.drawable.tab_person_normal);
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
        } else if (TAB_FORM.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_FORM);
            reset();
            mCartIv.setImageResource(R.drawable.tab_cart_pressed);
        } else if (TAB_CUS.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_CUS);
            reset();
            mCategoryIv.setImageResource(R.drawable.tab_category_pressed);
        } else if (TAB_SMS.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_SMS);
            reset();
            mPersonIv.setImageResource(R.drawable.tab_person_pressed);
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
            case R.id.home_category_fl: {
                mTabHost.setCurrentTabByTag(TAB_CUS);
                reset();
                mCategoryIv.setImageResource(R.drawable.tab_category_pressed);
                break;
            }
            case R.id.home_cart_fl: {
                mTabHost.setCurrentTabByTag(TAB_FORM);
                reset();
                mCartIv.setImageResource(R.drawable.tab_cart_pressed);
                break;
            }
            case R.id.home_person_fl: {
                mTabHost.setCurrentTabByTag(TAB_SMS);
                reset();
                mPersonIv.setImageResource(R.drawable.tab_person_pressed);
                break;
            }

            default:
                break;
        }
    }
}