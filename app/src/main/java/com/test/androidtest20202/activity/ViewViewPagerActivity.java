package com.test.androidtest20202.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.test.androidtest20202.R;
import com.test.androidtest20202.adapter.VideoViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private VideoViewPagerAdapter videoViewPagerAdapter;
    private List<String> videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_view_pager);
        ButterKnife.bind(this);

        createData();
        videoViewPagerAdapter = new VideoViewPagerAdapter(ViewViewPagerActivity.this,getSupportFragmentManager(),videos);
        viewPager.setAdapter(videoViewPagerAdapter);


    }

    private void createData() {
        videos = new ArrayList<>();
        videos.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        videos.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");



        videos.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");

    }
}