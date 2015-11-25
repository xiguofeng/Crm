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
    public static final String TAB_CATEGORY = "CATEGORY";
    public static final String TAB_CART = "CART";
    public static final String TAB_PERSON = "PERSON";

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
        Intent i_category = new Intent(this, CustomerActivity.class);
        Intent i_cart = new Intent(this, SmsActivity.class);
        Intent i_person = new Intent(this, LoginActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
                .setContent(i_home));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CART).setIndicator(TAB_CART)
                .setContent(i_cart));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CATEGORY)
                .setIndicator(TAB_CATEGORY).setContent(i_category));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSON)
                .setIndicator(TAB_PERSON).setContent(i_person));

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
        } else if (TAB_CART.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_CART);
            reset();
            mCartIv.setImageResource(R.drawable.tab_cart_pressed);
        } else if (TAB_CATEGORY.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_CATEGORY);
            reset();
            mCategoryIv.setImageResource(R.drawable.tab_category_pressed);
        } else if (TAB_PERSON.equals(tab)) {
            mTabHost.setCurrentTabByTag(TAB_PERSON);
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
                mTabHost.setCurrentTabByTag(TAB_CATEGORY);
                reset();
                mCategoryIv.setImageResource(R.drawable.tab_category_pressed);
                break;
            }
            case R.id.home_cart_fl: {
                mTabHost.setCurrentTabByTag(TAB_CART);
                reset();
                mCartIv.setImageResource(R.drawable.tab_cart_pressed);
                break;
            }
            case R.id.home_person_fl: {
                mTabHost.setCurrentTabByTag(TAB_PERSON);
                reset();
                mPersonIv.setImageResource(R.drawable.tab_person_pressed);
                break;
            }

            default:
                break;
        }
    }
}