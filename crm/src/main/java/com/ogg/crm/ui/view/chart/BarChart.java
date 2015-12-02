
package com.ogg.crm.ui.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.ogg.crm.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BarChart extends View {

    private Context mContext;

    private double maxcount = 3500;

    private double mincount = 0;

    private int mTotalTickets = 5620;

    private int topPlaceY;

    private int bottomPlaceY;

    private int width;

    private int height;

    private int xoffset;

    private int yoffset;

    private List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

    public BarChart(Context context) {
        super(context);
        this.mContext = context;
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas canvas) {

        initData();

        Paint paint = new Paint();
        paint.setAntiAlias(true);// 设置反锯齿效果,使得图像看起来更平滑

        canvas.drawColor(Color.WHITE);// 设置背景颜色

        drawWorkPlace(canvas, paint);

        drawXYLine(canvas, paint);

        drawTitle(canvas, paint);

        drawTimeData(canvas, paint);

        drawBar(canvas, paint);

        drawBrief(canvas, paint);

    }

    private void initData() {
        mList.clear();
        Map<String, Object> m1 = new HashMap<String, Object>();
        m1.put("count", 500);
        m1.put("eControl", "成人");
        Map<String, Object> m2 = new HashMap<String, Object>();
        m2.put("count", 2000);
        m2.put("eControl", "团购");
        Map<String, Object> m3 = new HashMap<String, Object>();
        m3.put("count", 1500);
        m3.put("eControl", "团购");
        Map<String, Object> m4 = new HashMap<String, Object>();
        m4.put("count", 2000);
        m4.put("eControl", "团购");
        Map<String, Object> m5 = new HashMap<String, Object>();
        m5.put("count", 3000);
        m5.put("eControl", "团购");
        Map<String, Object> m6 = new HashMap<String, Object>();
        m6.put("count", 1000);
        m6.put("eControl", "团购");
        mList.add(m1);
        mList.add(m2);
        mList.add(m3);
        mList.add(m4);
        mList.add(m5);
        mList.add(m6);

        WindowManager wm = (WindowManager) this.getContext().getSystemService(
                Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        // titlePlaceY = (int)
        // mContext.getResources().getDimension(R.dimen.chart_title_height);
        // briefY = (int)
        // mContext.getResources().getDimension(R.dimen.chart_brief_height);
        width = display.getWidth();
        height = display.getHeight();// 留下标题的空间,留下下方工具条
        topPlaceY = (int) mContext.getResources().getDimension(R.dimen.top_place_height);
        bottomPlaceY = (int) mContext.getResources().getDimension(R.dimen.bottom_place_height);
        height = height - topPlaceY - bottomPlaceY;

        xoffset = (int) (width * 0.1);// 设置偏移量，按x,y轴等比例缩放
        yoffset = (int) (height * 0.1);

    }

    /**
     * 绘制工作区
     * 
     * @param canvas
     * @param paint
     */
    private void drawWorkPlace(Canvas canvas, Paint paint) {
        // 绘制工作区
        // mypaint.setColor(Color.YELLOW);// 勾勒边沿
        // mypaint.setStrokeWidth(2);
        // canvas.drawRect(0, 0, width, height, mypaint);

        paint.setColor(Color.WHITE);// 设置背景为白色
        paint.setStrokeWidth(0);
        canvas.drawRect(2, 2, width - 2, height - 2, paint);
    }

    /**
     * setXYLine
     * 
     * @param canvas
     * @param paint
     */
    private void drawXYLine(Canvas canvas, Paint paint) {
        // 绘制x,y轴
        int xoffset = (int) (width * 0.1);// 设置偏移量，按x,y轴等比例缩放
        int yoffset = (int) (height * 0.1);

        paint.setColor(mContext.getResources().getColor(R.color.gray_chart_line));
        paint.setStyle(Style.FILL);

        // 这里面将y轴坐标，画到了80，避免标题显示时，与统计表重合
        canvas.drawLine(2 + xoffset, height - bottomPlaceY - yoffset, 2 + xoffset, topPlaceY,
                paint);
        canvas.drawLine(2 + xoffset, height - bottomPlaceY - yoffset, width - 2, height
                - bottomPlaceY - yoffset, paint);// 画x轴

        drawYNum(canvas, paint);
    }

    /**
     * 设置Y轴刻度
     * 
     * @param canvas
     * @param paint
     */
    private void drawYNum(Canvas canvas, Paint paint) {
        int xoffset = (int) (width * 0.1);// 设置偏移量，按x,y轴等比例缩放
        int yoffset = (int) (height * 0.1);
        // 绘制y轴，将y轴分成7等份
        int yUnit = 7;
        int yUnit_value = (height - bottomPlaceY - yoffset - topPlaceY) / yUnit;// 减去顶部，为标题预留的80个单元

        // 绘制虚线
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(1);
        // PathEffect 是指路径的方式，DashPathEffect是PathEffect的一个子类，
        // 其中的float数组，必须为偶数，且>=2,指定了多少长度的实线，之后再画多少长度的虚线。
        // 程序中，是先绘制长度为1的实线，再绘制长度为3的空白，1是偏移量。
        paint.setPathEffect(new DashPathEffect(new float[] {
                1, 3
        }, 1));

        for (int j = 0; j < 6; j++)// 这个虚线的边界解决
        {
            canvas.drawLine(2 + xoffset, height - bottomPlaceY - yoffset - (yUnit_value * (j + 1)),
                    width - 2, height - bottomPlaceY - yoffset - (yUnit_value * (j + 1)), paint);
        }

        // 在y轴方向上，刻度赋值
        double ymarkers = (maxcount - mincount) / yUnit;// 为了避免查询的最大值，显示到屏幕的顶端,这样的图像显示的比例为4/5

        NumberFormat nf = NumberFormat.getInstance();// 数据格式化
        nf.setMaximumFractionDigits(0);// 保留小数
        nf.setMinimumFractionDigits(0);

        paint.setColor(mContext.getResources().getColor(R.color.gray_chart_line));
        paint.setStyle(Style.STROKE);
        paint.setPathEffect(null);
        paint.setStrokeWidth(0);
        paint.setTextSize(20);

        for (int j = 0; j <= 6; j++) {
            double markers = ymarkers * j;
            canvas.drawText(nf.format(markers), xoffset - 60, height - bottomPlaceY - yoffset
                    - (yUnit_value * (j)), paint);
        }

    }

    /**
     * 设置标题
     * 
     * @param canvas
     * @param paint
     */
    private void drawTitle(Canvas canvas, Paint paint) {
        // 显示标题
        paint.setTextSize(mContext.getResources().getDimension(R.dimen.chart_title_text_size));// 设置画笔的颜色
        paint.setColor(mContext.getResources().getColor(R.color.gray_character));
        canvas.drawText(mContext.getResources().getString(R.string.customer_report_form),
                2 + (int) (width * 0.1) - 20, (0.65f*topPlaceY), paint);
    }

    private void drawTimeData(Canvas canvas, Paint paint) {

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        if (date.length() > 16) {
            date = date.substring(11, 16);
        }
        paint.setTextSize(mContext.getResources().getDimension(R.dimen.chart_content_text_size));
        paint.setColor(mContext.getResources().getColor(R.color.gray_character));
        canvas.drawText(date,
                2 + (int) (width * 0.1), topPlaceY, paint);

//        String nowTicketsStr = mTotalTickets + "("
//                + mContext.getResources().getString(R.string.zhang) + ")";
//        canvas.drawText(nowTicketsStr,
//                width - 150, topPlaceY, paint);
    }

    private void drawBar(Canvas canvas, Paint paint) {
        // 绘制y轴，将y轴分成7等份
        int yUnit = 7;
        int yUnit_value = (height - bottomPlaceY - yoffset - topPlaceY) / yUnit;// 减去顶部，为标题预留的160个单元
        double ymarkers = (maxcount - mincount) / yUnit;// 为了避免查询的最大值，显示到屏幕的顶端,这样的图像显示的比例为4/5
        // 绘制柱状图
        paint.setStyle(Style.FILL);
        paint.setStrokeWidth(2);

        int averageX = (width - 2 - 2 - xoffset) / mList.size();

        for (int j = 0; j < mList.size(); j++) {

            int barWidth = (int) (averageX / 2.5);// 定义统计图中，柱体的宽度。这个可以根据个人喜好自定义，在每一个区间内，只用1/2来显示柱状图
            int startPos = xoffset + 2 + averageX / 4 + averageX * j;// j+1,使得x轴的每一个区间从坐标原点，向后平移一个区间。

            int interval = barWidth / 2;

            Map<String, Object> map = mList.get(j);
            int temp = Integer.parseInt(map.get("count").toString());
            int barHeight = (int) (temp * (yUnit_value / ymarkers));// 这儿存在问题，如果使用的是(temp/ymarkers)*yUnit_value,则每次计算的基数有问题。所以改为temp*(
                                                                    // yUnit_value/ymarkers)

            // 绘制柱状图
            paint = setPaintColor(j, paint);

            canvas.drawRect(startPos, height - bottomPlaceY - yoffset - barHeight, startPos
                    + barWidth, height - bottomPlaceY - yoffset, paint);

            // 绘制数据值
            // paint.setColor(Color.BLUE);
            // canvas.drawText(String.valueOf(temp), startPos, height -
            // xUnit_value - yoffset
            // - barHeight - 4, paint);

            // 绘制x轴上面的横坐标
            // mypaint.setColor(Color.CYAN);
            // String str = map.get("eControl").toString();
            // canvas.drawText(str, startPos - interval, height - 2 - yoffset +
            // 10, mypaint);// x-interval是为了，让x轴上显示的字，居中显示

        }
    }

    private void drawBrief(Canvas canvas, Paint paint) {
        int row;
        if (mList.size() % 2 == 0) {
            row = mList.size() / 2 + 1;// 4列
        } else {
            row = mList.size() / 2 + 1;
        }

        int totalWidth = width - (2 * xoffset);
        int averageWidth = totalWidth / row;
        int barWidth = averageWidth / 4;

        // 绘制小方块
        for (int n = 0; n < mList.size(); n++) {

            int left = (int) (xoffset + averageWidth * n);
            int top = height - 3 * barWidth - 10 - yoffset / 2;
            int right = left + barWidth;
            int bottom = top - barWidth;

            paint = setPaintColor(n, paint);
            if (n > 3) {
                left = (int) (xoffset + averageWidth * (n - 4));
                top = height - 2 * barWidth - yoffset / 2;
                right = left + barWidth;
                bottom = top - barWidth;
            }

            // 绘制一个统计表的小方块
            canvas.drawRect(left, top, right, bottom, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(mContext.getResources().getDimension(R.dimen.chart_content_text_size));
            FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int baseline = top
                    + (bottom - top - fontMetrics.bottom + fontMetrics.top)
                    / 2 - fontMetrics.top;
            switch (n) {
                case 0: {
                    canvas.drawText(mContext.getResources().getString(R.string.adult_ticket),
                            right + 10, baseline, paint);

                    break;
                }
                case 1: {
                    canvas.drawText(mContext.getResources().getString(R.string.buy_ticket),
                            right + 10, baseline, paint);
                    break;
                }
                case 2: {
                    canvas.drawText(mContext.getResources().getString(R.string.children_ticket),
                            right + 10, baseline, paint);
                    break;
                }
                case 3: {
                    canvas.drawText(mContext.getResources().getString(R.string.discount_ticket),
                            right + 10, baseline, paint);

                    break;
                }
                case 4: {
                    canvas.drawText(mContext.getResources().getString(R.string.guest_ticket),
                            right + 10, baseline, paint);
                    break;
                }
                case 5: {
                    canvas.drawText(mContext.getResources().getString(R.string.card),
                            right + 10, baseline, paint);
                    break;
                }
                default:
                    break;
            }
        }
    }

    /**
     * 设置颜色
     * 
     * @param i
     * @param paint
     * @return
     */
    private Paint setPaintColor(int i, Paint paint) {
        switch (i) {
            case 0: {
                paint.setColor(mContext.getResources().getColor(R.color.blue_chart));
                break;
            }
            case 1: {
                paint.setColor(mContext.getResources().getColor(R.color.green_chart));
                break;
            }
            case 2: {
                paint.setColor(mContext.getResources().getColor(R.color.yellow_chart));
                break;
            }

            case 3: {
                paint.setColor(mContext.getResources().getColor(R.color.orange_chart));
                break;
            }
            case 4: {
                paint.setColor(mContext.getResources().getColor(R.color.red_chart));
                break;
            }

            case 5: {
                paint.setColor(mContext.getResources().getColor(R.color.pink_chart));
                break;
            }
            default:
                break;

        }
        return paint;
    }
}
