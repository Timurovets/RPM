package com.android.example.rpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.example.rpm.DB.Prepod.PrepodDBContract;
import com.android.example.rpm.DB.Prepod.PrepodDBHelper;
import com.android.example.rpm.GlavnaiOblast.Glavnai;

public class MainActivity extends AppCompatActivity {

    private EditText Login;
    private EditText Parol;

    private PrepodDBHelper dbHelper;
    private SQLiteDatabase database;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login=findViewById(R.id.Login);
        Parol=findViewById(R.id.Parol);

        dbHelper = new PrepodDBHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    public void Vxod (View view)
    {
        String log=Login.getText().toString();
        String parol=Parol.getText().toString();
        if(isFilled(log,parol))
        {
            int net = 0;
            Cursor cursor = database.query(PrepodDBContract.PrepodEntry.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {//Проверка есть ли такие данные в базе
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry._ID));
                String prlogin = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_LOGIN));
                String premail = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_E_MAIL));
                String prparol = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_PASSWORD));
                if(Login.getText().toString().trim().equalsIgnoreCase(prlogin) && Parol.getText().toString().trim().equalsIgnoreCase(prparol) && !(Parol.getText().toString().trim().equalsIgnoreCase("-")) && !(premail.toString().trim().equalsIgnoreCase("TPK")))
                {
                    net=1;
                    global.FIO=id;
                    Intent intent = new Intent(this, Glavnai.class);
                    startActivity(intent);
                }
                else if(Login.getText().toString().trim().equalsIgnoreCase(prlogin) && Parol.getText().toString().trim().equalsIgnoreCase(prparol) && !(Parol.getText().toString().trim().equalsIgnoreCase("-")) && premail.toString().trim().equalsIgnoreCase("TPK"))
                {
                    net=1;
                    Intent intent = new Intent(this,Adminka.class);
                    startActivity(intent);
                }
                else if(Login.getText().toString().trim().equalsIgnoreCase("sss") && Parol.getText().toString().trim().equalsIgnoreCase("sss"))
                {
                    net=1;
                    Intent intent = new Intent(this,Adminka.class);
                    startActivity(intent);
                }
            }
            cursor.close();
            if(net==0){Toast.makeText(this, "Вы что-то вводите не правильно!!", Toast.LENGTH_LONG).show();}
        }
        else {Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();}
    }

    public void Register (View view)
    {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    private boolean isFilled(String login, String parol) {
        return !login.isEmpty() && !parol.isEmpty();
    }

    public void ppp(View view) {
        Intent intent = new Intent(this,Adminka.class);
        startActivity(intent);
    }
}