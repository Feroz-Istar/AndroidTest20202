package com.test.androidtest20202;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import io.kimo.lib.faker.component.text.URLComponent;

public class MainActivity extends AppCompatActivity {

    private MainRecyclerAdapter mainRecyclerAdapter;
    private List<MainPojo> mainPojos;
    private Faker faker;
    @BindView(R.id.recylerview)
     RecyclerView recyclerView;

    public static String[] PERMISSIONS = { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int APP_UPDATE_REQUEST_CODE = 21986;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPojos = new ArrayList<>();
        faker = Faker.with(MainActivity.this);
        createDataSet();
            requestAllpermission();
        mainRecyclerAdapter = new MainRecyclerAdapter(MainActivity.this,mainPojos);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainRecyclerAdapter);






    }

    private void createDataSet() {
        NameComponent nameComponent = new NameComponent(MainActivity.this);
        URLComponent ic = new URLComponent(MainActivity.this);

        MainPojo mainPojo = new MainPojo(nameComponent.title(),ic.avatar(), DashboardType.VIDEOVIEWPAGER);
        mainPojos.add(mainPojo);


        MainPojo mainPojo1= new MainPojo( DashboardType.ANIMATEDBOTTOMBAR.name(),ic.avatar(), DashboardType.ANIMATEDBOTTOMBAR);
        mainPojos.add(mainPojo1);

        MainPojo mainPojo2= new MainPojo(DashboardType.TRANSLATOR.name(),ic.avatar(), DashboardType.TRANSLATOR);
        mainPojos.add(mainPojo2);


        MainPojo mainPojo3= new MainPojo(DashboardType.SMARTREPLY.name(),ic.avatar(), DashboardType.SMARTREPLY);
        mainPojos.add(mainPojo3);



        MainPojo mainPojo4= new MainPojo(DashboardType.IMAGETEXTRECOGINITION.name(),ic.avatar(), DashboardType.IMAGETEXTRECOGINITION);
        mainPojos.add(mainPojo4);



        MainPojo mainPojo5= new MainPojo(DashboardType.FACEDETECTION.name(),ic.avatar(), DashboardType.FACEDETECTION);
        mainPojos.add(mainPojo5);


        MainPojo mainPojo6= new MainPojo(DashboardType.CHART.name(),ic.avatar(), DashboardType.CHART);
        mainPojos.add(mainPojo6);
    }

    public boolean requestAllpermission() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : PERMISSIONS) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

}