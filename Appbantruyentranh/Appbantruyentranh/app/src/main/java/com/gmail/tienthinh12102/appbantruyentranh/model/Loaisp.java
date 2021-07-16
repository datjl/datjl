package com.gmail.tienthinh12102.appbantruyentranh.model;

public class Loaisp {
    public int id;
    public  String Tenloaisp;
    public int Hinhanhloaisp;

    public Loaisp(int id, String tenloaisp, int hinhanhloaisp) {
        this.id = id;
        Tenloaisp = tenloaisp;
        Hinhanhloaisp = hinhanhloaisp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaisp() {
        return Tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        Tenloaisp = tenloaisp;
    }

    public int getHinhanhloaisp() {
        return Hinhanhloaisp;
    }

    public void setHinhanhloaisp(int hinhanhloaisp) {
        Hinhanhloaisp = hinhanhloaisp;
    }

}
