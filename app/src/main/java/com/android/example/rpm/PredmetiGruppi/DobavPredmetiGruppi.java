package com.android.example.rpm.PredmetiGruppi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.android.example.rpm.PredmetPoPrepodu.ProsmotrPredmetPoPrepodu;
import com.android.example.rpm.R;

import java.util.ArrayList;

public class DobavPredmetiGruppi extends AppCompatActivity {

    public Spinner spinnerPredmetovPoPrepodu;
    private EditText Chas;

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
        setContentView(R.layout.activity_dobav_predmeti_gruppi);
        this.setTitle("Добавление предметов");

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

        Chas=findViewById(R.id.editTextChas);
        spinnerPredmetovPoPrepodu=findViewById(R.id.SpinnerPredmetovPoPrepodu);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerPredmetovPoPrepodu.setAdapter(adapter);
        // получение данных из базы данных

        Cursor cursor4 = database4.query(PPPDBContract.PPPEntry.TABLE_NAME,null,null, null, null, null, PPPDBContract.PPPEntry.COLUMN_ID_PREDMET);
        while (cursor4.moveToNext())
        {
            int idppp = cursor4.getInt(cursor4.getColumnIndexOrThrow(PPPDBContract.PPPEntry._ID));
            int idpred = cursor4.getInt(cursor4.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET));
            int idprep = cursor4.getInt(cursor4.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD));
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
            String data = fio+" "+predme;
            adapter.add(data);
        }
        cursor4.close();
        adapter.notifyDataSetChanged();

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void ZaregisterPredmetPrepody(View view) {
        String predmet=spinnerPredmetovPoPrepodu.getSelectedItem().toString();
        String chas=Chas.getText().toString().trim();
        int net=0;

        Cursor cursor = database.query(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor.moveToNext())
        {
            int idpredpoprep = cursor.getInt(cursor.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_PPP));
            int idgr = cursor.getInt(cursor.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI));
            if(idgr==global.SL){
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
                        String fio_predmet=fio+" "+predme;
                        if(fio_predmet.trim().equalsIgnoreCase(predmet)){
                            net++;
                        }

                    }
                }
                cursor4.close();
            }
        }
        cursor.close();

        if(net==0){
            int bb=0;
            ContentValues contentValues = new ContentValues();

            Cursor cursor4 = database4.query(PPPDBContract.PPPEntry.TABLE_NAME,null,null, null, null, null, PPPDBContract.PPPEntry.COLUMN_ID_PREDMET);
            while (cursor4.moveToNext())
            {
                int idppp = cursor4.getInt(cursor4.getColumnIndexOrThrow(PPPDBContract.PPPEntry._ID));
                int idpred = cursor4.getInt(cursor4.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET));
                int idprep = cursor4.getInt(cursor4.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD));
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
                String data = fio+" "+predme;
                if(data.equalsIgnoreCase(predmet)){
                    bb=idppp;
                }
            }
            cursor4.close();

            contentValues.put(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_KOLICHESTVO_CHASOV, chas);
            contentValues.put(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI, global.SL);
            contentValues.put(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_PPP, bb);
            database.insert(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, ProsmotrPredmetiGruppi.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Данный предмет у группы есть", Toast.LENGTH_SHORT).show();
        }
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