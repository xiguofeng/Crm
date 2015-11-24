package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.ogg.crm.R;


public class CommonSelectActivity extends Activity implements OnClickListener {


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
	}

	private void initData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}
}
