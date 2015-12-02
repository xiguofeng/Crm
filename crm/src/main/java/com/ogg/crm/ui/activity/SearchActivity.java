package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.Customer;
import com.ogg.crm.network.config.MsgRequest;
import com.ogg.crm.network.logic.SearchLogic;
import com.ogg.crm.ui.adapter.CustomerAdapter;
import com.ogg.crm.ui.adapter.MySimpleAdapter;
import com.ogg.crm.ui.utils.ListItemClickHelp;
import com.ogg.crm.ui.view.AutoClearEditText;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.ui.view.listview.XListView;
import com.ogg.crm.utils.CacheManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SearchActivity extends Activity implements OnClickListener,
        ListItemClickHelp, XListView.IXListViewListener {

    private AutoClearEditText mSearchGoodsEt;

    private ImageView mBackIv;
    private TextView mSearchTagTv;
    private TextView mSearchTv;


    private ListView mSearchHistroyLv;
    private MySimpleAdapter mSimpleAdapter;
    private ArrayList<String> mSearchHistoryList = new ArrayList<String>();

    private String mSearchKey;

    private XListView mGoodsLv;
    private CustomerAdapter mCustomerAdapter;
    private ArrayList<Customer> mCustomerList = new ArrayList<Customer>();

    private int mCurrentPageNum = 1;
    private CustomProgressDialog mCustomProgressDialog;

    private final Context mContext = SearchActivity.this;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case SearchLogic.NORAML_GET_SUC: {
                    mCustomerList.clear();
                    mCustomerList.addAll((ArrayList<Customer>) msg.obj);
                    if (mCustomerList.size() > 0) {
                        mCustomerAdapter.notifyDataSetChanged();
                        mSearchHistroyLv.setVisibility(View.GONE);
                        mGoodsLv.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(mContext, "没有查询到相关客户", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SearchLogic.NORAML_GET_FAIL: {
                    Toast.makeText(mContext, "没有查询到相关客户", Toast.LENGTH_SHORT).show();
                    break;
                }
                case SearchLogic.NORAML_GET_EXCEPTION: {
                    Toast.makeText(mContext, "没有查询到相关客户", Toast.LENGTH_SHORT).show();
                    break;
                }
                case SearchLogic.NET_ERROR: {
                    break;
                }
                default:
                    break;
            }

            if (null != mCustomProgressDialog) {
                mCustomProgressDialog.dismiss();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mCustomProgressDialog = new CustomProgressDialog(mContext);
        initView();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        CacheManager.setSearchHistroy(mContext);
        mSearchHistoryList.clear();
        mSearchHistoryList.addAll(CacheManager.sSearchHistroyList);
        mSimpleAdapter.notifyDataSetChanged();
    }

    private void initView() {
        // 初始化控件
        mSearchGoodsEt = (AutoClearEditText) findViewById(R.id.search_et);
        mSearchTv = (TextView) findViewById(R.id.search_tv);
        mSearchTagTv = (TextView) findViewById(R.id.search_tag_tv);
        mSearchHistroyLv = (ListView) findViewById(R.id.search_history_lv);

        mBackIv = (ImageView) findViewById(R.id.search_back_iv);
        mBackIv.setOnClickListener(this);

        initXListView();
    }

    private void initXListView() {
        mGoodsLv = (XListView) findViewById(R.id.search_goods_xlv);
        mGoodsLv.setPullRefreshEnable(false);
        mGoodsLv.setPullLoadEnable(false);
        mGoodsLv.setAutoLoadEnable(false);
        mGoodsLv.setXListViewListener(this);
        mGoodsLv.setRefreshTime(getTime());

        mCustomerAdapter = new CustomerAdapter(mContext, mCustomerList, this);
        mGoodsLv.setAdapter(mCustomerAdapter);

        mGoodsLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Log.i(TAG, "滚动状态变化");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Log.i(TAG, "正在滚动");
            }
        });
        mGoodsLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Intent intent = new Intent(SearchActivity.this, RegisterActivity.class);
//                    intent.setAction(GoodsDetailActivity.ORIGIN_FROM_SEAR_ACTION);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable(GoodsDetailActivity.GOODS_ID_KEY, mGoodsList.get(position - 1).getId());
//                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

    }

    private void initData() {
        mSearchTv.setOnClickListener(this);

        mSimpleAdapter = new MySimpleAdapter(SearchActivity.this, mSearchHistoryList);
        mSearchHistroyLv.setAdapter(mSimpleAdapter);
        mSearchHistroyLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (!TextUtils.isEmpty(mSearchHistoryList.get(position))) {
                    mSearchKey = mSearchHistoryList.get(position);
                    mSearchGoodsEt.setText(mSearchKey);
                    getGoodsData(mSearchKey);
                }
            }
        });
        mSearchHistoryList.clear();
        mSearchHistoryList.addAll(CacheManager.sSearchHistroyList);
        mSimpleAdapter.notifyDataSetChanged();

        if (null != getIntent().getExtras()) {
            mSearchKey = getIntent().getExtras().getString("searchKey");
            if (!TextUtils.isEmpty(mSearchKey)) {
                mSearchGoodsEt.setText(mSearchKey);
                getGoodsData(mSearchKey);
            }
        }
    }

    private void getGoodsData(String keyword) {
        mCustomProgressDialog.show();
        SearchLogic.queryCustomer(mContext, mHandler, keyword, "", mCurrentPageNum, MsgRequest.PAGE_SIZE);
        CacheManager.addSearchHistroy(getApplicationContext(), mSearchKey);
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
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
            case R.id.search_tv: {
                mSearchKey = mSearchGoodsEt.getText().toString().trim();
                CacheManager.addSearchHistroy(getApplicationContext(), mSearchKey);
                if ("".equals(mSearchKey)) {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.search_thing), Toast.LENGTH_SHORT)
                            .show();
                } else {
                    getGoodsData(mSearchKey);
                }
                break;
            }
            case R.id.search_back_iv: {
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
