package com.android.example.rpm.PredmetiGruppi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.example.rpm.R;

public class ZagolovokPredmetiGruppi extends AppCompatActivity {

    private int id;
    private String polepredmetov;

    public ZagolovokPredmetiGruppi(int id, String polepredmetov) {
        this.id = id;
        this.polepredmetov = polepredmetov;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getPolepredmetov() {return polepredmetov;}

    public void setPolepredmetov(String polepredmetov) {this.polepredmetov = polepredmetov;}
}