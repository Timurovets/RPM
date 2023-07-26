package com.android.example.rpm.Predmet;

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
import android.widget.Toast;

import com.android.example.rpm.Adminka;
import com.android.example.rpm.DB.Predmet.PredmetDBContract;
import com.android.example.rpm.DB.Predmet.PredmetDBHelper;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBContract;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.R;

import java.util.ArrayList;

public class ProsmotrPredmet extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ZagolovokPredmet> zagolovokPredmets=new ArrayList<>();

    private ZagolovokPredmetAdapter adapter;
    SQLiteDatabase database;
    private PredmetDBHelper dbHelper;

    SQLiteDatabase database1;
    private PPPDBHelper dbHelper1;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosmotr_predmet);

        recyclerView = findViewById(R.id.SpisokPredmet);

        dbHelper = new PredmetDBHelper(this);
        database = dbHelper.getWritableDatabase();

        dbHelper1 = new PPPDBHelper(this);
        database1= dbHelper1.getWritableDatabase();

        getData();
        adapter = new ZagolovokPredmetAdapter(zagolovokPredmets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new ZagolovokPredmetAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                global.FVA=1;
                global.AD=zagolovokPredmets.get(position).getId();
                Intent intent = new Intent(ProsmotrPredmet.this, DobavPredmet.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
                Toast.makeText(ProsmotrPredmet.this, "Запись удалена", Toast.LENGTH_SHORT).show();
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void dobavka(View view) {
        global.FVA=0;
        Intent intent = new Intent(this, DobavPredmet.class);
        startActivity(intent);
    }

    public void remove(int position){
        int id=zagolovokPredmets.get(position).getId();
        String where = PredmetDBContract.PredmetEntry._ID+" = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(PredmetDBContract.PredmetEntry.TABLE_NAME,where,whereArgs);
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
    }

    public void getData(){
        zagolovokPredmets.clear();
        Cursor cursor = database.query(PredmetDBContract.PredmetEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry._ID));
            String nazvaniepredmet = cursor.getString(cursor.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
            zagolovokPredmets.add(new ZagolovokPredmet(id,nazvaniepredmet));
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