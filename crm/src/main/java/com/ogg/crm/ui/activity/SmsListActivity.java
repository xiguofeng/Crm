package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.Sms;
import com.ogg.crm.network.logic.SmsLogic;
import com.ogg.crm.ui.adapter.SmsAdapter;
import com.ogg.crm.ui.utils.ListItemClickHelp;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.utils.ActivitiyInfoManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class SmsListActivity extends Activity implements OnClickListener,
        ListItemClickHelp {

    public static final String ORIGIN_FROM_MAIN_KEY = "com.main";

    public static final String ORIGIN_FROM_SELECT_KEY = "com.select";

    private Context mContext;

    private ListView mSmsLv;
    private SmsAdapter mSmsAdapter;
    private ArrayList<Sms> mSmsList = new ArrayList<Sms>();

    private ImageView mBackIv;

    private String mNowSortType;

    private int mCurrentPage = 1;
    private int mCurrentPageNum = 1;

    private String mNowAction = ORIGIN_FROM_MAIN_KEY;

    private CustomProgressDialog mProgressDialog;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case SmsLogic.LIST_GET_SUC: {
                    if (null != msg.obj) {
                        mSmsList.addAll((Collection<? extends Sms>) msg.obj);
                        mSmsAdapter.notifyDataSetChanged();
                    }

                    break;
                }
                case SmsLogic.LIST_GET_FAIL: {
                    Toast.makeText(mContext, R.string.login_fail,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                case SmsLogic.LIST_GET_EXCEPTION: {
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
        mNowAction = getIntent().getAction();

        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog.show();

        SmsLogic.list(mContext, mHandler);
    }


    private void initListView() {
        mSmsLv = (ListView) findViewById(R.id.sms_list_goods_lv);

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
                if (ORIGIN_FROM_SELECT_KEY.equals(mNowAction)) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SmsDetailActivity.SMS_KEY,
                            mSmsList.get(position));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent(SmsListActivity.this,
                            SmsDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SmsDetailActivity.SMS_KEY,
                            mSmsList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
                .format(new Date());
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
