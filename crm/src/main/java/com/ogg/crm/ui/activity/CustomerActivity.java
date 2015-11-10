package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.entity.Customer;
import com.ogg.crm.ui.adapter.CustomerAdapter;
import com.ogg.crm.ui.utils.ListItemClickHelp;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.ui.view.listview.XListView;
import com.ogg.crm.utils.ActivitiyInfoManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CustomerActivity extends Activity implements OnClickListener,
        ListItemClickHelp, XListView.IXListViewListener {

    private Context mContext;

    private LinearLayout mCompositeLl;
    private TextView mCompositeTv;
    private ImageView mCompositeIv;

    private LinearLayout mPriceLl;
    private TextView mPriceTv;
    private ImageView mPriceIv;

    private LinearLayout mSalesLl;
    private TextView mSalesTv;
    private ImageView mSalesIv;

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
    private int mCurrentPageNum = 1;

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);
        mContext = CustomerActivity.this;
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
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog.show();
    }

    private void initFilterView() {
        mCompositeLl = (LinearLayout) findViewById(R.id.customer_list_composite_ll);
        mCompositeTv = (TextView) findViewById(R.id.customer_list_composite_tv);
        mCompositeIv = (ImageView) findViewById(R.id.customer_list_composite_iv);

        mPriceLl = (LinearLayout) findViewById(R.id.customer_list_price_ll);
        mPriceTv = (TextView) findViewById(R.id.customer_list_price_tv);
        mPriceIv = (ImageView) findViewById(R.id.customer_list_price_iv);

        mSalesLl = (LinearLayout) findViewById(R.id.customer_list_sales_ll);
        mSalesTv = (TextView) findViewById(R.id.customer_list_sales_tv);
        mSalesIv = (ImageView) findViewById(R.id.customer_list_sales_iv);

        mCompositeLl.setOnClickListener(this);
        mPriceLl.setOnClickListener(this);
        mSalesLl.setOnClickListener(this);
    }

    private void setFilterViewDefalut() {
        mCompositeTv.setTextColor(getResources().getColor(
                R.color.gray_character));
        mPriceTv.setTextColor(getResources().getColor(R.color.gray_character));
        mSalesTv.setTextColor(getResources().getColor(R.color.gray_character));

        mCompositeIv.setImageDrawable(getResources().getDrawable(
                R.drawable.arrow_down_top));
        mPriceIv.setImageDrawable(getResources().getDrawable(
                R.drawable.arrow_down_top));
        mSalesIv.setImageDrawable(getResources().getDrawable(
                R.drawable.arrow_down_top));
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

    }

    @Override
    public void onClick(View item, View widget, int position, int which) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customer_list_filter_tv: {
                if (!isFilterOpen) {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                }
                isFilterOpen = !isFilterOpen;
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
            default: {
                break;
            }
        }
    }
}
