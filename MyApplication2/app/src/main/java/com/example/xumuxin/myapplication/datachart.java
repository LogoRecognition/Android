package com.example.xumuxin.myapplication;

/**
 * Created by XDDN2 on 2018/2/22.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class datachart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_datachart);
        try {
            PieChart mChart = (PieChart) findViewById(R.id.chart1);
            mChart.setUsePercentValues(true);//使用百分比显示

            mChart.setExtraOffsets(5, 10, 5, 5);//设置图表上下左右的偏移，类似于外边距

            mChart.setDragDecelerationFrictionCoef(0.95f);

            Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

            mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));//设置PieChart中间文字的字体


            mChart.setDrawHoleEnabled(true);//是否要将PieChart设为一个圆环状
            mChart.setHoleColor(Color.WHITE);//设置PieChart中间圆的颜色

            mChart.setTransparentCircleColor(Color.WHITE);
            mChart.setTransparentCircleAlpha(110);

            mChart.setHoleRadius(15f);
            mChart.setTransparentCircleRadius(18f);//设置中间圆的半透明圆环

            mChart.setDrawCenterText(true);

            mChart.setRotationAngle(0);//设置图表初始化时第一块数据显示的位置
            // enable rotation of the chart by touch
            mChart.setRotationEnabled(true);
            mChart.setHighlightPerTapEnabled(true);


            float mult = 10;
            String[] mParties=new String[]{"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};

            // IMPORTANT: In a PieChart, no values (Entry) should have the same
            // xIndex (even if from different DataSets), since no values can be
            // drawn above each other.
//            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//            for (int i = 0; i < 12 ; i++) {
//                yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
//            }//为图表的Y轴上添加数据
            ArrayList<String> xVals = new ArrayList<String>();



            // add a lot of colors

            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());

            int[] color = {rgb("#FFCC00"), rgb("#CC99090"), rgb("#663300"), rgb("#FF6600"), rgb("666FF"),
            rgb("#000CC"), rgb("#9933CC"), rgb("#00CC99"), rgb("#99FF99"),
                    rgb("#009966"), rgb("#CC3300"), rgb("#33FF33")};
            //dataSet.setSelectionShift(0f);


            ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
            for (int i = 0; i < 12 ; i++) {
                entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 12),
                        mParties[i % mParties.length]));
            }
            PieDataSet dataSet = new PieDataSet(entries, "Election Results");
            dataSet.setSliceSpace(3f);//设置不同DataSet之间的间距
            dataSet.setSelectionShift(5f);//设置Item被选中时变化的距离
            dataSet.setColors(color);//为DataSet中的数据匹配上颜色
            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());//为Item上的数据添加一个Formatter
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.WHITE);
            data.setValueTypeface(tf);
            mChart.setData(data);

            // undo all highlights
            mChart.highlightValues(null);

            mChart.invalidate();//将图表重绘以显示设置的属性和数据
            // mChart.setUnit(" €");
            // mChart.setDrawUnitsInChart(true);

            // add a selection listener

            //  LineChart mC = (LineChart) findViewById(R.id.chart);

    /* 使用 setData 的方法設定 LineData 物件*/
            // mC.setData( getLineData() );
        } catch (Exception e) {
            Log.e("dataChart", "Wrong!!", e);
        }


    }
    /* 將 DataSet 資料整理好後，回傳 LineData */



}
