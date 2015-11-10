package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ogg.crm.R;


public class MainActivity extends Activity {
	
	private Context mContext;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = MainActivity.this;
		//UserLogic.login(mContext, mHandler);
	}
}
