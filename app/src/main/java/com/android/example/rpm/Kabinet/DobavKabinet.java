package com.android.example.rpm.Kabinet;

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

import com.android.example.rpm.DB.Kabinet.KabinetDBContract;
import com.android.example.rpm.DB.Kabinet.KabinetDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.R;

public class DobavKabinet extends AppCompatActivity {

    private EditText editTextNomerKabineta;
    private Spinner spinnerVidKabineta;
    private EditText editTextKolMest;
    private RadioGroup radioGroup;

    private RadioButton radioButton1;
    private RadioButton radioButton2;

    private KabinetDBHelper dbHelper;
    private SQLiteDatabase database;

    private Global global;

    public static int t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobav_kabinet);

        dbHelper = new KabinetDBHelper(this);
        database = dbHelper.getWritableDatabase();

        editTextNomerKabineta = findViewById(R.id.editTextNomerKabineta);
        spinnerVidKabineta = findViewById(R.id.spinnerVidKabineta);
        editTextKolMest = findViewById(R.id.editTextKolMest);
        radioGroup = findViewById(R.id.radioGroup);

        radioButton1=findViewById(R.id.radioButton1);
        radioButton2=findViewById(R.id.radioButton2);

        if (global.FVA == 1) {
            this.setTitle("Редактироавние кабинета");
            Cursor cursor = database.query(KabinetDBContract.KabinetEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry._ID));
                if (global.AD == id) {
                    String nom = cursor.getString(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA));
                    String vid = cursor.getString(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_VID_KABINETA));
                    String kol = cursor.getString(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_KOLICHESTVO_MEST));
                    String nal = cursor.getString(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_NALICHIE_PROEKTORA)).trim();

                    editTextNomerKabineta.setText(nom);

                    ArrayAdapter adapter = (ArrayAdapter) spinnerVidKabineta.getAdapter();
                    int position = adapter.getPosition(vid);
                    spinnerVidKabineta.setSelection(position);
                    editTextKolMest.setText(kol);
                    if(nal.equalsIgnoreCase("Да")) {radioButton1.setChecked(true);}
                    else{radioButton2.setChecked(true);}
                    t = id;
                }
            }
            cursor.close();
            findViewById(R.id.DobavitKabinet).setVisibility(View.GONE);
        }
        else {
            this.setTitle("Добавление кабинета");
            findViewById(R.id.IzmenitKabinet).setVisibility(View.GONE);
        }

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void ZaregisterKabinet(View view) {
        String nom = editTextNomerKabineta.getText().toString().trim();
        String koli = editTextKolMest.getText().toString().trim();
        int kol = Integer.parseInt(koli);
        String vid=spinnerVidKabineta.getSelectedItem().toString();
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonID);
        String nal=radioButton.getText().toString();
        if (isFilled(nom, koli)) {
            int net = 0;
            Cursor cursor = database.query(KabinetDBContract.KabinetEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {//Проверка есть ли такие данные в базе
                String kabnom = cursor.getString(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA));
                if (editTextNomerKabineta.getText().toString().equalsIgnoreCase(kabnom)) {
                    net = 1;
                }
            }
            cursor.close();
            if (net == 0) {
                if (global.FVA == 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA, nom);
                    contentValues.put(KabinetDBContract.KabinetEntry.COLUMN_KOLICHESTVO_MEST, kol);
                    contentValues.put(KabinetDBContract.KabinetEntry.COLUMN_VID_KABINETA, vid);
                    contentValues.put(KabinetDBContract.KabinetEntry.COLUMN_NALICHIE_PROEKTORA,nal);
                    database.insert(KabinetDBContract.KabinetEntry.TABLE_NAME, null, contentValues);

                }
                else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA, nom);
                    contentValues.put(KabinetDBContract.KabinetEntry.COLUMN_KOLICHESTVO_MEST, kol);
                    contentValues.put(KabinetDBContract.KabinetEntry.COLUMN_VID_KABINETA, vid);
                    contentValues.put(KabinetDBContract.KabinetEntry.COLUMN_NALICHIE_PROEKTORA,nal);
                    database.update(KabinetDBContract.KabinetEntry.TABLE_NAME, contentValues, KabinetDBContract.KabinetEntry._ID + "=" + t, null);
                }
                Intent intent = new Intent(this, ProsmotrKabinet.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Данный кабинет уже есть в ТПК", Toast.LENGTH_SHORT).show();}
        }
        else {Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();}
    }

    private boolean isFilled(String nom, String kol) {
        return !nom.isEmpty() && !kol.isEmpty();
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