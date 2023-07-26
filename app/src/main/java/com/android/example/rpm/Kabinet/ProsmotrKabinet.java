package com.android.example.rpm.Kabinet;

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
import com.android.example.rpm.DB.Kabinet.KabinetDBContract;
import com.android.example.rpm.DB.Kabinet.KabinetDBHelper;
import com.android.example.rpm.Global;

import com.android.example.rpm.R;

import java.util.ArrayList;

public class ProsmotrKabinet extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ZagolovokKabinet> zagolovokKabinets=new ArrayList<>();

    private ZagolovokKabinetAdapter adapter;
    SQLiteDatabase database;
    private KabinetDBHelper dbHelper;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosmotr_kabinet);
        recyclerView = findViewById(R.id.SpisokKabineta);
        dbHelper = new KabinetDBHelper(this);
        database = dbHelper.getWritableDatabase();
        getData();
        adapter = new ZagolovokKabinetAdapter(zagolovokKabinets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new ZagolovokKabinetAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                global.FVA=1;
                global.AD=zagolovokKabinets.get(position).getId();
                Intent intent = new Intent(ProsmotrKabinet.this, DobavKabinet.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
                Toast.makeText(ProsmotrKabinet.this, "Запись удалена", Toast.LENGTH_SHORT).show();
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void dobavka(View view) {
        global.FVA=0;
        Intent intent = new Intent(this, DobavKabinet.class);
        startActivity(intent);
    }

    public void remove(int position){
        int id=zagolovokKabinets.get(position).getId();
        String where = KabinetDBContract.KabinetEntry._ID+" = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(KabinetDBContract.KabinetEntry.TABLE_NAME,where,whereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }

    public void getData(){
        zagolovokKabinets.clear();
        Cursor cursor = database.query(KabinetDBContract.KabinetEntry.TABLE_NAME,null,null, null, null, null, KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry._ID));
            String nomer = cursor.getString(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA));
            String vid1 = cursor.getString(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_VID_KABINETA));
            String vid=" "+vid1;
            zagolovokKabinets.add(new ZagolovokKabinet(id,nomer,vid));
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