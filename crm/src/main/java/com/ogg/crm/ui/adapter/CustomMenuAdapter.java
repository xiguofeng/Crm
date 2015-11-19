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
import com.ogg.crm.entity.Menu;
import com.ogg.crm.ui.utils.ListItemClickHelp;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomMenuAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<Menu> mDatas;

	private ListItemClickHelp mCallback;

	private LayoutInflater mInflater;

	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> mIsSelected = new HashMap<Integer, Boolean>();

	public CustomMenuAdapter(Context context, ArrayList<Menu> datas) {
		this.mContext = context;
		this.mDatas = datas;
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
			convertView = mInflater.inflate(R.layout.gv_custom_item, null);

			holder = new ViewHolder();
			holder.mNameTv = (TextView) convertView
					.findViewById(R.id.gv_menu_common_name_tv);
			holder.mImageIv = (ImageView) convertView
					.findViewById(R.id.gv_menu_common_iv);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mNameTv.setText(mDatas.get(position).getName());
        holder.mImageIv.setBackgroundResource(R.drawable.tab_person_pressed);
		return convertView;
	}

	static class ViewHolder {

		public TextView mNameTv;

		public ImageView mImageIv;

		public TextView mMenuDetail;

		public ImageView mUpdateIv;

		public LinearLayout mEditLl;

		public LinearLayout mDelLl;

		public CheckBox mDefaultCb;
	}

	public static HashMap<Integer, Boolean> getmIsSelected() {
		return mIsSelected;
	}

	public static void setmIsSelected(HashMap<Integer, Boolean> mIsSelected) {
		CustomMenuAdapter.mIsSelected = mIsSelected;
	}

}
