package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.entity.User;
import com.ogg.crm.ui.adapter.UserAdapter;

import java.util.ArrayList;


public class UserSelectActivity extends Activity implements OnClickListener {

    private TextView mTitleTv;

    private ListView mLv;
    private UserAdapter mUserAdapter;
    private ArrayList<User> mUserList = new ArrayList<User>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_select);
        initView();
        initData();
    }

    private void initView() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.9
        p.height = (int) (d.getHeight() * 0.6);
        // p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.6f;
        getWindow().addFlags(LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(p);

        mTitleTv = (TextView) findViewById(R.id.user_select_title_tv);

        mLv = (ListView) findViewById(R.id.user_select_lv);
        mUserAdapter = new UserAdapter(UserSelectActivity.this, mUserList);
        mLv.setAdapter(mUserAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("name", mUserList.get(position).getLogonName());
                intent.putExtra("id", mUserList.get(position).getUserId());
                setResult(RESULT_OK, intent);
                UserSelectActivity.this.finish();
            }
        });
    }

    private void initData() {
        if (CustomerListActivity.sUserList.size() > 0) {
            mUserList.clear();
            mUserList.addAll(CustomerListActivity.sUserList);
            mUserAdapter.notifyDataSetChanged();
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
