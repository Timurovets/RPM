package com.android.example.rpm.Gruppa;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.example.rpm.Adminka;
import com.android.example.rpm.DB.Gruppa.GruppaDBContract;
import com.android.example.rpm.DB.Gruppa.GruppaDBHelper;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBContract;
import com.android.example.rpm.DB.PredmetiGruppi.PredmetiGruppiDBContract;
import com.android.example.rpm.DB.PredmetiGruppi.PredmetiGruppiDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.R;

import java.util.ArrayList;

public class ProsmotrGruppu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ZagolovokGruppu> zagolovokGruppus=new ArrayList<>();

    private ZagolovokGruppuAdapter adapter;
    SQLiteDatabase database;
    private GruppaDBHelper dbHelper;

    SQLiteDatabase database1;
    private PredmetiGruppiDBHelper dbHelper1;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosmotr_gruppu);
        recyclerView = findViewById(R.id.SpisokGruppu);
        dbHelper = new GruppaDBHelper(this);
        database = dbHelper.getWritableDatabase();

        dbHelper1 = new PredmetiGruppiDBHelper(this);
        database1 = dbHelper1.getWritableDatabase();

        getData();
        adapter = new ZagolovokGruppuAdapter(zagolovokGruppus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new ZagolovokGruppuAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                global.FVA=1;
                global.AD=zagolovokGruppus.get(position).getId();
                Intent intent = new Intent(ProsmotrGruppu.this, DobavGruppu.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
                Toast.makeText(ProsmotrGruppu.this, "Запись удалена", Toast.LENGTH_SHORT).show();
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void dobavka(View view) {
        global.FVA=0;
        Intent intent = new Intent(this, DobavGruppu.class);
        startActivity(intent);
    }

    public void remove(int position){
        int id=zagolovokGruppus.get(position).getId();
        String where = GruppaDBContract.GruppaEntry._ID+" = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(GruppaDBContract.GruppaEntry.TABLE_NAME,where,whereArgs);

        Cursor cursor1 = database1.query(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,null,null, null, null, null, PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI);
        while (cursor1.moveToNext())
        {
            int idppp=cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry._ID));
            int idpred=cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI));
            if(idpred==id){
                String where1 = PredmetiGruppiDBContract.PredmetiGruppiEntry._ID+" = ?";
                String[] whereArgs1 = new String[]{Integer.toString(idppp)};
                database1.delete(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,where1,whereArgs1);
            }
        }
        cursor1.close();
        getData();
        adapter.notifyDataSetChanged();
    }

    public void getData(){
        zagolovokGruppus.clear();
        Cursor cursor = database.query(GruppaDBContract.GruppaEntry.TABLE_NAME,null,null, null, null, null, GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry._ID));
            String nazvaniegruppu = cursor.getString(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA));
            String kolichestvovgruppe1 = cursor.getString(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_KOLICHESTVO));
            String kolichestvovgruppe=" "+kolichestvovgruppe1;
            zagolovokGruppus.add(new ZagolovokGruppu(id,nazvaniegruppu,kolichestvovgruppe));
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