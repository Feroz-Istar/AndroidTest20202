package com.test.androidtest20202.viewholder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.androidtest20202.R;
import com.test.androidtest20202.activity.AnimatedBottomBar;
import com.test.androidtest20202.activity.AudioVisualizerActivity;
import com.test.androidtest20202.activity.ChartActivity;
import com.test.androidtest20202.activity.FaceDetectionActivity;
import com.test.androidtest20202.activity.HorizontalProgressBarActivity;
import com.test.androidtest20202.activity.ImageTextRecognizer;
import com.test.androidtest20202.activity.SmartReplyActivity;
import com.test.androidtest20202.activity.TranslatorActivity;
import com.test.androidtest20202.activity.ViewViewPagerActivity;
import com.test.androidtest20202.pojo.MainPojo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    public ImageView image;

    @BindView(R.id.title)
    public TextView title;

    @BindView(R.id.items)
    public ConstraintLayout items;

    Context context;

    public MainViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.context = context;
    }


    public void bind(MainPojo mainPojo){
        title.setText(mainPojo.getDashboardType().name());
        Log.d("MainViewHolder",mainPojo.getImage());
        Glide.with(context).load(mainPojo.getImage()).into(image);
        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mainPojo.getDashboardType()){
                    case VIDEOVIEWPAGER:
                        context.startActivity(new Intent(context, ViewViewPagerActivity.class));
                        break;
                    case ANIMATEDBOTTOMBAR:
                        context.startActivity(new Intent(context, AnimatedBottomBar.class));

                        break;
                    case TRANSLATOR:
                        context.startActivity(new Intent(context, TranslatorActivity.class));
                        break;
                    case SMARTREPLY:
                        context.startActivity(new Intent(context, SmartReplyActivity.class));
                        break;
                    case IMAGETEXTRECOGINITION:
                        context.startActivity(new Intent(context, ImageTextRecognizer.class));
                        break;
                    case FACEDETECTION:
                        context.startActivity(new Intent(context, FaceDetectionActivity.class));
                        break;
                    case CHART:
                        context.startActivity(new Intent(context, ChartActivity.class));
                        break;
                    case AUDIOVISUALIZER:
                        context.startActivity(new Intent(context, AudioVisualizerActivity.class));
                        break;
                    case HORIZONTAL_PROGRESS:
                        context.startActivity(new Intent(context, HorizontalProgressBarActivity.class));
                        break;
                }
            }
        });



    }
}
