package com.ogg.crm.ui.activity;


import android.app.Activity;
import android.content.Context;
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


public class SettingActivity extends Activity implements OnClickListener {

    private Context mContext;

    private TextView mSettingTv;

    private ImageView mBackIv;

    private LinearLayout mAboutLl;
    private LinearLayout mReportFormLl;
    private LinearLayout mSmsLl;

    private Button mSwitchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        mContext = SettingActivity.this;
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        mBackIv = (ImageView) findViewById(R.id.setting_back_iv);
        mBackIv.setOnClickListener(this);

        mAboutLl = (LinearLayout) findViewById(R.id.setting_about_ll);
        mAboutLl.setOnClickListener(this);
    }


    private void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_about_ll: {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.setting_back_iv: {
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