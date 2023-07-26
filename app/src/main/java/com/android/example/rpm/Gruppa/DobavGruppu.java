package com.android.example.rpm.Gruppa;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.example.rpm.DB.Gruppa.GruppaDBContract;
import com.android.example.rpm.DB.Gruppa.GruppaDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.R;

public class DobavGruppu extends AppCompatActivity {

    private EditText editTextNazvanieGr;
    private EditText editTextKolichestvoGr;

    private RadioButton radioButton1;
    private RadioButton radioButton2;

    private GruppaDBHelper dbHelper;
    private SQLiteDatabase database;

    private Global global;

    public static int t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobav_gruppu);

        dbHelper = new GruppaDBHelper(this);
        database = dbHelper.getWritableDatabase();

        editTextNazvanieGr = findViewById(R.id.editTextNazvanieGr);
        editTextKolichestvoGr = findViewById(R.id.editTextKolichestvoGr);

        if (global.FVA == 1) {
            this.setTitle("Редактироавние группы");
            Cursor cursor = database.query(GruppaDBContract.GruppaEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry._ID));
                if (global.AD == id) {
                    String nazvaniegruppu = cursor.getString(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA));
                    String kolichestvovgruppe = cursor.getString(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_KOLICHESTVO));

                    editTextNazvanieGr.setText(nazvaniegruppu);
                    editTextKolichestvoGr.setText(kolichestvovgruppe);
                    t = id;
                }
            }
            cursor.close();
            findViewById(R.id.DobavitGruppu).setVisibility(View.GONE);
        }
        else {
            this.setTitle("Добавление группы");
            findViewById(R.id.IzmenitGruppu).setVisibility(View.GONE);
        }

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void ZaregisterGruppu(View view) {
        String nazvaniegruppu = editTextNazvanieGr.getText().toString().trim();
        String kolichestvovgruppe = editTextKolichestvoGr.getText().toString().trim();
        if (isFilled(nazvaniegruppu, kolichestvovgruppe)) {
            int net = 0;
            Cursor cursor = database.query(GruppaDBContract.GruppaEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {//Проверка есть ли такие данные в базе
                String nazgr = cursor.getString(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA));
                String kolgr = cursor.getString(cursor.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_KOLICHESTVO));
                if (editTextNazvanieGr.getText().toString().equalsIgnoreCase(nazgr)&&editTextKolichestvoGr.getText().toString().equalsIgnoreCase(kolgr)) {
                    net = 1;
                }
            }
            cursor.close();
            if (net == 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA, nazvaniegruppu);
                contentValues.put(GruppaDBContract.GruppaEntry.COLUMN_KOLICHESTVO, kolichestvovgruppe);
                if (global.FVA == 0) {
                    database.insert(GruppaDBContract.GruppaEntry.TABLE_NAME, null, contentValues);
                }
                else {
                    database.update(GruppaDBContract.GruppaEntry.TABLE_NAME, contentValues, GruppaDBContract.GruppaEntry._ID + "=" + t, null);
                }
                Intent intent = new Intent(this, ProsmotrGruppu.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Данная запись есть", Toast.LENGTH_SHORT).show();}
        }
        else {Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();}
    }

    private boolean isFilled(String nazvaniegruppu, String kolichestvovgruppe) {return !nazvaniegruppu.isEmpty() && !kolichestvovgruppe.isEmpty();}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}