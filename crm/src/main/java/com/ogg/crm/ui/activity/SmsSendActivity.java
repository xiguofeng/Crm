package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.entity.Sms;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.utils.ActivitiyInfoManager;

public class SmsSendActivity extends Activity implements OnClickListener {

    public static final String SMS_KEY = "sms_key";

    private Context mContext;

    private ImageView mBackIv;

    private TextView mUserNameTv;
    private TextView mUserPhoneTv;
    private TextView mCompanyNameTv;
    private TextView mTemplateSelectTv;
    private EditText mContentEt;

    private Sms mSms;

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_send);
        mContext = SmsSendActivity.this;
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
        mBackIv = (ImageView) findViewById(R.id.sms_send_back_iv);
        mBackIv.setOnClickListener(this);

        mUserNameTv = (TextView) findViewById(R.id.sms_send_user_name_tv);
        mUserPhoneTv = (TextView) findViewById(R.id.sms_send_user_phone_tv);
        mCompanyNameTv = (TextView) findViewById(R.id.sms_send_company_name_tv);
        mTemplateSelectTv = (TextView) findViewById(R.id.sms_send_select_template_tv);
        mContentEt = (EditText) findViewById(R.id.sms_send_content_et);
    }

    private void initData() {
        Sms sms = (Sms) getIntent().getSerializableExtra(SMS_KEY);
        if (null != sms) {
            mSms = sms;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sms_send_back_iv: {
                finish();
                break;
            }

            default: {
                break;
            }
        }
    }
}
