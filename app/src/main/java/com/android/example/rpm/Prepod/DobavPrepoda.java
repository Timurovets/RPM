package com.android.example.rpm.Prepod;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.rpm.DB.Prepod.PrepodDBContract;
import com.android.example.rpm.DB.Prepod.PrepodDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.R;

public class DobavPrepoda extends AppCompatActivity {

    private EditText editTextFam;
    private EditText editTextIma;
    private EditText editTextOtch;
    private EditText editTextLogin;
    private TextView textViewParol;
    private TextView textViewEmail;
    private Button button;

    private PrepodDBHelper dbHelper;
    private SQLiteDatabase database;

    private Global global;

    public static int t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobav_prepoda);

        dbHelper = new PrepodDBHelper(this);
        database = dbHelper.getWritableDatabase();

        editTextFam = findViewById(R.id.editTextFam);
        editTextIma = findViewById(R.id.editTextIma);
        editTextOtch = findViewById(R.id.editTextOtch);
        editTextLogin = findViewById(R.id.editTextLogin);
        textViewParol = findViewById(R.id.textViewParol);
        textViewEmail = findViewById(R.id.textViewEmail);

        if (global.FVA == 1) {
            this.setTitle("Редактироавние преподавателя");
            Cursor cursor = database.query(PrepodDBContract.PrepodEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry._ID));
                if (global.AD == id) {
                    String prfam = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA));
                    String prima = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_IMA));
                    String protch = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO));
                    String prpar = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_PASSWORD));
                    String prlog = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_LOGIN));
                    String prem = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_E_MAIL));
                    editTextFam.setText(prfam);
                    editTextIma.setText(prima);
                    editTextOtch.setText(protch);
                    editTextLogin.setText(prlog);
                    textViewParol.setText(prpar);
                    textViewEmail.setText(prem);
                    if(prlog.trim().equalsIgnoreCase("admin"))
                    {
                        findViewById(R.id.Izmenit).setVisibility(View.GONE);
                        editTextLogin.setEnabled(false);
                        editTextIma.setEnabled(false);
                        editTextOtch.setEnabled(false);
                        editTextFam.setEnabled(false);
                    }
                    t = id;
                }
            }
            cursor.close();
            findViewById(R.id.DobavitPrepoda).setVisibility(View.GONE);
        }
        else {
            this.setTitle("Добавление преподавателя");
            findViewById(R.id.Izmenit).setVisibility(View.GONE);
            findViewById(R.id.Obnulit).setVisibility(View.GONE);
            findViewById(R.id.textViewParol).setVisibility(View.GONE);
            findViewById(R.id.textViewEmail).setVisibility(View.GONE);
        }

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void ZaregisterPrepod(View view) {
        String fam = editTextFam.getText().toString().trim();
        //String fam = fam1.replaceAll(" ","");
        String ima = editTextIma.getText().toString().trim();
        //String ima = ima1.replaceAll(" ","");
        String otch = editTextOtch.getText().toString().trim();
        //String otch = otch1.replaceAll(" ","");
        String login = editTextLogin.getText().toString().trim();

        if (isFilled(fam, ima, otch, login)) {
            int net = 0;
            Cursor cursor = database.query(PrepodDBContract.PrepodEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {//Проверка есть ли такие данные в базе
                String prfam = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA));
                String prima = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_IMA));
                String protch = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO));
                String prlogin = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_LOGIN));
                if (editTextFam.getText().toString().equalsIgnoreCase(prfam) && editTextIma.getText().toString().equalsIgnoreCase(prima) && editTextOtch.getText().toString().equalsIgnoreCase(protch) && editTextLogin.getText().toString().equalsIgnoreCase(prlogin)) {
                    net = 1;
                }
            }
            cursor.close();
            if (net == 0) {
                if (global.FVA == 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA, fam);
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_IMA, ima);
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO, otch);
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_LOGIN,login);
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_PASSWORD,"-");
                    contentValues.put(PrepodDBContract.PrepodEntry. COLUMN_E_MAIL,"-");
                    database.insert(PrepodDBContract.PrepodEntry.TABLE_NAME, null, contentValues);

                }
                else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA, fam);
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_IMA, ima);
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO, otch);
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_LOGIN,login);
                    database.update(PrepodDBContract.PrepodEntry.TABLE_NAME, contentValues, PrepodDBContract.PrepodEntry._ID + "=" + t, null);
                }
                Intent intent = new Intent(this, ProsmotrPrepoda.class);
                startActivity(intent);
            }
            else {Toast.makeText(this, "Данный преподователь уже есть в ТПК", Toast.LENGTH_SHORT).show();}
        }
        else {Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();}
    }


    private boolean isFilled(String fam, String ima, String otch, String login) {
        return !fam.isEmpty() && !ima.isEmpty() && !otch.isEmpty() && !login.isEmpty();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void Obnulit(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_PASSWORD, "-");
        contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_E_MAIL,"-");
        database.update(PrepodDBContract.PrepodEntry.TABLE_NAME, contentValues, PrepodDBContract.PrepodEntry._ID + "=" + t, null);
        Intent intent = new Intent(this, ProsmotrPrepoda.class);
        startActivity(intent);
    }
}