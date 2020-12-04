package com.test.androidtest20202.pojo;

import com.test.androidtest20202.constant.DashboardType;

public class MainPojo {
    private String title;
    private String image;
    private DashboardType dashboardType;

    public MainPojo(String title, String image, DashboardType dashboardType) {
        this.title = title;
        this.image = image;
        this.dashboardType = dashboardType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DashboardType getDashboardType() {
        return dashboardType;
    }

    public void setDashboardType(DashboardType dashboardType) {
        this.dashboardType = dashboardType;
    }
}
