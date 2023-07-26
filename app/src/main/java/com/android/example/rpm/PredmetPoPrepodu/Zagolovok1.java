package com.android.example.rpm.PredmetPoPrepodu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.example.rpm.R;

public class Zagolovok1 extends AppCompatActivity {

    private int id;
    private String prepod;

    public Zagolovok1(int id, String prepod) {
        this.id = id;
        this.prepod = prepod;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getPrepod() {return prepod;}

    public void setPrepod(String prepod) {this.prepod = prepod;}
}