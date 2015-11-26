package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.Customer;
import com.ogg.crm.network.config.MsgRequest;
import com.ogg.crm.network.logic.CustomerLogic;
import com.ogg.crm.ui.adapter.CustomerAdapter;
import com.ogg.crm.ui.utils.ListItemClickHelp;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.ui.view.listview.XListView;
import com.ogg.crm.utils.ActivitiyInfoManager;
import com.ogg.crm.utils.UserInfoManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class CustomerListActivity extends Activity implements OnClickListener,
        ListItemClickHelp, XListView.IXListViewListener {

    private Context mContext;

    private RelativeLayout mLevelRl;
    private TextView mFilterLevelTv;

    private RelativeLayout mTypeRl;
    private TextView mFilterTypeTv;

    private RelativeLayout mTradeRl;
    private TextView mFilterTradeTv;

    private RelativeLayout mStateRl;
    private TextView mFilterStateTv;

    private XListView mCustomerLv;
    private CustomerAdapter mCustomerAdapter;
    private ArrayList<Customer> mCustomerList = new ArrayList<Customer>();

    private DrawerLayout mDrawerLayout;
    private TextView mFilterTv;
    private boolean isFilterOpen = false;

    private TextView mFilterConfrimTv;
    private TextView mFilterCancelTv;

    private ImageView mBackIv;
    private ImageView mAddCustomerIv;

    private String mNowSortType;

    private String mFilterType;
    private String mFilterState;
    private String mFilterTrade;
    private String mFilterLevel;

    private int mCurrentPage = 1;

    private CustomProgressDialog mProgressDialog;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case CustomerLogic.LIST_GET_SUC: {
                    if (null != msg.obj) {
                        if (1 == mCurrentPage) {
                            mCustomerList.clear();
                        }
                        mCurrentPage++;
                        mCustomerList.addAll((Collection<? extends Customer>) msg.obj);
                        mCustomerAdapter.notifyDataSetChanged();
                        onLoadComplete();
                    }
                    break;
                }
                case CustomerLogic.LIST_GET_FAIL: {
                    Toast.makeText(mContext, "获取数据失败!",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                case CustomerLogic.LIST_GET_EXCEPTION: {
                    break;
                }
                case CustomerLogic.FILTER_LIST_GET_SUC: {
                    if (null != msg.obj) {
                        if (1 == mCurrentPage) {
                            mCustomerList.clear();
                        }
                        mCurrentPage++;
                        mCustomerList.addAll((Collection<? extends Customer>) msg.obj);
                        mCustomerAdapter.notifyDataSetChanged();
                        onLoadComplete();
                    }
                    break;
                }
                case CustomerLogic.FILTER_LIST_GET_FAIL: {
                    Toast.makeText(mContext, "获取数据失败!",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                case CustomerLogic.FILTER_LIST_GET_EXCEPTION: {
                    break;
                }
                case CustomerLogic.NET_ERROR: {
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
        setContentView(R.layout.customer_list);
        mContext = CustomerListActivity.this;
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
        mBackIv = (ImageView) findViewById(R.id.customer_list_back_iv);
        mBackIv.setOnClickListener(this);

        mAddCustomerIv = (ImageView) findViewById(R.id.customer_list_add_iv);
        mAddCustomerIv.setOnClickListener(this);

        initFilterView();
        initListView();
        initDrawerLayout();
    }

    private void initData() {
        mProgressDialog.show();
        CustomerLogic.list(mContext, mHandler, UserInfoManager.userInfo.getUserId(), String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE));
//
//        Customer customer = new Customer();
//        customer.setUserId(UserInfoManager.userInfo.getUserId());
//        customer.setName("大菠萝");
//        customer.setStatus("2");
//        CustomerLogic.save(mContext, mHandler, UserInfoManager.userInfo.getUserId(), JsonUtils.Object2Json(customer));

    }

    private void initFilterView() {
        mLevelRl = (RelativeLayout) findViewById(R.id.customer_list_filter_level_rl);
        mFilterLevelTv = (TextView) findViewById(R.id.customer_list_filter_level_tv);

        mTypeRl = (RelativeLayout) findViewById(R.id.customer_list_filter_type_rl);
        mFilterTypeTv = (TextView) findViewById(R.id.customer_list_filter_type_tv);

        mStateRl = (RelativeLayout) findViewById(R.id.customer_list_filter_status_rl);
        mFilterStateTv = (TextView) findViewById(R.id.customer_list_filter_status_tv);

        mTradeRl = (RelativeLayout) findViewById(R.id.customer_list_filter_is_deal_rl);
        mFilterTradeTv = (TextView) findViewById(R.id.customer_list_filter_is_deal_tv);


        mLevelRl.setOnClickListener(this);
        mTypeRl.setOnClickListener(this);
        mTradeRl.setOnClickListener(this);
        mStateRl.setOnClickListener(this);
    }

    private void setFilterViewDefalut() {
        mFilterLevelTv.setTextColor(getResources().getColor(
                R.color.gray_character));
        mFilterTypeTv.setTextColor(getResources().getColor(R.color.gray_character));
        mFilterStateTv.setTextColor(getResources().getColor(R.color.gray_character));
    }

    private void initListView() {
        mCustomerLv = (XListView) findViewById(R.id.customer_list_goods_xlv);
        mCustomerLv.setPullRefreshEnable(false);
        mCustomerLv.setPullLoadEnable(true);
        mCustomerLv.setAutoLoadEnable(true);
        mCustomerLv.setXListViewListener(this);
        mCustomerLv.setRefreshTime(getTime());

        mCustomerAdapter = new CustomerAdapter(mContext, mCustomerList, this);
        mCustomerLv.setAdapter(mCustomerAdapter);

        mCustomerLv.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        mCustomerLv.setOnItemClickListener(new OnItemClickListener() {

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

    }


    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.customer_dl);
        mFilterTv = (TextView) findViewById(R.id.customer_list_filter_tv);
        mFilterTv.setOnClickListener(this);

        mFilterConfrimTv = (TextView) findViewById(R.id.customer_list_filter_confirm_tv);
        mFilterConfrimTv.setOnClickListener(this);
        mFilterCancelTv = (TextView) findViewById(R.id.customer_list_filter_cancel_tv);
        mFilterCancelTv.setOnClickListener(this);
    }

    private void onLoadComplete() {
        mCustomerLv.stopRefresh();
        mCustomerLv.stopLoadMore();
        mCustomerLv.setRefreshTime(getTime());
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
        CustomerLogic.list(mContext, mHandler, UserInfoManager.userInfo.getUserId(), String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE));
    }

    @Override
    public void onClick(View item, View widget, int position, int which) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 201: {
                    mFilterLevelTv.setText(data.getStringExtra("text"));
                    mFilterLevel = data.getStringExtra("value");
                    if ("-1".equals(mFilterLevel)) {
                        mFilterTypeTv.setText("其它");
                    }
                    break;
                }
                case 202: {
                    mFilterTypeTv.setText(data.getStringExtra("text"));
                    mFilterType = data.getStringExtra("value");
                    if ("-1".equals(mFilterType)) {
                        mFilterTypeTv.setText("其它");
                    }
                    break;
                }
                case 203: {
                    mFilterTradeTv.setText(data.getStringExtra("text"));
                    mFilterTrade = data.getStringExtra("value");
                    if ("-1".equals(mFilterTrade)) {
                        mFilterTypeTv.setText("其它");
                    }
                    break;
                }
                case 204: {
                    mFilterStateTv.setText(data.getStringExtra("text"));
                    mFilterState = data.getStringExtra("value");
                    if ("-1".equals(mFilterState)) {
                        mFilterTypeTv.setText("其它");
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
            case R.id.customer_list_add_iv: {
                Intent intent = new Intent(CustomerListActivity.this, CustomerAddActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.customer_list_back_iv: {
                finish();
                break;
            }
            case R.id.customer_list_filter_tv: {
                isFilterOpen = !isFilterOpen;
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
            }
            case R.id.customer_list_filter_confirm_tv: {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                mProgressDialog.show();
                CustomerLogic.filterList(mContext, mHandler, UserInfoManager.userInfo.getUserId(),
                        String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE), mFilterLevel, mFilterType, mFilterTrade, mFilterState);
                break;
            }
            case R.id.customer_list_filter_cancel_tv: {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            }
            case R.id.customer_list_filter_level_rl: {
                Intent intent = new Intent(CustomerListActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "CUS_LEVEL");
                startActivityForResult(intent, 201);
                break;
            }
            case R.id.customer_list_filter_type_rl: {
                Intent intent = new Intent(CustomerListActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "CUSTOMER_TYPE_B");
                startActivityForResult(intent, 202);
                break;
            }
            case R.id.customer_list_filter_is_deal_rl: {
                Intent intent = new Intent(CustomerListActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "TRADE_FLG");
                startActivityForResult(intent, 203);
                break;
            }
            case R.id.customer_list_filter_status_rl: {
                Intent intent = new Intent(CustomerListActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "FOLLOW_STATUS");
                startActivityForResult(intent, 204);
                break;
            }

            default: {
                break;
            }
        }
    }
}
