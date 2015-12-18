package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.Customer;
import com.ogg.crm.entity.Sms;
import com.ogg.crm.network.logic.SmsLogic;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.utils.ActivitiyInfoManager;
import com.ogg.crm.utils.UserInfoManager;

public class SmsSendActivity extends Activity implements OnClickListener {

    public static final String SMS_KEY = "sms_key";

    public static final String CUSTOMER_KEY = "customer_key";

    private Context mContext;

    private ImageView mBackIv;

    private TextView mUserNameTv;
    private TextView mUserPhoneTv;
    private TextView mCompanyNameTv;
    private TextView mTemplateSelectTv;
    private TextView mCustomerSelectTv;
    private EditText mContentEt;

    private Button mSendBtn;

    private Sms mSms;
    private Customer mCustomer;

    private CustomProgressDialog mProgressDialog;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case SmsLogic.SEND_SUC: {
                    Toast.makeText(mContext, R.string.send_suc,
                            Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
                case SmsLogic.SEND_FAIL: {
                    if (null != msg.obj) {
                        Toast.makeText(mContext, (String) msg.obj,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, R.string.send_fail,
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SmsLogic.SEND_EXCEPTION: {
                    break;
                }
                case SmsLogic.NET_ERROR: {
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
        setContentView(R.layout.sms_send);
        mContext = SmsSendActivity.this;
        mProgressDialog = new CustomProgressDialog(mContext);
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
        mCustomerSelectTv = (TextView) findViewById(R.id.sms_send_select_customer_tv);
        mContentEt = (EditText) findViewById(R.id.sms_send_content_et);

        mTemplateSelectTv.setOnClickListener(this);
        mCustomerSelectTv.setOnClickListener(this);

        mSendBtn = (Button) findViewById(R.id.sms_send_btn);
        mSendBtn.setOnClickListener(this);
    }

    private void initData() {
        Sms sms = (Sms) getIntent().getSerializableExtra(SMS_KEY);
        if (null != sms) {
            mSms = sms;
            mTemplateSelectTv.setText(mSms.getTemplateTitle());
            mContentEt.setText(mSms.getTemplateContent());
        }
    }

    private void send() {
        if (null == mCustomer) {
            Toast.makeText(mContext, "请选择客户!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(mUserPhoneTv.getText().toString()) && !TextUtils.isEmpty(mContentEt.getText().toString())) {
            mProgressDialog.show();
            SmsLogic.send(mContext, mHandler, UserInfoManager.getUserId(mContext), mUserPhoneTv.getText().toString(), mContentEt.getText().toString());
        } else {
            Toast.makeText(mContext, "请填写发送内容!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 600: {
                    Sms sms = (Sms) data.getSerializableExtra(SMS_KEY);
                    if (null != sms) {
                        mSms = sms;
                        mTemplateSelectTv.setText(mSms.getTemplateTitle());
                        mContentEt.setText(mSms.getTemplateContent());
                    }
                    break;
                }
                case 601: {
                    Customer customer = (Customer) data.getSerializableExtra(CUSTOMER_KEY);
                    if (null != customer) {
                        mCustomer = customer;
                        mCustomerSelectTv.setText(mCustomer.getName());
                        mUserNameTv.setText(mCustomer.getName());
                        mUserPhoneTv.setText(!"null".equals(mCustomer.getMobile()) ? mCustomer.getMobile() : "");
                        mCompanyNameTv.setText(!"null".equals(mCustomer.getCompanyName()) ? mCustomer.getCompanyName() : "");
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
            case R.id.sms_send_back_iv: {
                finish();
                break;
            }

            case R.id.sms_send_select_template_tv: {
                Intent intent = new Intent(SmsSendActivity.this, SmsListActivity.class);
                intent.setAction(SmsListActivity.ORIGIN_FROM_SELECT_KEY);
                startActivityForResult(intent, 600);
                break;
            }

            case R.id.sms_send_select_customer_tv: {
                Intent intent = new Intent(SmsSendActivity.this, CustomerSelectListActivity.class);
                intent.setAction(SmsListActivity.ORIGIN_FROM_SELECT_KEY);
                startActivityForResult(intent, 601);
                break;
            }
            case R.id.sms_send_btn: {
                send();
                break;
            }
            default: {
                break;
            }
        }
    }
}
