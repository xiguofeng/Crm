package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.Appointment;
import com.ogg.crm.network.logic.AppointmentLogic;
import com.ogg.crm.ui.adapter.AppointmentAdapter;
import com.ogg.crm.ui.utils.ListItemClickHelp;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.utils.TimeUtils;
import com.ogg.crm.utils.UserInfoManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class MainActivity extends Activity implements
        ListItemClickHelp, OnClickListener {

    private Context mContext;

    private ListView mAppointmentLv;
    private AppointmentAdapter mAppointmentAdapter;
    private ArrayList<Appointment> mAppointmentList = new ArrayList<Appointment>();

    private TextView mDateTv;
    private TextView mAppointmentMoreTv;

    private CustomProgressDialog mProgressDialog;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
        }

    };

    Handler mAppointmentHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case AppointmentLogic.LIST_GET_SUC: {
                    if (null != msg.obj) {
                        mAppointmentList.clear();
                        mAppointmentList.addAll((Collection<? extends Appointment>) msg.obj);
                        mAppointmentAdapter.notifyDataSetChanged();
                    }

                    break;
                }
                case AppointmentLogic.LIST_GET_FAIL: {
                    Toast.makeText(mContext, "获取数据失败!",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                case AppointmentLogic.LIST_GET_EXCEPTION: {
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
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        mProgressDialog = new CustomProgressDialog(mContext);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserInfoManager.getLoginIn(mContext)) {
            UserInfoManager.setUserInfo(mContext);
            mProgressDialog.show();
            AppointmentLogic.getList(mContext, mAppointmentHandler, UserInfoManager.userInfo);
        }
    }

    private void initView() {
        mAppointmentLv = (ListView) findViewById(R.id.main_appointment_lv);
        mAppointmentAdapter = new AppointmentAdapter(mContext, mAppointmentList, this);
        mAppointmentLv.setAdapter(mAppointmentAdapter);

        mAppointmentLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this,
                        AppointmentDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppointmentDetailActivity.APPOINTMENTKEY,
                        mAppointmentList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mDateTv = (TextView) findViewById(R.id.main_appointment_date_tv);
        mAppointmentMoreTv = (TextView) findViewById(R.id.main_appointment_more_tv);
        mAppointmentMoreTv.setOnClickListener(this);

    }

    private void initData() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String frontS = TimeUtils.TimeStamp2Date(String.valueOf(date.getTime()), "yyyy-MM-dd");
        String afterS = dateFm.format(date);
        mDateTv.setText(frontS + " " + afterS);
    }

    @Override
    public void onClick(View item, View widget, int position, int which) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_appointment_more_tv: {
                Intent intent = new Intent(MainActivity.this, AppointmentListActivity.class);
                startActivity(intent);
            }
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

//            new AlertDialog(MainActivity.this)
//                    .builder()
//                    .setTitle(getString(R.string.prompt))
//                    .setMsg(getString(R.string.exit_str))
//                    .setPositiveButton(getString(R.string.confirm),
//                            new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    finish();
//                                }
//                            })
//                    .setNegativeButton(getString(R.string.cancel),
//                            new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            }).show();

            // if ((System.currentTimeMillis() - exitTime) > 2000) {
            // Toast.makeText(getApplicationContext(), R.string.exit,
            // Toast.LENGTH_SHORT).show();
            // exitTime = System.currentTimeMillis();
            // } else {
            // finish();
            // }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
