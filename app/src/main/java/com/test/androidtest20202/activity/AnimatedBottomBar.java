package com.test.androidtest20202.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bitvale.lightprogress.LightProgress;
import com.test.androidtest20202.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimatedBottomBar extends AppCompatActivity {

    private static final String TAG = "AnimatedBottomBar";

    @BindView(R.id.bottom_bar)
    nl.joery.animatedbottombar.AnimatedBottomBar animatedBottomBar;
    @BindView(R.id.light)
    LightProgress  light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_bottom_bar);
        ButterKnife.bind(this);

        animatedBottomBar.setOnTabSelectListener(new nl.joery.animatedbottombar.AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable nl.joery.animatedbottombar.AnimatedBottomBar.Tab tab, int i1, @NotNull nl.joery.animatedbottombar.AnimatedBottomBar.Tab tab1) {
                Log.d(TAG,tab.getTitle()+" ---- i --- "+i+"  i1 --- "+i1+"   -- "+tab1.getTitle());
            }

            @Override
            public void onTabReselected(int i, @NotNull nl.joery.animatedbottombar.AnimatedBottomBar.Tab tab) {
                Log.d(TAG,tab.getTitle()+" ---- i --- "+i);

            }
        });

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!light.isOn()){
                    light.on();
                }
                else {
                    light.off();
                }            }
        });
    }
}