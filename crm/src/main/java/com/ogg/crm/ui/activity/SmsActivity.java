package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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

public class SmsActivity extends Activity implements OnClickListener {

    private Context mContext;

    private ImageView mBackIv;

    private CustomGridView mCategoryGv;
    private CustomMenuAdapter mMenuAdapter;
    private ArrayList<Menu> mMenuList = new ArrayList<Menu>();


    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms);
        mContext = SmsActivity.this;
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
        mBackIv = (ImageView) findViewById(R.id.sms_back_iv);
        mBackIv.setOnClickListener(this);

        mCategoryGv = (CustomGridView) findViewById(R.id.sms_gv);
        mCategoryGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (0 == position) {
                    intent = new Intent(SmsActivity.this, SmsListActivity.class);
                    intent.setAction(SmsListActivity.ORIGIN_FROM_MAIN_KEY);
                } else {
                    intent = new Intent(SmsActivity.this, SmsSendActivity.class);
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
                menu.setLocalImage(R.drawable.sms_templ);
                menu.setName(getResources().getString(R.string.sms_template));
            } else {
                menu.setLocalImage(R.drawable.sms_send);
                menu.setName(getResources().getString(R.string.sms_send));
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            HomeActivity.showMainByOnkey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
