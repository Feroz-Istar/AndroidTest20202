package com.test.androidtest20202.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.test.androidtest20202.fragments.VideoViewFragment;

import java.util.List;

public class VideoViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> videos;
    public VideoViewPagerAdapter(Context context, @NonNull FragmentManager fm,List<String> videos) {
        super(fm);
        this.videos = videos;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (position) {
            default:
                bundle.putString(VideoViewFragment.URL,videos.get(position));
                fragment = new VideoViewFragment();

        }
        fragment.setArguments(bundle);
        return fragment;    }

    @Override
    public int getCount() {
        return videos.size();
    }
}
