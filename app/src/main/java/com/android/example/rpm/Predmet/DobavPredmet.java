package com.android.example.rpm.Predmet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.example.rpm.DB.Predmet.PredmetDBContract;
import com.android.example.rpm.DB.Predmet.PredmetDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.R;

public class DobavPredmet extends AppCompatActivity {

    private EditText editTextNazvaniePredmet;

    private PredmetDBHelper dbHelper;
    private SQLiteDatabase database;

    private Global global;

    public static int t;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobav_predmet);
        dbHelper = new PredmetDBHelper(this);
        database = dbHelper.getWritableDatabase();

        editTextNazvaniePredmet=findViewById(R.id.editTextNazvaniePredmeta);
        if (global.FVA == 1) {
            this.setTitle("Редактироавние предмета");
            Cursor cursor = database.query(PredmetDBContract.PredmetEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry._ID));
                if (global.AD == id) {
                    String nazvaniepredmet = cursor.getString(cursor.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));

                    editTextNazvaniePredmet.setText(nazvaniepredmet);
                    t = id;
                }
            }
            cursor.close();
            findViewById(R.id.DobavitPredmet).setVisibility(View.GONE);
        }
        else {
            this.setTitle("Добавление предмета");
            findViewById(R.id.IzmenitPredmet).setVisibility(View.GONE);
        }

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void ZaregisterPredmet(View view) {
        String nazvaniepredmet = editTextNazvaniePredmet.getText().toString().trim();
        if (isFilled(nazvaniepredmet)) {
            int net = 0;
            Cursor cursor = database.query(PredmetDBContract.PredmetEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {//Проверка есть ли такие данные в базе
                String nazpr = cursor.getString(cursor.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
                if (editTextNazvaniePredmet.getText().toString().equalsIgnoreCase(nazpr)) {
                    net = 1;
                }
            }
            cursor.close();
            if (net == 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA, nazvaniepredmet);
                if (global.FVA == 0) {
                    database.insert(PredmetDBContract.PredmetEntry.TABLE_NAME, null, contentValues);
                }
                else {
                    database.update(PredmetDBContract.PredmetEntry.TABLE_NAME, contentValues, PredmetDBContract.PredmetEntry._ID + "=" + t, null);
                }
                Intent intent = new Intent(this, ProsmotrPredmet.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Данный предмет уже есть в ТПК", Toast.LENGTH_SHORT).show();}
        }
        else {Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();}
    }

    private boolean isFilled(String nazvaniepredmet)
    {return !nazvaniepredmet.isEmpty();}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}