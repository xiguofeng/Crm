package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
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
    private TextView mLevelTv;

    private RelativeLayout mTypeRl;
    private TextView mTypeTv;

    private RelativeLayout mStateRl;
    private TextView mStateTv;

    private XListView mGoodsLv;
    private CustomerAdapter mCustomerAdapter;
    private ArrayList<Customer> mCustomerList = new ArrayList<Customer>();

    private DrawerLayout mDrawerLayout;
    private TextView mFilterTv;
    private boolean isFilterOpen = false;

    private TextView mFilterConfrimTv;
    private TextView mFilterCancelTv;


    private ImageView mBackIv;

    private String mNowSortType;

    private int mCurrentPage = 1;

    private CustomProgressDialog mProgressDialog;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case CustomerLogic.LIST_GET_SUC: {
                    if (null != msg.obj) {
                        mCurrentPage++;
                        mCustomerList.clear();
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

        initFilterView();
        initListView();
        initDrawerLayout();
    }

    private void initData() {
        mProgressDialog.show();
        CustomerLogic.list(mContext, mHandler, UserInfoManager.userInfo.getUserId(), String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE));
//        CustomerLogic.filterList(mContext, mHandler, UserInfoManager.userInfo.getUserId(), String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE), "", "", "", "");
//
//        Customer customer = new Customer();
//        customer.setUserId(UserInfoManager.userInfo.getUserId());
//        customer.setName("大菠萝");
//        customer.setStatus("2");
//        CustomerLogic.save(mContext, mHandler, UserInfoManager.userInfo.getUserId(), JsonUtils.Object2Json(customer));

    }

    private void initFilterView() {
        mLevelRl = (RelativeLayout) findViewById(R.id.customer_list_filter_level_rl);
        mLevelTv = (TextView) findViewById(R.id.customer_list_filter_level_tv);

        mTypeRl = (RelativeLayout) findViewById(R.id.customer_list_filter_type_rl);
        mTypeTv = (TextView) findViewById(R.id.customer_list_filter_type_tv);

        mStateRl = (RelativeLayout) findViewById(R.id.customer_list_filter_status_rl);
        mStateTv = (TextView) findViewById(R.id.customer_list_filter_status_tv);

        mLevelRl.setOnClickListener(this);
        mTypeRl.setOnClickListener(this);
        mStateRl.setOnClickListener(this);
    }

    private void setFilterViewDefalut() {
        mLevelTv.setTextColor(getResources().getColor(
                R.color.gray_character));
        mTypeTv.setTextColor(getResources().getColor(R.color.gray_character));
        mStateTv.setTextColor(getResources().getColor(R.color.gray_character));
    }

    private void initListView() {
        mGoodsLv = (XListView) findViewById(R.id.customer_list_goods_xlv);
        mGoodsLv.setPullRefreshEnable(false);
        mGoodsLv.setPullLoadEnable(true);
        mGoodsLv.setAutoLoadEnable(true);
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
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // Log.i(TAG, "正在滚动");
            }
        });
        mGoodsLv.setOnItemClickListener(new OnItemClickListener() {

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
        mGoodsLv.stopRefresh();
        mGoodsLv.stopLoadMore();
        mGoodsLv.setRefreshTime(getTime());
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
    public void onClick(View v) {
        switch (v.getId()) {
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
                break;
            }
            case R.id.customer_list_filter_cancel_tv: {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            }
            case R.id.customer_list_filter_level_rl: {
                break;
            }
            case R.id.customer_list_filter_type_rl: {
                break;
            }
            case R.id.customer_list_filter_status_rl: {
                break;
            }

            default: {
                break;
            }
        }
    }
}
