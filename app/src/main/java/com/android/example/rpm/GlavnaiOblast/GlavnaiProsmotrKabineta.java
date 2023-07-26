package com.android.example.rpm.GlavnaiOblast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.example.rpm.DB.Gruppa.GruppaDBHelper;
import com.android.example.rpm.DB.Kabinet.KabinetDBContract;
import com.android.example.rpm.DB.Kabinet.KabinetDBHelper;
import com.android.example.rpm.DB.Predmet.PredmetDBHelper;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBHelper;
import com.android.example.rpm.DB.PredmetiGruppi.PredmetiGruppiDBHelper;
import com.android.example.rpm.DB.Prepod.PrepodDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.Kabinet.ZagolovokKabinet;
import com.android.example.rpm.MainActivity;
import com.android.example.rpm.PredmetiGruppi.ZagolovokPredmetiGruppi;
import com.android.example.rpm.PredmetiGruppi.ZagolovokPredmetiGruppiAdapter;
import com.android.example.rpm.R;

import java.util.ArrayList;

public class GlavnaiProsmotrKabineta extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ZagolovokGlavnaiKabinet> zagolovokGlavnaiKabinets=new ArrayList<>();

    private ZagolovokGlavnaiKabinetAdapter adapter;

    SQLiteDatabase database;
    private KabinetDBHelper dbHelper;

    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavnai_prosmotr_kabineta);

        this.setTitle("Доступные кабинеты");

        recyclerView = findViewById(R.id.SpisokGruppRaspisanie);
        dbHelper = new KabinetDBHelper(this);
        database = dbHelper.getWritableDatabase();
        getData();
        adapter = new ZagolovokGlavnaiKabinetAdapter(zagolovokGlavnaiKabinets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnNoteClickListener(new ZagolovokGlavnaiKabinetAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {

            }

            @Override
            public void onLongClick(int position) {
                global.DD=zagolovokGlavnaiKabinets.get(position).getId();
                Intent intent = new Intent(GlavnaiProsmotrKabineta.this, GlavnaiKabinet.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mena,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_nastroiki){
            Intent openSettings = new Intent(this, Nastroiki.class);
            startActivity(openSettings);
            return  true;
        }
        else if(id==R.id.action_glavnai){
            Intent openAbout = new Intent(this, Glavnai.class);
            startActivity(openAbout);
            return  true;
        }
        else if(id==R.id.action_glavnai_gruppa){
            Intent openAbout = new Intent(this, GlavnaiProsmotrGrupp.class);
            startActivity(openAbout);
            return  true;
        }
        else if(id==R.id.action_glavnai_kabinet){
            Intent openAbout = new Intent(this, GlavnaiProsmotrKabineta.class);
            startActivity(openAbout);
            return  true;
        }
        else if(id==R.id.action_vixod){
            Intent openAbout = new Intent(this, MainActivity.class);
            startActivity(openAbout);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        zagolovokGlavnaiKabinets.clear();
        Cursor cursor = database.query(KabinetDBContract.KabinetEntry.TABLE_NAME,null,null, null, null, null, KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA);
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry._ID));
            String nomer = cursor.getString(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA));
            String vid1 = cursor.getString(cursor.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_VID_KABINETA));
            String vid=" "+vid1;
            zagolovokGlavnaiKabinets.add(new ZagolovokGlavnaiKabinet(id,nomer));
        }
        cursor.close();
    }
}