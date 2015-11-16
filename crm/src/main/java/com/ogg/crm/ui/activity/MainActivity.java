package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.entity.Appointment;
import com.ogg.crm.ui.adapter.AppointmentAdapter;
import com.ogg.crm.ui.utils.ListItemClickHelp;
import com.ogg.crm.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends Activity implements
        ListItemClickHelp,OnClickListener {

    private Context mContext;

    private ListView mAppointmentLv;
    private AppointmentAdapter mAppointmentAdapter;
    private ArrayList<Appointment> mAppointmentList = new ArrayList<Appointment>();

    private TextView mDateTv;
    private TextView mAppointmentMoreTv;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        initView();
        initData();
        //UserLogic.login(mContext, mHandler);
    }

    private void initView() {
        mAppointmentLv = (ListView) findViewById(R.id.main_appointment_lv);
        mAppointmentAdapter = new AppointmentAdapter(mContext, mAppointmentList, this);
        mAppointmentLv.setAdapter(mAppointmentAdapter);

        mAppointmentLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position > 0) {
                    // Intent intent = new Intent(GoodsListActivity.this,
                    // GoodsDetailActivity.class);
                    // intent.setAction(GoodsDetailActivity.ORIGIN_FROM_CATE_ACTION);
                    // Bundle bundle = new Bundle();
                    // bundle.putSerializable(GoodsDetailActivity.GOODS_ID_KEY,
                    // mGoodsList.get(position - 1).getId());
                    // intent.putExtras(bundle);
                    // startActivity(intent);
                }

            }
        });

        mDateTv = (TextView) findViewById(R.id.main_appointment_date_tv);
        mAppointmentMoreTv = (TextView) findViewById(R.id.main_appointment_more_tv);
        mAppointmentMoreTv.setOnClickListener(this);

    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            Appointment appointment = new Appointment();
            appointment.setId("id" + i);
            appointment.setName("name" + i);
            mAppointmentList.add(appointment);
        }
        mAppointmentAdapter.notifyDataSetChanged();


        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String frontS=TimeUtils.TimeStamp2Date(String.valueOf(date.getTime()),"yyyy-MM-dd");
        String afterS = dateFm.format(date);

        mDateTv.setText(frontS+" "+afterS);
    }

    @Override
    public void onClick(View item, View widget, int position, int which) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_appointment_more_tv: {
                Intent intent =new Intent(MainActivity.this,AppointmentActivity.class);
                startActivity(intent);
            }
            default:
                break;
        }

    }
}
