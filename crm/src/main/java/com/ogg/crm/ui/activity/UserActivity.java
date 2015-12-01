package com.ogg.crm.ui.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.utils.UserInfoManager;


public class UserActivity extends Activity implements OnClickListener {

    private Context mContext;

    private TextView mUserNameTv;
    private TextView mUserJobTv;

    private ImageView headImage;
    private ImageView mBackIv;

    private LinearLayout mMyCustomerLl;
    private LinearLayout mReportFormLl;
    private LinearLayout mSmsLl;

    private Button mExitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        mContext = UserActivity.this;
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        headImage = (ImageView) findViewById(R.id.user_icon_iv);
        mBackIv = (ImageView) findViewById(R.id.user_back_iv);
        mBackIv.setOnClickListener(this);

        mMyCustomerLl = (LinearLayout) findViewById(R.id.user_my_customer_ll);
        mMyCustomerLl.setOnClickListener(this);
        mReportFormLl = (LinearLayout) findViewById(R.id.user_my_form_ll);
        mReportFormLl.setOnClickListener(this);
        mSmsLl = (LinearLayout) findViewById(R.id.user_my_sms_ll);
        mSmsLl.setOnClickListener(this);

        mUserNameTv = (TextView) findViewById(R.id.user_name_tv);
        mUserJobTv = (TextView) findViewById(R.id.user_job_tv);

        mExitBtn = (Button) findViewById(R.id.user_exit_btn);
        mExitBtn.setOnClickListener(this);
    }


    private void initData() {
        if (!"null".equals(UserInfoManager.userInfo.getLogonName())) {
            mUserNameTv.setText(UserInfoManager.userInfo.getLogonName());
        }
        if (!"null".equals(UserInfoManager.userInfo.getRoleName())) {
            mUserJobTv.setText(UserInfoManager.userInfo.getRoleName());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_my_customer_ll: {

                break;
            }

            case R.id.user_my_form_ll: {

                break;
            }

            case R.id.user_my_sms_ll: {

                break;
            }

            case R.id.user_back_iv: {
                finish();
                break;
            }

            case R.id.user_exit_btn: {
                UserInfoManager.clearUserInfo(mContext);
                UserInfoManager.setLoginIn(mContext, false);
                finish();
                break;
            }

            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}