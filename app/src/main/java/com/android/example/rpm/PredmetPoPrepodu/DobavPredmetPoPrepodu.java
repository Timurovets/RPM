package com.android.example.rpm.PredmetPoPrepodu;

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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.example.rpm.DB.Kabinet.KabinetDBContract;
import com.android.example.rpm.DB.Kabinet.KabinetDBHelper;
import com.android.example.rpm.DB.Predmet.PredmetDBContract;
import com.android.example.rpm.DB.Predmet.PredmetDBHelper;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBContract;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.Kabinet.ProsmotrKabinet;
import com.android.example.rpm.R;

import java.util.ArrayList;

public class DobavPredmetPoPrepodu extends AppCompatActivity {

    public Spinner spinnerPredmetov;

    private PredmetDBHelper dbHelper1;
    private SQLiteDatabase database1;

    private PPPDBHelper dbHelper;
    private SQLiteDatabase database;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobav_predmet_po_prepodu);
        this.setTitle("Добавление предметов");

        dbHelper1 = new PredmetDBHelper(this);
        database1 = dbHelper1.getWritableDatabase();

        dbHelper = new PPPDBHelper(this);
        database = dbHelper.getWritableDatabase();

        spinnerPredmetov = findViewById(R.id.SpinnerPredmetov);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerPredmetov.setAdapter(adapter);
        // получение данных из базы данных
        Cursor cursor = database1.query(PredmetDBContract.PredmetEntry.TABLE_NAME, null, null, null, null, null, null);
        // добавление данных в адаптер
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
            adapter.add(data);
        }
        cursor.close();
        adapter.notifyDataSetChanged();

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void ZaregisterPredmetPrepody(View view) {
        String predmet=spinnerPredmetov.getSelectedItem().toString();

        int net=0;

        Cursor cursor = database.query(PPPDBContract.PPPEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor.moveToNext())
        {
            int idprep = cursor.getInt(cursor.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD));
            int idpred = cursor.getInt(cursor.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET));
            if(idprep==global.SL){
                String aa;
                Cursor cursor1 = database1.query(PredmetDBContract.PredmetEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                while (cursor1.moveToNext())
                {
                    int idpredmeta = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry._ID));
                    String namepredmeta=cursor1.getString(cursor1.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
                    if(idpredmeta==idpred){
                        aa=namepredmeta;
                        if(aa.trim().equalsIgnoreCase(predmet)){
                            net++;
                        }
                    }
                }
                cursor1.close();
            }
        }
        cursor.close();

        if(net==0){
            int bb=0;
            ContentValues contentValues = new ContentValues();
            Cursor cursor1 = database1.query(PredmetDBContract.PredmetEntry.TABLE_NAME,null,null, null, null, null, null);
            while (cursor1.moveToNext())
            {
                int idpredmeta = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry._ID));
                String namepredmeta=cursor1.getString(cursor1.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
                if(predmet.trim().equalsIgnoreCase(namepredmeta)){
                    bb=idpredmeta;
                }
            }
            cursor1.close();
            contentValues.put(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET, bb);
            contentValues.put(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD, global.SL);
            database.insert(PPPDBContract.PPPEntry.TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, ProsmotrPredmetPoPrepodu.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Данный предмет у препода есть", Toast.LENGTH_SHORT).show();
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