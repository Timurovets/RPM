package com.android.example.rpm.GlavnaiOblast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.example.rpm.DB.Gruppa.GruppaDBContract;
import com.android.example.rpm.DB.Gruppa.GruppaDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.MainActivity;
import com.android.example.rpm.R;

import java.util.ArrayList;

public class GlavnaiProsmotrGrupp extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ZagolovokGlavnaiGruppi> zagolovokGlavnaiGruppis=new ArrayList<>();

    private ZagolovokGlavnaiGruppiAdapter adapter;
    SQLiteDatabase database;
    private GruppaDBHelper dbHelper;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavnai_prosmotr_grupp);

        this.setTitle("Доступные группы");

        recyclerView = findViewById(R.id.SpisokGruppRaspisanie);
        dbHelper = new GruppaDBHelper(this);
        database = dbHelper.getWritableDatabase();
        getData();
        adapter = new ZagolovokGlavnaiGruppiAdapter(zagolovokGlavnaiGruppis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new ZagolovokGlavnaiGruppiAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {

            }

            @Override
            public void onLongClick(int position) {
                global.AD=zagolovokGlavnaiGruppis.get(position).getId();
                Intent intent = new Intent(GlavnaiProsmotrGrupp.this, GlavnaiGruppa.class);
                startActivity(intent);
            }
        });
    }
    public void getData(){
        zagolovokGlavnaiGruppis.clear();
        Cursor cursor = database.query(GruppaDBContract.GruppaEntry.TABLE_NAME,null,null, null, null, null, GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA));
            zagolovokGlavnaiGruppis.add(new ZagolovokGlavnaiGruppi(id,name));
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mena,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_nastroiki){
            Intent openSettings = new Intent(this, Nastroiki.class);
            startActivity(openSettings);
            return  true;
        }
        else if(id==R.id.action_glavnai){
            Intent openAbout = new Intent(this, Glavnai.class);
            startActivity(openAbout);
            return  true;
        }
        else if(id==R.id.action_glavnai_gruppa){
            Intent openAbout = new Intent(this, GlavnaiProsmotrGrupp.class);
            startActivity(openAbout);
            return  true;
        }
        else if(id==R.id.action_glavnai_kabinet){
            Intent openAbout = new Intent(this, GlavnaiProsmotrKabineta.class);
            startActivity(openAbout);
            return  true;
        }
        else if(id==R.id.action_vixod){
            Intent openAbout = new Intent(this, MainActivity.class);
            startActivity(openAbout);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}