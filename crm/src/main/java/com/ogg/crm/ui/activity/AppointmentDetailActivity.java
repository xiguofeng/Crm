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

public class AppointmentDetailActivity extends Activity implements OnClickListener {

    public static final String APPOINTMENTKEY = "appointment_key";

    private Context mContext;

    private ImageView mBackIv;

    private TextView mBusinessTypeTv;
    private TextView mContentDescriptionTv;
    private TextView mAppointmentTimeTv;
    private TextView mCustomerNameTv;
    private TextView mCustomerPhoneTv;


    private CustomProgressDialog mProgressDialog;

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
        mBackIv = (ImageView) findViewById(R.id.appointment_list_back_iv);
        mBackIv.setOnClickListener(this);

        mBusinessTypeTv = (TextView) findViewById(R.id.appointment_detail_complete_btn);
        mContentDescriptionTv = (TextView) findViewById(R.id.appointment_detail_complete_btn);
        mAppointmentTimeTv = (TextView) findViewById(R.id.appointment_detail_complete_btn);
        mCustomerNameTv = (TextView) findViewById(R.id.appointment_detail_complete_btn);
        mCustomerPhoneTv = (TextView) findViewById(R.id.appointment_detail_complete_btn);
    }

    private void initData() {
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appointment_list_filter_tv: {

                break;
            }

            default: {
                break;
            }
        }
    }
}
