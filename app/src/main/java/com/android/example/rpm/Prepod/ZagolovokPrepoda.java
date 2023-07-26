package com.android.example.rpm.Prepod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.example.rpm.R;

public class ZagolovokPrepoda{
    private int id;
    private String fam;
    private String imaotchestvo;
    private String idpolzovatela;

    public ZagolovokPrepoda(int id,String fam, String imaotchestvo, String idpolzovatela) {
        this.id=id;
        this.fam = fam;
        this.imaotchestvo = imaotchestvo;
        this.idpolzovatela=idpolzovatela;
    }

    public int getId(){return id;}

    public String getFam() {
        return fam;
    }

    public String getImaotchestvo() {
        return imaotchestvo;
    }

    public String getIdPolzovatela() {return idpolzovatela;}

    public void setFam(String fam) {
        this.fam = fam;
    }

    public void setImaotchestvo(String imaotchestvo) {
        this.imaotchestvo = imaotchestvo;
    }

    public void setIdPolzovatela(String idpolzovatela) { this.idpolzovatela = idpolzovatela;}
}