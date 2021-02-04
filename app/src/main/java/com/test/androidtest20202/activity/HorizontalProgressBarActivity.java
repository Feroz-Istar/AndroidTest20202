package com.test.androidtest20202.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.test.androidtest20202.R;
import com.test.androidtest20202.util.CustomProgressBar;
import com.test.androidtest20202.util.ProgressItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalProgressBarActivity extends AppCompatActivity {


    @BindView(R.id.textView3)
    CustomProgressBar seekbar;
    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_progress_bar);
        ButterKnife.bind(this);
        seekbar.getThumb().mutate().setAlpha(0);
        progressItemList = new ArrayList<ProgressItem>();
        // red span

        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 70;
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");


        mProgressItem.color = R.color.theme_color;

        progressItemList.add(mProgressItem);


        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 20;
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");


        mProgressItem.color = Color.parseColor("#000000");

        progressItemList.add(mProgressItem);


        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 10;
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");


        mProgressItem.color = R.color.av_dark_blue;

        progressItemList.add(mProgressItem);

        seekbar.initData(progressItemList);
        seekbar.invalidate();
    }
}