package com.android.example.rpm.PredmetPoPrepodu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.example.rpm.R;

public class ZagolovokPredmetPoPrepodu extends AppCompatActivity {

    private int id;
    private  String predmeti_prepoda;

    public ZagolovokPredmetPoPrepodu(int id, String predmeti_prepoda) {
        this.id = id;
        this.predmeti_prepoda = predmeti_prepoda;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getPredmeti_prepoda() {return predmeti_prepoda;}

    public void setPredmeti_prepoda(String predmeti_prepoda) {this.predmeti_prepoda = predmeti_prepoda;}
}