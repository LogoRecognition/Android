package com.example.xumuxin.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by XDDN2 on 2018/5/7.
 */
@ContentView(R.layout.statistic_activity)
public class statistic_activity extends Fragment {
    @ViewInject(R.id.chart)
    private LineChartView lineChart;
    @ViewInject(R.id.start_time)
    private EditText start;
    @ViewInject(R.id.end_time)
    private EditText end;
    @ViewInject(R.id.check_for_more_info)
    private TextView under_line_text;
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private String start_time, end_time;
    private Index index;

    private String[] temp = {"ja", "fe", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        try {
//            initViews(view);
            initViews();
            init();
            Log.e("line chart", "success!");
        } catch (Exception e) {
            Log.e("line chart", "wrong!", e);
        }
        return view;
    }

    private void init() {
        getAxisXLables();
        initLineChart();
        askForData();
    }

    @Event(value = {R.id.start_time, R.id.end_time})
    private void getEvent(View view) {
        Log.e("Click", "for calender");
        switch (view.getId()) {
            case R.id.start_time:
                showDateDialog(1);
                break;
            case R.id.end_time:
                showDateDialog(2);
                break;
        }
    }

    private void showDateDialog(final int number) {
        //获得当前时间 DatePicker默认显示
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //SimpleDateFromat转变表示时间的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //实例化DatePickerDialog对象 并设置时间选择监听
        DatePickerDialog dp = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("time", year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                switch (number) {
                    case 1:
                        //为什么这么设置时间格式? 本人项目服务器要求这么传 哈哈
                        start_time = year + "-" + (monthOfYear + 1);
                        start.setText(start_time);
                        break;
                    case 2:
                        end_time = year + "-" + (monthOfYear + 1);
                        end.setText(end_time);
                        break;
                }
            }
        }, year, month, day);
        //当开始时间已经选则而且是点击结束时间弹出picker
        if (!TextUtils.isEmpty(start_time) && number == 2) {
            try {
                Date date = sdf.parse(start_time);
                //设置最小可选择时间
                dp.getDatePicker().setMinDate(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        //当结束时间已经选择而且是点击开始时间弹出的picker
        if (!TextUtils.isEmpty(end_time) && number == 1) {
            try {
                Date date = sdf.parse(end_time);
                //设置最大可选择时间
                dp.getDatePicker().setMaxDate(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dp.show();
    }

    private void initViews() {
//        lineChart = view.findViewById(R.id.chart);
//        TextView t = view.findViewById(R.id.check_for_more_info);
//        t.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        start = view.findViewById(R.id.start_time);
//        end = view.findViewById(R.id.end_time);
//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getEvent(v);
//            }
//        });
//        end.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getEvent(v);
//            }
//        });
        under_line_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
    private void getAxisXLables() {
        for (int i = 0; i < temp.length; ++i) {
            mAxisXValues.add(new AxisValue(i).setLabel(temp[i]));
        }
    }
    private List<PointValue> getAxisPoints(int[] dataY) {
        List<PointValue> mPointValues = new ArrayList<PointValue>();
        for (int i = 0; i < dataY.length; ++i) {
            mPointValues.add(new PointValue(i, dataY[i]));
        }
        return mPointValues;
    }

    private void initLineChart() {
        List<Line> lines = new ArrayList<Line>();
        int[] d = initDatas();
        lines.add(initLine(getAxisPoints(d)));
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }

    private Line initLine(List<PointValue> mPointValues) {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        List<Line> lines = new ArrayList<>();
        return line;
    }
    private int[] initDatas() {
        int[] dataY;
        dataY = new int[12];
        for (int i = 0; i < dataY.length; ++i) {
            dataY[i] = i + 1;
        }
        return dataY;
    }

    @Event(value = R.id.check_for_more_info)
    private void clickView(View view) {
        Intent intent = new Intent(getActivity(), brand_details.class);
        startActivity(intent);
    }


    private void askForData() {
        String url = "http://private-anon-12c211a2e0-logorecognition.apiary-mock.com/searching_indexes";

        RequestParams requestParams = new RequestParams(url);
        String[] brands = {"Nike", "Apple"};
        requestParams.addBodyParameter("brands", brands, "");
        requestParams.addParameter("month_start", "2017-11-02");
        requestParams.addParameter("month_end", "2018-02-07");
        requestParams.setAsJsonContent(true);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                index = gson.fromJson(result, Index.class);
                Log.e("get index", "Content: " + index.getPos(0)[0]+"...");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
