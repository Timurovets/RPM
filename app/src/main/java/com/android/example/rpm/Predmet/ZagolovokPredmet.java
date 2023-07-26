package com.android.example.rpm.Predmet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.example.rpm.R;

public class ZagolovokPredmet extends AppCompatActivity {

    private int id;
    private String nazvaniepredmet;

    public ZagolovokPredmet(int id, String nazvaniepredmet) {
        this.id = id;
        this.nazvaniepredmet = nazvaniepredmet;
    }

    public int getId() {return id;}

    public String getNazvaniepredmet() {return nazvaniepredmet;}

    public void setId(int id) {this.id = id;}

    public void setNazvaniepredmet(String nazvaniepredmet) {this.nazvaniepredmet = nazvaniepredmet;}
}