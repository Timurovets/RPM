package com.android.example.rpm.Prepod;

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
import android.view.Window;
import android.widget.Toast;

import com.android.example.rpm.Adminka;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBContract;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBHelper;
import com.android.example.rpm.DB.Prepod.PrepodDBContract;
import com.android.example.rpm.DB.Prepod.PrepodDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.R;

import java.util.ArrayList;

public class ProsmotrPrepoda extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ZagolovokPrepoda> zagolovokPrepodas=new ArrayList<>();

    private ZagolovokPrepodaAdapter adapter;
    SQLiteDatabase database;
    private PrepodDBHelper dbHelper;

    SQLiteDatabase database1;
    private PPPDBHelper dbHelper1;

    private Global global;

    int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosmotr_prepoda);

        recyclerView = findViewById(R.id.SpisokPrepodov);
        dbHelper = new PrepodDBHelper(this);
        database = dbHelper.getWritableDatabase();

        dbHelper1 = new PPPDBHelper(this);
        database1= dbHelper1.getWritableDatabase();

        getData();
        adapter = new ZagolovokPrepodaAdapter(zagolovokPrepodas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new ZagolovokPrepodaAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                //Toast.makeText(ProsmotrPrepoda.this, "Чтобы удалить предодавателя нужно долго нажимать на него", Toast.LENGTH_SHORT).show();
                global.FVA=1;
                global.AD=zagolovokPrepodas.get(position).getId();
                Intent intent = new Intent(ProsmotrPrepoda.this, DobavPrepoda.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
                if(a==1)
                {
                    Toast.makeText(ProsmotrPrepoda.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void dobavka(View view) {
        global.FVA=0;
        Intent intent = new Intent(this, DobavPrepoda.class);
        startActivity(intent);
    }

    public void remove(int position){
        String prlog=zagolovokPrepodas.get(position).getIdPolzovatela().trim();
        int id=zagolovokPrepodas.get(position).getId();
        if(prlog.equalsIgnoreCase("admin"))
        {
            Toast.makeText(ProsmotrPrepoda.this, "Запись удалить нельзя, так как это учетка админа", Toast.LENGTH_SHORT).show();
            a=0;
        }
        else
        {
            String where = PrepodDBContract.PrepodEntry._ID+" = ?";
            String[] whereArgs = new String[]{Integer.toString(id)};
            database.delete(PrepodDBContract.PrepodEntry.TABLE_NAME,where,whereArgs);
            Cursor cursor1 = database1.query(PPPDBContract.PPPEntry.TABLE_NAME,null,null, null, null, null, PPPDBContract.PPPEntry.COLUMN_ID_PREDMET);
            while (cursor1.moveToNext())
            {
                int idppp=cursor1.getInt(cursor1.getColumnIndexOrThrow(PPPDBContract.PPPEntry._ID));
                int idpred=cursor1.getInt(cursor1.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET));
                if(idpred==id){
                    String where1 = PPPDBContract.PPPEntry._ID+" = ?";
                    String[] whereArgs1 = new String[]{Integer.toString(idppp)};
                    database1.delete(PPPDBContract.PPPEntry.TABLE_NAME,where1,whereArgs1);
                }
            }
            cursor1.close();
            getData();
            adapter.notifyDataSetChanged();
            a=1;
        }
    }

    public void getData(){
        zagolovokPrepodas.clear();
        Cursor cursor = database.query(PrepodDBContract.PrepodEntry.TABLE_NAME,null,null, null, null, null, PrepodDBContract.PrepodEntry.COLUMN_FAMILIA);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry._ID));
            String fam = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA));
            String ima = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_IMA));
            String otch = cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO));
            String log1= cursor.getString(cursor.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_LOGIN));
            String imaotch=ima+" "+otch;
            String log=" "+log1;
            zagolovokPrepodas.add(new ZagolovokPrepoda(id,fam,imaotch,log));
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