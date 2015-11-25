package com.ogg.crm.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.entity.CustomerInfoCategory;

import java.util.ArrayList;

public class CustomerInfoCategoryAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<CustomerInfoCategory> mDatas;

	private LayoutInflater mInflater;

	public CustomerInfoCategoryAdapter(Context context, ArrayList<CustomerInfoCategory> datas) {
		this.mContext = context;
		this.mDatas = datas;
		mInflater = LayoutInflater.from(mContext);
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
			convertView = mInflater.inflate(R.layout.list_customer_info_category_item, null);

			holder = new ViewHolder();
			holder.mNameTv = (TextView) convertView
					.findViewById(R.id.item_name_tv);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mNameTv.setText(mDatas.get(position).getDefaultText());

		return convertView;
	}

	static class ViewHolder {

		public TextView mNameTv;

	}


}
