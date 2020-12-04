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
        title.setText(mainPojo.getTitle());
        Log.d("MainViewHolder",mainPojo.getImage());
        Glide.with(context).load(mainPojo.getImage()).into(image);
        switch (mainPojo.getDashboardType()){
            case VIDEOVIEWPAGER:
                    context.startActivity(new Intent(context, ViewViewPagerActivity.class));
                break;
        }


    }
}
