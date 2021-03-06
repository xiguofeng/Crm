package com.ogg.crm.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.ogg.crm.R;
import com.ogg.crm.entity.Customer;
import com.ogg.crm.ui.utils.ListItemCheckClickHelp;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Customer> mDatas;

    private ListItemCheckClickHelp mCallback;

    private LayoutInflater mInflater;

    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> mIsSelected = new HashMap<Integer, Boolean>();

    public CustomerAdapter(Context context, ArrayList<Customer> datas,
                           ListItemCheckClickHelp callback) {
        this.mContext = context;
        this.mDatas = datas;
        this.mCallback = callback;
        mInflater = LayoutInflater.from(mContext);
    }

    public void initCheck() {
        for (int i = 0; i < mDatas.size(); i++) {
            if (null == getmIsSelected().get(i)) {
                getmIsSelected().put(i, false);
            }
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
            convertView = mInflater.inflate(R.layout.list_customer_item, null);

            holder = new ViewHolder();
            holder.mNameTv = (TextView) convertView
                    .findViewById(R.id.list_customer_user_name_tv);
            holder.mPhoneTv = (TextView) convertView
                    .findViewById(R.id.list_customer_phone_tv);
            holder.mCompanyTv = (TextView) convertView
                    .findViewById(R.id.list_customer_company_tv);
            holder.mBelongTv = (TextView) convertView
                    .findViewById(R.id.list_customer_belong_tv);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.list_customer_select_cb);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mNameTv.setText(!"null".equals(mDatas.get(position).getName()) ? mDatas.get(position).getName() : "");
        holder.mPhoneTv.setText(!"null".equals(mDatas.get(position).getMobile()) ? mDatas.get(position).getMobile() : "");
        holder.mCompanyTv.setText(!"null".equals(mDatas.get(position).getCompanyName()) ? mDatas.get(position).getCompanyName() : "");
        holder.mBelongTv.setText(!"null".equals(mDatas.get(position).getFullName()) ? mDatas.get(position).getFullName() : "");

        final int tempPosition = position;
        final View view = convertView;
        final int whichCb = holder.mCheckBox.getId();
        holder.mCheckBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        getmIsSelected().put(tempPosition, isChecked);
                        mCallback.onClick(view, buttonView, tempPosition, whichCb, isChecked);
                    }
                });
        if (getmIsSelected().size() > position) {
            holder.mCheckBox.setChecked(getmIsSelected().get(position));
        }

        return convertView;
    }

    static class ViewHolder {

        public TextView mNameTv;

        public TextView mPhoneTv;

        public TextView mCompanyTv;

        public TextView mBelongTv;

        public CheckBox mCheckBox;
    }

    public static HashMap<Integer, Boolean> getmIsSelected() {
        return mIsSelected;
    }

    public static void setmIsSelected(HashMap<Integer, Boolean> mIsSelected) {
        CustomerAdapter.mIsSelected = mIsSelected;
    }

}
