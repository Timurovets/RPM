package com.android.example.rpm.Gruppa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.android.example.rpm.R;

public class ZagolovokGruppu {

    private int id;
    private String nazvaniegruppu;
    private String kolichestvovgruppe;

    public ZagolovokGruppu(int id, String nazvaniegruppu, String kolichestvovgruppe) {
        this.id = id;
        this.nazvaniegruppu = nazvaniegruppu;
        this.kolichestvovgruppe = kolichestvovgruppe;
    }

    public int getId() {return id;}

    public String getNazvaniegruppu() {return nazvaniegruppu;}

    public String getKolichestvovgruppe() {return kolichestvovgruppe;}

    public void setNazvaniegruppu(String nazvaniegruppu) {this.nazvaniegruppu = nazvaniegruppu;}

    public void setKolichestvovgruppe(String kolichestvogruppu) {this.kolichestvovgruppe = kolichestvovgruppe;}
}