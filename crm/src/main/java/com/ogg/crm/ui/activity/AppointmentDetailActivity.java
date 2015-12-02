package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.Appointment;
import com.ogg.crm.network.logic.AppointmentLogic;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.utils.ActivitiyInfoManager;

public class AppointmentDetailActivity extends Activity implements OnClickListener {

    public static final String APPOINTMENTKEY = "appointment_key";

    private Context mContext;

    private ImageView mBackIv;

    private TextView mBusinessTypeTv;
    private TextView mContentDescriptionTv;
    private TextView mAppointmentTimeTv;
    private TextView mCustomerNameTv;
    private TextView mCustomerPhoneTv;

    private Button mChangeStateBtn;

    private CustomProgressDialog mProgressDialog;

    private Appointment mAppointment;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case AppointmentLogic.STATE_SET_SUC: {
                    Toast.makeText(mContext, "已完成!", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
                case AppointmentLogic.STATE_SET_FAIL: {
                    break;
                }
                case AppointmentLogic.STATE_SET_EXCEPTION: {
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
        setContentView(R.layout.appointment_detail);
        mContext = AppointmentDetailActivity.this;
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
        mBackIv = (ImageView) findViewById(R.id.appointment_detail_back_iv);
        mBackIv.setOnClickListener(this);

        mChangeStateBtn = (Button) findViewById(R.id.appointment_detail_complete_btn);
        mChangeStateBtn.setOnClickListener(this);

        mBusinessTypeTv = (TextView) findViewById(R.id.appointment_detail_business_type_tv);
        mContentDescriptionTv = (TextView) findViewById(R.id.appointment_detail_content_description_tv);
        mAppointmentTimeTv = (TextView) findViewById(R.id.appointment_detail_appointment_time_tv);
        mCustomerNameTv = (TextView) findViewById(R.id.appointment_detail_customer_name_tv);
        mCustomerPhoneTv = (TextView) findViewById(R.id.appointment_detail_customer_phone_tv);
    }

    private void initData() {
        mProgressDialog = new CustomProgressDialog(mContext);
        //mProgressDialog.show();
        mAppointment = (Appointment) getIntent().getSerializableExtra(APPOINTMENTKEY);
        if (null != mAppointment) {
            fillUpData();
        }
    }

    private void fillUpData() {
        mBusinessTypeTv.setText(!"null".equals(mAppointment.getBussinessDes()) ? mAppointment.getBussinessDes() : "");
        mContentDescriptionTv.setText(!"null".equals(mAppointment.getShortDesc()) ? mAppointment.getShortDesc() : "");
        mAppointmentTimeTv.setText(!"null".equals(mAppointment.getReserveTime()) ? mAppointment.getReserveTime() : "");
        mCustomerNameTv.setText(!"null".equals(mAppointment.getCustomerName()) ? mAppointment.getCustomerName() : "");
        mCustomerPhoneTv.setText(!"null".equals(mAppointment.getCustomerTel()) ? mAppointment.getCustomerTel() : "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appointment_detail_complete_btn: {
                if (null != mAppointment) {
                    AppointmentLogic.setState(mContext, mHandler, mAppointment.getRemindId());
                }
                break;
            }

            default: {
                break;
            }
        }
    }
}
