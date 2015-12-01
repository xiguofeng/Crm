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

public class ReportFormActivity extends Activity implements OnClickListener {

    private Context mContext;

    private ImageView mBackIv;

    private CustomGridView mCategoryGv;
    private CustomMenuAdapter mMenuAdapter;
    private ArrayList<Menu> mMenuList = new ArrayList<Menu>();


    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_form);
        mContext = ReportFormActivity.this;
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
        mBackIv = (ImageView) findViewById(R.id.report_form_back_iv);
        mBackIv.setOnClickListener(this);

        mCategoryGv = (CustomGridView) findViewById(R.id.report_form_gv);
        mCategoryGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (0 == position) {
                    intent = new Intent(ReportFormActivity.this, ReportFormDetailActivity.class);
                } else {
                    intent = new Intent(ReportFormActivity.this, ReportFormDetailActivity.class);
                }
                startActivity(intent);
            }
        });
        mMenuAdapter = new CustomMenuAdapter(this, mMenuList);
        mCategoryGv.setAdapter(mMenuAdapter);

    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            Menu menu = new Menu();
            if (i == 0) {
                menu.setLocalImage(R.drawable.customer_report_form);
                menu.setName(getResources().getString(R.string.customer_report_form));
            } else {
                menu.setLocalImage(R.drawable.follow_report_form);
                menu.setName(getResources().getString(R.string.follow_report_form));
            }
            mMenuList.add(menu);
        }
        mMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sms_back_iv: {

                break;
            }

            default: {
                break;
            }
        }
    }
}
