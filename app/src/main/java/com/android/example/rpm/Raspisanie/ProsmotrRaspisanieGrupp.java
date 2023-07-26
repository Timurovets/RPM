package com.android.example.rpm.Raspisanie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.example.rpm.Adminka;
import com.android.example.rpm.DB.Gruppa.GruppaDBContract;
import com.android.example.rpm.DB.Gruppa.GruppaDBHelper;
import com.android.example.rpm.Global;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ProsmotrRaspisanieGrupp extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ZagolovokRaspisanieGrupp> zagolovokRaspisanieGrupps=new ArrayList<>();
    private Button nb;

    private ZagolovokRaspisanieGruppAdapter adapter;
    SQLiteDatabase database;
    private GruppaDBHelper dbHelper;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosmotr_raspisanie_grupp);
        this.setTitle("Доступные группы");

        recyclerView = findViewById(R.id.SpisokGruppRaspisanie);
        dbHelper = new GruppaDBHelper(this);
        database = dbHelper.getWritableDatabase();
        getData();
        adapter = new ZagolovokRaspisanieGruppAdapter(zagolovokRaspisanieGrupps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new ZagolovokRaspisanieGruppAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(ProsmotrRaspisanieGrupp.this, "Вы нажали один раз", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                global.AD=zagolovokRaspisanieGrupps.get(position).getId();
                Intent intent = new Intent(ProsmotrRaspisanieGrupp.this, RaspisanieNedeli.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void getData(){
        zagolovokRaspisanieGrupps.clear();
        Cursor cursor = database.query(GruppaDBContract.GruppaEntry.TABLE_NAME,null,null, null, null, null, GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA));
            zagolovokRaspisanieGrupps.add(new ZagolovokRaspisanieGrupp(id,name));
        }
        cursor.close();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}