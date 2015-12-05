package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
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
import com.ogg.crm.entity.User;
import com.ogg.crm.network.config.MsgRequest;
import com.ogg.crm.network.logic.CustomerLogic;
import com.ogg.crm.ui.adapter.CustomerAdapter;
import com.ogg.crm.ui.utils.ListItemCheckClickHelp;
import com.ogg.crm.ui.view.AutoClearEditText;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.ui.view.listview.XListView;
import com.ogg.crm.utils.ActivitiyInfoManager;
import com.ogg.crm.utils.UserInfoManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CustomerListActivity extends Activity implements OnClickListener,
        ListItemCheckClickHelp, XListView.IXListViewListener {

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
    private CustomerAdapter mCustomerAdapter;
    private ArrayList<Customer> mCustomerList = new ArrayList<Customer>();

    private DrawerLayout mDrawerLayout;
    private TextView mFilterTv;
    private boolean isFilterOpen = false;

    private TextView mFilterConfrimTv;
    private TextView mFilterCancelTv;

    private ImageView mBackIv;
    private ImageView mAddCustomerIv;

    private Button mDistributionBtn;
    private Button mGiveUpBtn;

    private Button mClearBtn;

    private String mNowSortType;

    private String mFilterType;
    private String mFilterState;
    private String mFilterTrade;
    private String mFilterLevel;

    public static ArrayList<User> sUserList = new ArrayList<User>();

    private int mCurrentPage = 1;
    private boolean mIsHasSelect = false;

    private HashMap<String, Boolean> mSelect = new HashMap<String, Boolean>();

    public static boolean isRefresh = false;

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
                            mCustomerAdapter.getmIsSelected().clear();
                        }
                        mCurrentPage++;
                        mCustomerList.addAll((Collection<? extends Customer>) msg.obj);
                        mCustomerAdapter.initCheck();
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
                            mCustomerAdapter.getmIsSelected().clear();
                        }
                        mCurrentPage++;
                        mCustomerList.addAll((Collection<? extends Customer>) msg.obj);
                        mCustomerAdapter.initCheck();
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

    Handler mDisHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case CustomerLogic.DIS_USER_LIST_GET_SUC: {
                    if (null != msg.obj) {
                        sUserList.clear();
                        sUserList.addAll((Collection<? extends User>) msg.obj);
                        Intent intent = new Intent(CustomerListActivity.this, UserSelectActivity.class);
                        startActivityForResult(intent, 205);
                    }
                    break;
                }
                case CustomerLogic.DIS_USER_LIST_GET_FAIL: {
                    if (null != msg.obj) {
                        Toast.makeText(mContext, (String) msg.obj,
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case CustomerLogic.DIS_USER_LIST_GET_EXCEPTION: {
                    break;
                }
                case CustomerLogic.DIS_CUS_SET_SUC: {
                    Toast.makeText(mContext, "指派成功!",
                            Toast.LENGTH_SHORT).show();
                    mIsHasSelect = false;
                    mSelect.clear();
                    mCustomerAdapter.getmIsSelected().clear();
                    mCurrentPage = 1;
                    mProgressDialog.show();
                    CustomerLogic.list(mContext, mHandler, UserInfoManager.getUserId(mContext), String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE));
                    break;
                }
                case CustomerLogic.DIS_CUS_SET_FAIL: {
                    if (null != msg.obj) {
                        Toast.makeText(mContext, (String) msg.obj,
                                Toast.LENGTH_SHORT).show();
                    }else{
                    Toast.makeText(mContext, "指派失败!",
                            Toast.LENGTH_SHORT).show();
                    break;
                    }
                }
                case CustomerLogic.DIS_CUS_SET_EXCEPTION: {
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

    Handler mGiveUpHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case CustomerLogic.GIVE_UP_SET_SUC: {
                    Toast.makeText(mContext, "放弃客户成功!",
                            Toast.LENGTH_SHORT).show();
                    mIsHasSelect = false;
                    mSelect.clear();
                    mCustomerAdapter.getmIsSelected().clear();
                    mCurrentPage = 1;
                    mProgressDialog.show();
                    CustomerLogic.list(mContext, mHandler, UserInfoManager.getUserId(mContext), String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE));
                    break;
                }
                case CustomerLogic.GIVE_UP_SET_FAIL: {
                    if (null != msg.obj) {
                        Toast.makeText(mContext, (String) msg.obj,
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case CustomerLogic.GIVE_UP_SET_EXCEPTION: {
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

    @Override
    protected void onResume() {
        super.onResume();
        if (isRefresh) {
            isRefresh = !isRefresh;
            initData();
        }
    }

    private void initView() {
        mBackIv = (ImageView) findViewById(R.id.customer_list_back_iv);
        mBackIv.setOnClickListener(this);

        mAddCustomerIv = (ImageView) findViewById(R.id.customer_list_add_iv);
        mAddCustomerIv.setOnClickListener(this);

        mSearchTv = (TextView) findViewById(R.id.customer_list_search_tv);
        mSearchTv.setOnClickListener(this);

        mSearchKeyEt = (AutoClearEditText) findViewById(R.id.customer_list_search_et);

        mDistributionBtn = (Button) findViewById(R.id.customer_list_distribution_btn);
        mDistributionBtn.setOnClickListener(this);

        mGiveUpBtn = (Button) findViewById(R.id.customer_list_give_up_btn);
        mGiveUpBtn.setOnClickListener(this);

        initFilterView();
        initListView();
        initDrawerLayout();
    }

    private void initData() {
        mProgressDialog.show();
        mCurrentPage = 1;
        CustomerLogic.list(mContext, mHandler, UserInfoManager.getUserId(mContext), String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE));
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

        mCustomerAdapter = new CustomerAdapter(mContext, mCustomerList, this);
        mCustomerLv.setAdapter(mCustomerAdapter);
        mCustomerAdapter.initCheck();
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
                    Intent intent = new Intent(CustomerListActivity.this,
                            CustomerDetailActivity.class);
                    intent.setAction(CustomerDetailActivity.MY_ACTION);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(CustomerDetailActivity.CUSTOMER_KEY,
                            mCustomerList.get(position - 1));
                    intent.putExtras(bundle);
                    startActivity(intent);
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
        CustomerLogic.filterList(mContext, mHandler, UserInfoManager.getUserId(mContext),
                String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE), keyword, mFilterLevel, mFilterType, mFilterTrade, mFilterState);
    }

    private void filter() {
        mProgressDialog.show();
        mCurrentPage = 1;
        CustomerLogic.filterList(mContext, mHandler, UserInfoManager.getUserId(mContext),
                String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE), "", mFilterLevel, mFilterType, mFilterTrade, mFilterState);
    }

    private String getCustomerIds() {
        String customerIds = "";
        for (Map.Entry<String, Boolean> entry : mSelect.entrySet()) {
            if (entry.getValue()) {
                if (TextUtils.isEmpty(customerIds)) {
                    customerIds = entry.getKey();
                } else {
                    customerIds = customerIds + "," + entry.getKey();
                }
            }
        }
        return customerIds;
    }

    private void showDialog() {
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE: {
                        mProgressDialog.show();
                        CustomerLogic.giveUpCustomer(mContext,mGiveUpHandler,UserInfoManager.getUserId(mContext),getCustomerIds());
                        break;
                    }
                    case Dialog.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否放弃客户?"); //设置内容
        //builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确认", dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        CustomerLogic.list(mContext, mHandler, UserInfoManager.getUserId(mContext), String.valueOf(mCurrentPage), String.valueOf(MsgRequest.PAGE_SIZE));
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
                case 205: {
                    String disUserName = data.getStringExtra("name");
                    String disUserId = data.getStringExtra("id");
                    String customersIds = getCustomerIds();
                    Log.e("xxx_customersIds", "ids:" + customersIds);
                    CustomerLogic.disCusSet(mContext, mDisHandler, UserInfoManager.getUserId(mContext), disUserId, customersIds);
                    break;
                }
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isCheck) {
        mSelect.put(mCustomerList.get(position).getCustomerId(), isCheck);
        if (isCheck) {
            mDistributionBtn.setBackgroundColor(getResources().getColor(
                    R.color.blue_bg));
        }
        boolean isHasAllSelect = true;
        boolean isHasSelect = false;
        for (Map.Entry<String, Boolean> entry : mSelect.entrySet()) {
            if (!entry.getValue()) {
                isHasAllSelect = false;
            }
            if (entry.getValue()) {
                isHasSelect = true;
                mIsHasSelect = true;
            }
        }

        if (!isHasSelect) {
//            mDistributionBtn.setBackgroundColor(getResources().getColor(
//                    R.color.gray_bg));
        }
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
                filter();
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
            case R.id.customer_list_distribution_btn: {
                if (mIsHasSelect) {
                    mProgressDialog.show();
                    CustomerLogic.getDisUserList(mContext, mDisHandler);
                } else {
                    Toast.makeText(mContext, "请选择客户!",
                            Toast.LENGTH_SHORT).show();
                }

//                final CharSequence[] items = {"Red", "Green", "Blue"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Pick a color");
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int item) {
//                        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
            }
            case R.id.customer_list_give_up_btn: {
                if (mIsHasSelect) {
                    showDialog();
                } else {
                    Toast.makeText(mContext, "请选择客户!",
                            Toast.LENGTH_SHORT).show();
                }
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
