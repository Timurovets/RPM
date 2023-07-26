package com.android.example.rpm.PredmetPoPrepodu;

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
import android.widget.Toast;


import com.android.example.rpm.Adminka;
import com.android.example.rpm.DB.Prepod.PrepodDBContract;
import com.android.example.rpm.DB.Prepod.PrepodDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.R;

import java.util.ArrayList;

public class ProsmotrPrepodov extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Zagolovok1> zagolovok1s=new ArrayList<>();

    private Zagolovok1Adapter adapter;
    SQLiteDatabase database;
    private PrepodDBHelper dbHelper;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosmotr_prepodov);
        this.setTitle("Просмотр доступных преподавателей");

        recyclerView = findViewById(R.id.SpisokPrepodov);
        dbHelper = new PrepodDBHelper(this);
        database = dbHelper.getWritableDatabase();
        getData();
        adapter = new Zagolovok1Adapter(zagolovok1s);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new Zagolovok1Adapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(ProsmotrPrepodov.this, "Вы нажали один раз", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                global.AD=zagolovok1s.get(position).getId();
                Intent intent = new Intent(ProsmotrPrepodov.this,ProsmotrPredmetPoPrepodu.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void getData(){
        zagolovok1s.clear();
        Cursor cursor = database.query(PrepodDBContract.PrepodEntry.TABLE_NAME,null,null, null, null, null, PrepodDBContract.PrepodEntry.COLUMN_FAMILIA);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry._ID));
            String fam = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA));
            String ima = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_IMA));
            String otch = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO));
            String prepod = fam+" "+ima.charAt(0)+". "+otch.charAt(0)+".";
            String log= cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_LOGIN));
            if(!log.equalsIgnoreCase("admin")){
                zagolovok1s.add(new Zagolovok1(id,prepod));
            }
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