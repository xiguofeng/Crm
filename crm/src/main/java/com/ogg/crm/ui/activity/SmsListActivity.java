package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.ogg.crm.R;
import com.ogg.crm.entity.Sms;
import com.ogg.crm.ui.adapter.SmsAdapter;
import com.ogg.crm.ui.utils.ListItemClickHelp;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.ui.view.listview.XListView;
import com.ogg.crm.utils.ActivitiyInfoManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SmsListActivity extends Activity implements OnClickListener,
        ListItemClickHelp, XListView.IXListViewListener {

    private Context mContext;

    private XListView mSmsLv;
    private SmsAdapter mSmsAdapter;
    private ArrayList<Sms> mSmsList = new ArrayList<Sms>();


    private ImageView mBackIv;

    private String mNowSortType;

    private int mCurrentPage = 1;
    private int mCurrentPageNum = 1;

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_list);
        mContext = SmsListActivity.this;
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
        mBackIv = (ImageView) findViewById(R.id.sms_list_back_iv);
        mBackIv.setOnClickListener(this);

        initListView();
    }

    private void initData() {
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog.show();
    }


    private void initListView() {
        mSmsLv = (XListView) findViewById(R.id.sms_list_goods_xlv);
        mSmsLv.setPullRefreshEnable(false);
        mSmsLv.setPullLoadEnable(true);
        mSmsLv.setAutoLoadEnable(true);
        mSmsLv.setXListViewListener(this);
        mSmsLv.setRefreshTime(getTime());

        mSmsAdapter = new SmsAdapter(mContext, mSmsList, this);
        mSmsLv.setAdapter(mSmsAdapter);

        mSmsLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Log.i(TAG, "滚动状态变化");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // Log.i(TAG, "正在滚动");
            }
        });
        mSmsLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position > 0) {
                    Intent intent = new Intent(SmsListActivity.this,
                            SmsSendActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SmsSendActivity.SMS_KEY,
                            mSmsList.get(position - 1).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

        for (int i = 0; i < 10; i++) {
            Sms Sms = new Sms();
            Sms.setId("id" + i);
            Sms.setName("name" + i);
            mSmsList.add(Sms);
        }
        mSmsAdapter.notifyDataSetChanged();

    }

    private void onLoadComplete() {
        mSmsLv.stopRefresh();
        mSmsLv.stopLoadMore();
        mSmsLv.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
                .format(new Date());
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View item, View widget, int position, int which) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sms_list_back_iv: {
                finish();
                break;
            }

            default: {
                break;
            }
        }
    }
}
