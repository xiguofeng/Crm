package com.ogg.crm.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.Customer;
import com.ogg.crm.network.config.MsgRequest;
import com.ogg.crm.network.logic.CustomerLogic;
import com.ogg.crm.ui.adapter.CustomerSelectAdapter;
import com.ogg.crm.ui.utils.ListItemCheckClickHelp;
import com.ogg.crm.ui.view.AutoClearEditText;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.ui.view.listview.XListView;
import com.ogg.crm.utils.UserInfoManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class CustomerSelectListActivity extends Activity implements OnClickListener,
        ListItemCheckClickHelp, XListView.IXListViewListener,TextWatcher {

    private Context mContext;

    private RelativeLayout mLevelRl;
    private TextView mFilterLevelTv;

    private RelativeLayout mTypeRl;
    private TextView mFilterTypeTv;

    private RelativeLayout mTradeRl;
    private TextView mFilterTradeTv;

    private RelativeLayout mStateRl;
    private TextView mFilterStateTv;

    private TextView mSearchTv;
    private AutoClearEditText mSearchKeyEt;

    private XListView mCustomerLv;
    private CustomerSelectAdapter mCustomerAdapter;
    private ArrayList<Customer> mCustomerList = new ArrayList<Customer>();

    private DrawerLayout mDrawerLayout;
    private TextView mFilterTv;
    private boolean isFilterOpen = false;

    private TextView mFilterConfrimTv;
    private TextView mFilterCancelTv;

    private ImageView mBackIv;
    private ImageView mAddCustomerIv;

    private Button mClearBtn;

    private String mNowSortType;

    private String mFilterType;
    private String mFilterState;
    private String mFilterTrade;
    private String mFilterLevel;

    private int mCurrentPage = 1;

    private String mKeyWord;

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
        setContentView(R.layout.customer_select_list);
        mContext = CustomerSelectListActivity.this;
        mProgressDialog = new CustomProgressDialog(mContext);
//        if (!ActivitiyInfoManager.activitityMap
//                .containsKey(ActivitiyInfoManager
//                        .getCurrentActivityName(mContext))) {
//            ActivitiyInfoManager.activitityMap
//                    .put(ActivitiyInfoManager.getCurrentActivityName(mContext),
//                            this);
//        }
        initView();
        initData();

    }

    private void initView() {
        mBackIv = (ImageView) findViewById(R.id.customer_list_back_iv);
        mBackIv.setOnClickListener(this);

        mAddCustomerIv = (ImageView) findViewById(R.id.customer_list_add_iv);
        mAddCustomerIv.setOnClickListener(this);
        mAddCustomerIv.setVisibility(View.INVISIBLE);

        mSearchTv = (TextView) findViewById(R.id.customer_list_search_tv);
        mSearchTv.setOnClickListener(this);

        mSearchKeyEt = (AutoClearEditText) findViewById(R.id.customer_list_search_et);

        initFilterView();
        initListView();
        initDrawerLayout();
    }

    private void initData() {
        mProgressDialog.show();
        CustomerLogic.filterList(mContext, mHandler, UserInfoManager.getUserId(mContext),
                String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE), mKeyWord, mFilterLevel, mFilterType, mFilterTrade, mFilterState);
//
//        Customer customer = new Customer();
//        customer.setUserId(UserInfoManager.getUserId(mContext));
//        customer.setName("大菠萝");
//        customer.setStatus("2");
//        CustomerLogic.save(mContext, mHandler, UserInfoManager.getUserId(mContext), JsonUtils.Object2Json(customer));

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

        mClearBtn = (Button) findViewById(R.id.customer_list_filter_clear_btn);
        mClearBtn.setOnClickListener(this);
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

        mCustomerAdapter = new CustomerSelectAdapter(mContext, mCustomerList, this);
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
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SmsSendActivity.CUSTOMER_KEY,
                            mCustomerList.get(position - 1));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
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


    private void search(String keyword) {
        mProgressDialog.show();
        mCurrentPage = 1;
        mKeyWord =keyword;
        CustomerLogic.filterList(mContext, mHandler, UserInfoManager.getUserId(mContext),
                String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE), mKeyWord, mFilterLevel, mFilterType, mFilterTrade, mFilterState);
    }

    private void filter() {
        mProgressDialog.show();
        mCurrentPage = 1;
        CustomerLogic.filterList(mContext, mHandler, UserInfoManager.getUserId(mContext),
                String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE), mKeyWord, mFilterLevel, mFilterType, mFilterTrade, mFilterState);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        CustomerLogic.filterList(mContext, mHandler, UserInfoManager.getUserId(mContext),
                String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE), mKeyWord, mFilterLevel, mFilterType, mFilterTrade, mFilterState);
    }

    @Override
    public void onClick(View item, View widget, int position, int which,boolean isCheck) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mSearchKeyEt.setClearIconVisible(s.length() > 0);
    }

    @SuppressLint("NewApi")
    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            mKeyWord = "";
            initData();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 201: {
                    mFilterLevelTv.setText(data.getStringExtra("text"));
                    mFilterLevel = data.getStringExtra("value");
                    if ("-1".equals(mFilterLevel)) {
                        mFilterLevelTv.setText("其它");
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
                        mFilterTradeTv.setText("其它");
                    }
                    break;
                }
                case 204: {
                    mFilterStateTv.setText(data.getStringExtra("text"));
                    mFilterState = data.getStringExtra("value");
                    if ("-1".equals(mFilterState)) {
                        mFilterStateTv.setText("其它");
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
                Intent intent = new Intent(CustomerSelectListActivity.this, CustomerAddActivity.class);
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
                filter();
                break;
            }
            case R.id.customer_list_filter_cancel_tv: {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            }
            case R.id.customer_list_filter_level_rl: {
                Intent intent = new Intent(CustomerSelectListActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "CUS_LEVEL");
                startActivityForResult(intent, 201);
                break;
            }
            case R.id.customer_list_filter_type_rl: {
                Intent intent = new Intent(CustomerSelectListActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "CUSTOMER_TYPE_B");
                startActivityForResult(intent, 202);
                break;
            }
            case R.id.customer_list_filter_is_deal_rl: {
                Intent intent = new Intent(CustomerSelectListActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "TRADE_FLG");
                startActivityForResult(intent, 203);
                break;
            }
            case R.id.customer_list_filter_status_rl: {
                Intent intent = new Intent(CustomerSelectListActivity.this, CommonSelectActivity.class);
                intent.putExtra("category", "FOLLOW_STATUS");
                startActivityForResult(intent, 204);
                break;
            }
            case R.id.customer_list_search_tv: {
                if (!TextUtils.isEmpty(mSearchKeyEt.getText().toString())) {
                    search(mSearchKeyEt.getText().toString());
                } else {
                    Toast.makeText(mContext, "请输入用户名!", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.customer_list_filter_clear_btn: {
                mFilterType = "";
                mFilterState = "";
                mFilterTrade = "";
                mFilterLevel = "";

                mFilterLevelTv.setText("");
                mFilterTypeTv.setText("");
                mFilterTradeTv.setText("");
                mFilterStateTv.setText("");
            }

            default: {
                break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
