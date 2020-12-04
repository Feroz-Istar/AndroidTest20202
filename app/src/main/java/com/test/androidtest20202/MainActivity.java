package com.test.androidtest20202;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.androidtest20202.adapter.MainRecyclerAdapter;
import com.test.androidtest20202.constant.DashboardType;
import com.test.androidtest20202.pojo.MainPojo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.kimo.lib.faker.Faker;
import io.kimo.lib.faker.component.text.NameComponent;

public class MainActivity extends AppCompatActivity {

    private MainRecyclerAdapter mainRecyclerAdapter;
    private List<MainPojo> mainPojos;
    private Faker faker;
    @BindView(R.id.recylerview)
     RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPojos = new ArrayList<>();
        faker = Faker.with(MainActivity.this);

        createDataSet();

        mainRecyclerAdapter = new MainRecyclerAdapter(MainActivity.this,mainPojos);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainRecyclerAdapter);






    }

    private void createDataSet() {
        NameComponent nameComponent = new NameComponent(MainActivity.this);
        MainPojo mainPojo = new MainPojo(nameComponent.title(),nameComponent.completeName(), DashboardType.VIDEOVIEWPAGER);
        mainPojos.add(mainPojo);


        MainPojo mainPojo1= new MainPojo(nameComponent.title(),nameComponent.completeName(), DashboardType.ANIMATEDBOTTOMBAR);
        mainPojos.add(mainPojo1);
    }
}