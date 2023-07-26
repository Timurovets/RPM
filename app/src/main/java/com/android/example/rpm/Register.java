package com.android.example.rpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.example.rpm.DB.Prepod.PrepodDBContract;
import com.android.example.rpm.DB.Prepod.PrepodDBHelper;

public class Register extends AppCompatActivity {

    private EditText Login;
    private EditText Email;
    private EditText VvodParol;
    private EditText PovtorParol;

    private PrepodDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Login=findViewById(R.id.Login);
        Email=findViewById(R.id.Email);
        VvodParol=findViewById(R.id.VvodParol);
        PovtorParol=findViewById(R.id.PovtorParol);

        dbHelper = new PrepodDBHelper(this);
        database = dbHelper.getWritableDatabase();
    }
    public void Obratno(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Zaregister (View view)
    {
        String login = Login.getText().toString();
        String email = Email.getText().toString();
        String parol = VvodParol.getText().toString();
        String povparol = PovtorParol.getText().toString();
        int t=0;
        if(isFilled(login,email,parol,povparol))
        {
            if(VvodParol.getText().length() != 0 && VvodParol.getText().toString().equalsIgnoreCase(PovtorParol.getText().toString()))
            {
                int net = 0;
                Cursor cursor = database.query(PrepodDBContract.PrepodEntry.TABLE_NAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {//Проверка есть ли такие данные в базе
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry._ID));
                    String prlogin = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_LOGIN));
                    String premail = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_E_MAIL));
                    String prparol = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_PASSWORD));
                    if (Login.getText().toString().equalsIgnoreCase(prlogin) && premail.toString().equalsIgnoreCase("-") && prparol.toString().equalsIgnoreCase("-")) {
                        net = 1;
                        t=id;
                    }
                }
                cursor.close();
                if(net==1)
                {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_PASSWORD,parol);
                    contentValues.put(PrepodDBContract.PrepodEntry.COLUMN_E_MAIL,email);
                    database.update(PrepodDBContract.PrepodEntry.TABLE_NAME, contentValues, PrepodDBContract.PrepodEntry._ID + "=" + t, null);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else {Toast.makeText(this, "Вы уже зарегистрированны или вы указали неправильный логин", Toast.LENGTH_SHORT).show();}
            }
            else {Toast.makeText(this, "Пароли не сходятся", Toast.LENGTH_LONG).show();}
        }
        else {Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();}

    }

    private boolean isFilled(String login, String email, String parol, String povparol) {
        return !login.isEmpty() && !email.isEmpty() && !parol.isEmpty() && !povparol.isEmpty();
    }
}