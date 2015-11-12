package com.ogg.crm.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.entity.Appointment;
import com.ogg.crm.ui.utils.ListItemClickHelp;

import java.util.ArrayList;
import java.util.HashMap;

public class AppointmentAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<Appointment> mDatas;

	private ListItemClickHelp mCallback;

	private LayoutInflater mInflater;

	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> mIsSelected = new HashMap<Integer, Boolean>();

	public AppointmentAdapter(Context context, ArrayList<Appointment> datas,
                              ListItemClickHelp callback) {
		this.mContext = context;
		this.mDatas = datas;
		this.mCallback = callback;
		mInflater = LayoutInflater.from(mContext);
	}

	public void initCheck() {
		for (int i = 0; i < mDatas.size(); i++) {
			getmIsSelected().put(i, false);
		}
	}

	public void initChecked() {
		for (int i = 0; i < mDatas.size(); i++) {
			getmIsSelected().put(i, true);
		}
	}

	@Override
	public int getCount() {
		if (mDatas != null) {
			return mDatas.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_appointment_item, null);

			holder = new ViewHolder();
			holder.mNameTv = (TextView) convertView
					.findViewById(R.id.list_appointment_user_name_tv);
			holder.mPhoneTv = (TextView) convertView
					.findViewById(R.id.list_appointment_phone_tv);
			holder.mAppointmentDetail = (TextView) convertView
					.findViewById(R.id.list_appointment_detail_tv);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mNameTv.setText(mDatas.get(position).getName());
		holder.mPhoneTv.setText(mDatas.get(position).getId());
		holder.mAppointmentDetail.setText(mDatas.get(position).getId());

		return convertView;
	}

	static class ViewHolder {

		public TextView mNameTv;

		public TextView mPhoneTv;

		public TextView mAppointmentDetail;

		public ImageView mUpdateIv;

		public LinearLayout mEditLl;

		public LinearLayout mDelLl;

		public CheckBox mDefaultCb;
	}

	public static HashMap<Integer, Boolean> getmIsSelected() {
		return mIsSelected;
	}

	public static void setmIsSelected(HashMap<Integer, Boolean> mIsSelected) {
		AppointmentAdapter.mIsSelected = mIsSelected;
	}

}
