package com.android.example.rpm.Raspisanie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.example.rpm.R;

public class ZagolovokRaspisanieGrupp extends AppCompatActivity {

    private int id;
    private String polepredmetovraspisanie;

    public ZagolovokRaspisanieGrupp(int id, String polepredmetovraspisanie) {
        this.id = id;
        this.polepredmetovraspisanie = polepredmetovraspisanie;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getPolepredmetovraspisanie() {return polepredmetovraspisanie;}

    public void setPolepredmetovraspisanie(String polepredmetovraspisanie) {this.polepredmetovraspisanie = polepredmetovraspisanie;}
}