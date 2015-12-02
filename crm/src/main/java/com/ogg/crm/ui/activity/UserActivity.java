package com.ogg.crm.ui.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    private TextView mSettingTv;

    private ImageView headImage;
    private ImageView mBackIv;

    private LinearLayout mMyCustomerLl;
    private LinearLayout mReportFormLl;
    private LinearLayout mSmsLl;

    private Button mSwitchBtn;


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

        mSettingTv = (TextView) findViewById(R.id.user_setting_tv);
        mSettingTv.setOnClickListener(this);

        mSwitchBtn = (Button) findViewById(R.id.user_switch_btn);
        mSwitchBtn.setOnClickListener(this);
    }


    private void initData() {
        if (!"null".equals(UserInfoManager.userInfo.getLogonName())) {
            mUserNameTv.setText(UserInfoManager.userInfo.getLogonName());
        }
        if (!"null".equals(UserInfoManager.userInfo.getRoleName())) {
            mUserJobTv.setText(UserInfoManager.userInfo.getRoleName());
        }
    }

    private void showDialog() {
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        UserInfoManager.clearUserInfo(mContext);
                        UserInfoManager.setLoginIn(mContext, false);
                        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                        intent.setAction(LoginActivity.ORIGIN_FROM_USER_KEY);
                        startActivity(intent);
                        UserActivity.this.finish();
                        overridePendingTransition(R.anim.push_down_in,
                                R.anim.push_down_out);
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认切换账户?"); //设置内容
        //builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确认", dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_my_customer_ll: {
                HomeActivity.setTab(HomeActivity.TAB_MAIN);
                break;
            }

            case R.id.user_my_form_ll: {

                break;
            }

            case R.id.user_my_sms_ll: {
                HomeActivity.setTab(HomeActivity.TAB_SMS);
                break;
            }

            case R.id.user_back_iv: {
                finish();
                break;
            }

            case R.id.user_switch_btn: {
                showDialog();
                break;
            }

            case R.id.user_setting_tv: {
                Intent intent = new Intent(UserActivity.this, SettingActivity.class);
                startActivity(intent);
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
            HomeActivity.showMainByOnkey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}