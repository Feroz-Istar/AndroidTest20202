package com.test.androidtest20202.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.test.androidtest20202.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {


    @BindView(R.id.chart1)
     LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        chart = findViewById(R.id.chart1);

        // background color
        chart.setBackgroundColor(Color.TRANSPARENT);

        // disable description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // set listeners
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawGridBackground(false);
        XAxis xAxis;
        xAxis = chart.getXAxis();
        xAxis.setDrawLabels(false); // no axis labels
        xAxis.setDrawAxisLine(false); // no axis line
        xAxis.setDrawGridLines(false); // no grid lines
        xAxis.disableAxisLineDashedLine();
        xAxis.removeAllLimitLines();
        xAxis.disableGridDashedLine();
        xAxis.setEnabled(false);
        // vertical grid lines
        //xAxis.enableGridDashedLine(10f, 10f, 0f);
        YAxis yAxis;

        yAxis = chart.getAxisLeft();
        yAxis.setEnabled(false);
        // disable dual axis (only use LEFT axis)
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawLabels(false); // no axis labels
        leftAxis.setDrawAxisLine(false); // no axis line
        leftAxis.setDrawGridLines(false); // no grid lines
        leftAxis.disableAxisLineDashedLine();
        leftAxis.removeAllLimitLines();
        leftAxis.disableGridDashedLine();
        leftAxis.setEnabled(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawLabels(false); // no axis labels
        rightAxis.setDrawAxisLine(false); // no axis line
        rightAxis.setDrawGridLines(false); // no grid lines
        rightAxis.disableAxisLineDashedLine();
        rightAxis.removeAllLimitLines();
        rightAxis.disableGridDashedLine();
        rightAxis.setEnabled(false);
        // horizontal grid lines
       // yAxis.enableGridDashedLine(10f, 10f, 0f);

        // axis range
        yAxis.setAxisMaximum(200f);
        yAxis.setAxisMinimum(-50f);


        chart.setViewPortOffsets(23,10,23,10);
        setData(45, 180);

        chart.animateX(1500);
        Legend legend = chart.getLegend();
        legend.setEnabled(false);


        chart.fitScreen();

        //egend l = chart.getLegend();

        // draw legend entries as lines
       // l.setForm(Legend.LegendForm.LINE);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }




    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(10, 20, getResources().getColor(R.color.bg_purple)));
        values.add(new Entry(20, 30, getResources().getColor(R.color.greyishBrown)));
        values.add(new Entry(30, 40, getResources().getColor(R.color.theme)));
        values.add(new Entry(40, 50, getResources().getDrawable(R.drawable.star)));
        values.add(new Entry(50, 60, getResources().getDrawable(R.drawable.star)));
        values.add(new Entry(60, 70, getResources().getDrawable(R.drawable.star)));
        values.add(new Entry(70, 87, getResources().getDrawable(R.drawable.star)));






        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();


            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
           // set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            //set1.enableDashedLine(10f, 5f, 0f);
            set1.setColor(Color.parseColor("#ffa7b5"));


            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area

            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.parseColor("#0000FF"));
            colors.add(Color.parseColor("#ed4d67"));
            colors.add(Color.parseColor("#57b280"));

            //set1.setCircleColor(colors);







            set1.setCircleColors(colors);
            set1.setFillColor(Color.parseColor("#ffa7b5" ));
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);
            // create a data object with the data sets

            // set data
            chart.setData(data);
        }
    }

}