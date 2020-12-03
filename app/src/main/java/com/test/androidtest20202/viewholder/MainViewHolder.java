package com.test.androidtest20202.viewholder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.androidtest20202.R;
import com.test.androidtest20202.pojo.MainPojo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    public ImageView image;

    @BindView(R.id.title)
    public TextView title;
    @BindView(R.id.subtitle)
    public TextView subtitle;

    Context context;

    public MainViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.context = context;
    }


    public void bind(MainPojo mainPojo){
        title.setText(mainPojo.getTitle());
        subtitle.setText(mainPojo.getSubtitle());
        Log.d("MainViewHolder",mainPojo.getImage());
        Glide.with(context).load(mainPojo.getImage()).into(image);

    }
}