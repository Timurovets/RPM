package com.android.example.rpm.PredmetiGruppi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.example.rpm.DB.Gruppa.GruppaDBContract;
import com.android.example.rpm.DB.Gruppa.GruppaDBHelper;
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

public class ProsmotrPredmetiGruppi extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ZagolovokPredmetiGruppi> zagolovokPredmetiGruppis=new ArrayList<>();

    private ZagolovokPredmetiGruppiAdapter adapter;

    SQLiteDatabase database;
    private PredmetiGruppiDBHelper dbHelper;
    SQLiteDatabase database1;
    private GruppaDBHelper dbHelper1;
    SQLiteDatabase database2;
    private PrepodDBHelper dbHelper2;
    SQLiteDatabase database3;
    private PredmetDBHelper dbHelper3;
    SQLiteDatabase database4;
    private PPPDBHelper dbHelper4;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosmotr_predmeti_gruppi);

        dbHelper = new PredmetiGruppiDBHelper(this);
        database = dbHelper.getWritableDatabase();
        dbHelper1 = new GruppaDBHelper(this);
        database1 = dbHelper1.getWritableDatabase();
        dbHelper2 = new PrepodDBHelper(this);
        database2 = dbHelper2.getWritableDatabase();
        dbHelper3 = new PredmetDBHelper(this);
        database3 = dbHelper3.getWritableDatabase();
        dbHelper4 = new PPPDBHelper(this);
        database4 = dbHelper4.getWritableDatabase();

        Cursor cursor1 = database1.query(GruppaDBContract.GruppaEntry.TABLE_NAME,null,null, null, null, null, null);
        String namegr="";
        while (cursor1.moveToNext())
        {
            int idgr = cursor1.getInt(cursor1.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry._ID));
            if(idgr==global.AD){
                namegr = cursor1.getString(cursor1.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA));
            }
        }
        cursor1.close();
        this.setTitle(namegr);

        recyclerView = findViewById(R.id.SpisokGruppa);

        getData();
        adapter = new ZagolovokPredmetiGruppiAdapter(zagolovokPredmetiGruppis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new ZagolovokPredmetiGruppiAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(ProsmotrPredmetiGruppi.this, "Вы нажали один раз", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
                Toast.makeText(ProsmotrPredmetiGruppi.this, "Вы удалили предмет", Toast.LENGTH_SHORT).show();
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#6200ed"));
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void dob(View view) {
        global.SL=global.AD;
        Intent intent = new Intent(ProsmotrPredmetiGruppi.this, DobavPredmetiGruppi.class);
        startActivity(intent);
    }

    public void remove(int position){
        int id=zagolovokPredmetiGruppis.get(position).getId();
        String where = PredmetiGruppiDBContract.PredmetiGruppiEntry._ID+" = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,where,whereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }

    public void getData(){
        zagolovokPredmetiGruppis.clear();//АА!!
        Cursor cursor = database.query(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry._ID));
            int idpredpoprep = cursor.getInt(cursor.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_PPP));
            int idgr = cursor.getInt(cursor.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI));
            int chasi=cursor.getInt(cursor.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_KOLICHESTVO_CHASOV));
            if(idgr==global.AD){
                Cursor cursor4 = database4.query(PPPDBContract.PPPEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                while (cursor4.moveToNext())
                {
                    int idppp = cursor4.getInt(cursor4.getColumnIndexOrThrow(PPPDBContract.PPPEntry._ID));
                    int idpred = cursor4.getInt(cursor4.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET));
                    int idprep = cursor4.getInt(cursor4.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD));
                    if(idppp==idpredpoprep){
                        String fio="";
                        String predme="";
                        Cursor cursor2 = database2.query(PrepodDBContract.PrepodEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                        while (cursor2.moveToNext())
                        {
                            int idprepoda = cursor2.getInt(cursor2.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry._ID));
                            if(idprep==idprepoda){
                                String fam = cursor2.getString(cursor2.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA));
                                String ima = cursor2.getString(cursor2.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_IMA));
                                String otch = cursor2.getString(cursor2.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO));
                                fio=fam+" "+ima.charAt(0)+". "+otch.charAt(0)+".";
                            }
                        }
                        cursor2.close();
                        Cursor cursor3 = database3.query(PredmetDBContract.PredmetEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                        while (cursor3.moveToNext())
                        {
                            int idpredmeta = cursor3.getInt(cursor3.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry._ID));
                            if(idpred==idpredmeta){
                                String name=cursor3.getString(cursor3.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
                                predme=name;
                            }
                        }
                        cursor3.close();
                        String fio_predmet_chas=fio+" "+predme+" "+chasi;
                        zagolovokPredmetiGruppis.add(new ZagolovokPredmetiGruppi(id,fio_predmet_chas));
                    }
                }
                cursor4.close();
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