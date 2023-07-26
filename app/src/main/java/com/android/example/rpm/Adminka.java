package com.android.example.rpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.example.rpm.Gruppa.ProsmotrGruppu;
import com.android.example.rpm.Kabinet.ProsmotrKabinet;
import com.android.example.rpm.Predmet.ProsmotrPredmet;
import com.android.example.rpm.PredmetPoPrepodu.ProsmotrPredmetPoPrepodu;
import com.android.example.rpm.PredmetPoPrepodu.ProsmotrPrepodov;
import com.android.example.rpm.PredmetiGruppi.ProsmotrGrupp;
import com.android.example.rpm.Prepod.ProsmotrPrepoda;
import com.android.example.rpm.Raspisanie.ProsmotrRaspisanieGrupp;

public class Adminka extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminka);
    }

    public void DobavGruppu(View view)
    {
        Intent intent = new Intent(this, ProsmotrGruppu.class);
        startActivity(intent);
    }

    public void DobavKabinet(View view)
    {
        Intent intent = new Intent(this, ProsmotrKabinet.class);
        startActivity(intent);
    }
    public void DobavPredmet(View view)
    {
        Intent intent = new Intent(this, ProsmotrPredmet.class);
        startActivity(intent);
    }
    public void DobavPrepodavatela(View view)
    {
        Intent intent = new Intent(this, ProsmotrPrepoda.class);
        startActivity(intent);
    }

    public void O(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void PredmetPoPrepodu(View view) {
        Intent intent = new Intent(this, ProsmotrPrepodov.class);
        startActivity(intent);
    }

    public void PredmetiGruppi(View view) {
        Intent intent = new Intent(this, ProsmotrGrupp.class);
        startActivity(intent);
    }

    public void Rospisanie(View view) {
        Intent intent = new Intent(this, ProsmotrRaspisanieGrupp.class);
        startActivity(intent);
    }
}
