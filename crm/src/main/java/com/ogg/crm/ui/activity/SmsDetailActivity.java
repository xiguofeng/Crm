package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.utils.ActivitiyInfoManager;

public class SmsDetailActivity extends Activity implements OnClickListener {

    public static final String SMS_KEY = "sms_key";

    private Context mContext;

    private ImageView mBackIv;

    private TextView mSmsTitleTv;
    private TextView mSmsContentTv;

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_detail);
        mContext = SmsDetailActivity.this;
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
        mBackIv = (ImageView) findViewById(R.id.sms_detail_back_iv);
        mBackIv.setOnClickListener(this);

        mSmsTitleTv = (TextView) findViewById(R.id.sms_detail_title_tv);
        mSmsContentTv = (TextView) findViewById(R.id.sms_detail_content_tv);
    }

    private void initData() {
        mProgressDialog = new CustomProgressDialog(mContext);
        //mProgressDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sms_detail_back_iv: {
                finish();
                break;
            }

            default: {
                break;
            }
        }
    }
}
