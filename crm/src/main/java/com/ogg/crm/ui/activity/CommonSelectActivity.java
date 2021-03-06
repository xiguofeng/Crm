package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogg.crm.R;
import com.ogg.crm.entity.CustomerInfoCategory;
import com.ogg.crm.service.ConfigInfoService;
import com.ogg.crm.ui.adapter.CustomerInfoCategoryAdapter;
import com.ogg.crm.ui.view.listview.CoustomListView;

import java.util.ArrayList;


public class CommonSelectActivity extends Activity implements OnClickListener {

    private TextView mTitleTv;

    private CoustomListView mLv;
    private CustomerInfoCategoryAdapter mCustomerInfoCategoryAdapter;
    private ArrayList<CustomerInfoCategory> mCustomerInfoCategories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_select);
        initView();
        initData();
    }

    private void initView() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.9
        // p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.6f;
        getWindow().addFlags(LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(p);

        mTitleTv = (TextView) findViewById(R.id.common_select_title_tv);

        mLv = (CoustomListView) findViewById(R.id.common_select_lv);
        mCustomerInfoCategoryAdapter = new CustomerInfoCategoryAdapter(CommonSelectActivity.this, mCustomerInfoCategories);
        mLv.setAdapter(mCustomerInfoCategoryAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("value", mCustomerInfoCategories.get(position).getDefaultValue());
                intent.putExtra("text", mCustomerInfoCategories.get(position).getDefaultText());
                setResult(RESULT_OK, intent);
                CommonSelectActivity.this.finish();
            }
        });
    }

    private void initData() {
        String category = getIntent().getStringExtra("category");
        if (!TextUtils.isEmpty(category)) {
            mCustomerInfoCategories.clear();
            if (!ConfigInfoService.sCustomerCategoryInfoMap.isEmpty() && null != ConfigInfoService.sCustomerCategoryInfoMap.get(category)) {
                mCustomerInfoCategories.addAll(ConfigInfoService.sCustomerCategoryInfoMap.get(category));
                mCustomerInfoCategoryAdapter.notifyDataSetChanged();

                if ("CUSTOMER_TYPE_B".equals(category)) {
                    mTitleTv.setText("选择客户类型");
                } else if ("COMPANY_TYPE_B".equals(category)) {
                    mTitleTv.setText("选择公司类型");
                } else if ("FOLLOW_STATUS".equals(category)) {
                    mTitleTv.setText("状态");
                } else if ("CUS_LEVEL".equals(category)) {
                    mTitleTv.setText("选择客户等级");
                } else if ("TRADE_FLG".equals(category)) {
                    mTitleTv.setText("是否交易");
                }
            } else if (!CustomerAddActivity.sCategoryInfoMap.isEmpty() && null != CustomerAddActivity.sCategoryInfoMap.get(category)) {
                mCustomerInfoCategories.addAll(CustomerAddActivity.sCategoryInfoMap.get(category));
                mCustomerInfoCategoryAdapter.notifyDataSetChanged();

                if ("CUSTOMER_TYPE_B".equals(category)) {
                    mTitleTv.setText("选择客户类型");
                } else if ("COMPANY_TYPE_B".equals(category)) {
                    mTitleTv.setText("选择公司类型");
                } else if ("FOLLOW_STATUS".equals(category)) {
                    mTitleTv.setText("状态");
                } else if ("CUS_LEVEL".equals(category)) {
                    mTitleTv.setText("选择客户等级");
                } else if ("TRADE_FLG".equals(category)) {
                    mTitleTv.setText("是否交易");
                }
            } else {
                Toast.makeText(CommonSelectActivity.this, "数据加载错误!", Toast.LENGTH_SHORT).show();
                CommonSelectActivity.this.finish();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }
}
