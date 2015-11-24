package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.ogg.crm.R;
import com.ogg.crm.entity.Menu;
import com.ogg.crm.ui.adapter.CustomMenuAdapter;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.ui.view.gridview.CustomGridView;
import com.ogg.crm.utils.ActivitiyInfoManager;

import java.util.ArrayList;

public class CustomerActivity extends Activity implements OnClickListener {

    private Context mContext;

    private ImageView mBackIv;

    private CustomGridView mCategoryGv;
    private CustomMenuAdapter mCustomMenuAdapter;
    private ArrayList<Menu> mMenuList = new ArrayList<Menu>();


    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);
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
        mBackIv = (ImageView) findViewById(R.id.customer_back_iv);
        mBackIv.setOnClickListener(this);

        mCategoryGv = (CustomGridView) findViewById(R.id.customer_gv);
        mCategoryGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (0 == position) {
                    Intent intent = new Intent(CustomerActivity.this, CustomerAddActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(CustomerActivity.this, CustomerListActivity.class);
                    startActivity(intent);
                }
            }
        });
        mCustomMenuAdapter = new CustomMenuAdapter(this, mMenuList);
        mCategoryGv.setAdapter(mCustomMenuAdapter);

    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            Menu menu = new Menu();
            menu.setLocalImage(R.drawable.ic_launcher);
            if (i == 0) {
                menu.setName(getResources().getString(R.string.my_customer));
            } else {
                menu.setName(getResources().getString(R.string.public_customer));
            }
            mMenuList.add(menu);
        }
        mCustomMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customer_back_iv: {

                break;
            }

            default: {
                break;
            }
        }
    }
}
