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

import com.android.example.rpm.DB.Predmet.PredmetDBContract;
import com.android.example.rpm.DB.Predmet.PredmetDBHelper;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBContract;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBHelper;
import com.android.example.rpm.DB.PredmetiGruppi.PredmetiGruppiDBContract;
import com.android.example.rpm.DB.PredmetiGruppi.PredmetiGruppiDBHelper;
import com.android.example.rpm.DB.Prepod.PrepodDBContract;
import com.android.example.rpm.DB.Prepod.PrepodDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.R;

import java.util.ArrayList;

public class ProsmotrPredmetPoPrepodu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ZagolovokPredmetPoPrepodu> zagolovokPredmetPoPrepodus=new ArrayList<>();

    private ZagolovokPredmetPoPrepoduAdapter adapter;
    SQLiteDatabase database;
    private PPPDBHelper dbHelper;

    SQLiteDatabase database1;
    private PredmetDBHelper dbHelper1;

    SQLiteDatabase database2;
    private PrepodDBHelper dbHelper2;

    SQLiteDatabase database3;
    private PredmetiGruppiDBHelper dbHelper3;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosmotr_predmet_po_prepodu);
        dbHelper2 = new PrepodDBHelper(this);
        database2 = dbHelper2.getWritableDatabase();

        dbHelper3 = new PredmetiGruppiDBHelper(this);
        database3 = dbHelper3.getWritableDatabase();

        Cursor cursor2 = database2.query(PrepodDBContract.PrepodEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
        String fio="";
        while (cursor2.moveToNext())
        {
            int idprep = cursor2.getInt(cursor2.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry._ID));
            if(idprep==global.AD){
                String fam = cursor2.getString(cursor2.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA));
                String ima = cursor2.getString(cursor2.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_IMA));
                String otch = cursor2.getString(cursor2.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO));
                fio=fam+" "+ima+" "+otch;
            }
        }
        cursor2.close();
        this.setTitle(fio);

        recyclerView = findViewById(R.id.SpisokPredmetovYPrepoda);
        dbHelper = new PPPDBHelper(this);
        database = dbHelper.getWritableDatabase();

        dbHelper1 = new PredmetDBHelper(this);
        database1 = dbHelper1.getWritableDatabase();

        getData();
        adapter = new ZagolovokPredmetPoPrepoduAdapter(zagolovokPredmetPoPrepodus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new ZagolovokPredmetPoPrepoduAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(ProsmotrPredmetPoPrepodu.this, "Вы нажали один раз", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
                Toast.makeText(ProsmotrPredmetPoPrepodu.this, "Вы удалили предмет", Toast.LENGTH_SHORT).show();
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void dobavka1(View view) {
        global.SL=global.AD;
        Intent intent = new Intent(ProsmotrPredmetPoPrepodu.this,DobavPredmetPoPrepodu.class);
        startActivity(intent);
    }

    public void remove(int position){
        int id=zagolovokPredmetPoPrepodus.get(position).getId();
        String where = PPPDBContract.PPPEntry._ID+" = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(PPPDBContract.PPPEntry.TABLE_NAME,where,whereArgs);
        Cursor cursor3 = database3.query(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,null,null, null, null, null, PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI);
        while (cursor3.moveToNext())
        {
            int idppp=cursor3.getInt(cursor3.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry._ID));
            int idpred=cursor3.getInt(cursor3.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI));
            if(idpred==id){
                String where1 = PredmetiGruppiDBContract.PredmetiGruppiEntry._ID+" = ?";
                String[] whereArgs1 = new String[]{Integer.toString(idppp)};
                database1.delete(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,where1,whereArgs1);
            }
        }
        cursor3.close();
        getData();
        adapter.notifyDataSetChanged();
    }

    public void getData(){
        zagolovokPredmetPoPrepodus.clear();
        Cursor cursor = database.query(PPPDBContract.PPPEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(PPPDBContract.PPPEntry._ID));
            int idprep = cursor.getInt(cursor.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD));
            int idpred = cursor.getInt(cursor.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET));
            if(idprep==global.AD){
                Cursor cursor1 = database1.query(PredmetDBContract.PredmetEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                while (cursor1.moveToNext())
                {
                    int idpredmeta = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry._ID));
                    if(idpredmeta==idpred){
                        String nazvaniepredmeta = cursor1.getString(cursor1.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
                        zagolovokPredmetPoPrepodus.add(new ZagolovokPredmetPoPrepodu(id,nazvaniepredmeta));
                    }
                }
                cursor1.close();
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