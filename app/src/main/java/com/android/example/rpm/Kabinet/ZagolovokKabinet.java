package com.android.example.rpm.Kabinet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.example.rpm.R;

public class ZagolovokKabinet{

    private int id;
    private String nomer;
    private String vid;

    public ZagolovokKabinet(int id, String nomer, String vid) {
        this.id = id;
        this.nomer = nomer;
        this.vid = vid;
    }

    public int getId() {return id;}

    public String getNomer() {return nomer;}

    public String getVid() {return vid;}

    public void setNomer(String nomer) {this.nomer = nomer;}

    public void setVid(String vid) {this.vid = vid;}
}