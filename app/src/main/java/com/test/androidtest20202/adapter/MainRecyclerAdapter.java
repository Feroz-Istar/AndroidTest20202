package com.test.androidtest20202.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.androidtest20202.R;
import com.test.androidtest20202.pojo.MainPojo;
import com.test.androidtest20202.viewholder.MainViewHolder;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context context;
    private List<MainPojo> mainPojos;

    public MainRecyclerAdapter(Context context, List<MainPojo> mainPojos) {
        this.context = context;
        this.mainPojos = mainPojos;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.main_item, parent, false);
        MainViewHolder viewHolder = new MainViewHolder(listItem,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        final MainPojo mainPojo =mainPojos.get(position);
        holder.bind(mainPojo);
    }

    @Override
    public int getItemCount() {
        return mainPojos.size();
    }
}
