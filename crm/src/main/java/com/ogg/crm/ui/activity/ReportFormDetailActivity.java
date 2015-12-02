package com.ogg.crm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ogg.crm.R;
import com.ogg.crm.ui.view.CustomProgressDialog;
import com.ogg.crm.ui.view.chart.BarChart;
import com.ogg.crm.utils.ActivitiyInfoManager;

public class ReportFormDetailActivity extends Activity implements OnClickListener {

    private Context mContext;

    private ImageView mBackIv;

    private LinearLayout mChartLl; // 用于绑定图表控件。

    private int mNowChart = 0;

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_form_detail);
        mContext = ReportFormDetailActivity.this;
        mProgressDialog = new CustomProgressDialog(mContext);
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
        mBackIv = (ImageView) findViewById(R.id.report_form_detail_back_iv);
        mBackIv.setOnClickListener(this);

        mChartLl = (LinearLayout) findViewById(R.id.report_form_detail_chart_show_ll);
    }

    private void initData() {
        mProgressDialog.show();
        initChart();
    }

    private void initChart() {
        mChartLl.addView(new BarChart(ReportFormDetailActivity.this));
        mNowChart = 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_form_detail_back_iv: {
                finish();
                break;
            }

            default: {
                break;
            }
        }
    }
}
