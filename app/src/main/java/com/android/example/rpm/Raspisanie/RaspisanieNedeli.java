package com.android.example.rpm.Raspisanie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.example.rpm.DB.Gruppa.GruppaDBContract;
import com.android.example.rpm.DB.Gruppa.GruppaDBHelper;
import com.android.example.rpm.DB.Kabinet.KabinetDBHelper;
import com.android.example.rpm.DB.Predmet.PredmetDBContract;
import com.android.example.rpm.DB.Predmet.PredmetDBHelper;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBContract;
import com.android.example.rpm.DB.PredmetPoPrepodu.PPPDBHelper;
import com.android.example.rpm.DB.PredmetiGruppi.PredmetiGruppiDBContract;
import com.android.example.rpm.DB.PredmetiGruppi.PredmetiGruppiDBHelper;
import com.android.example.rpm.DB.Prepod.PrepodDBContract;
import com.android.example.rpm.DB.Prepod.PrepodDBHelper;
import com.android.example.rpm.DB.Raspisanie.RaspisanieDBContract;
import com.android.example.rpm.DB.Raspisanie.RaspisanieDBHelper;
import com.android.example.rpm.Global;
import com.android.example.rpm.PredmetiGruppi.ProsmotrPredmetiGruppi;
import com.android.example.rpm.PredmetiGruppi.ZagolovokPredmetiGruppi;
import com.android.example.rpm.R;

public class RaspisanieNedeli extends AppCompatActivity {

    private Button Para1_ZN,Para2_ZN,Para3_ZN,Para4_ZN,Para5_ZN,Para6_ZN,Para1_KN,Para2_KN,Para3_KN,Para4_KN,Para5_KN,Para6_KN;

    SQLiteDatabase database;
    private RaspisanieDBHelper dbHelper;
    SQLiteDatabase database1;
    private PredmetiGruppiDBHelper dbHelper1;
    SQLiteDatabase database2;
    private GruppaDBHelper dbHelper2;
    SQLiteDatabase database3;
    private PrepodDBHelper dbHelper3;
    SQLiteDatabase database4;
    private PredmetDBHelper dbHelper4;
    SQLiteDatabase database5;
    private PPPDBHelper dbHelper5;
    SQLiteDatabase database6;
    private KabinetDBHelper dbHelper6;

    private Global global;

    private String namegr;
    private String den_n="Понедельник";
    private String zvet_n="";
    private int nomer_pari=0;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raspisanie_nedeli);

        dbHelper = new RaspisanieDBHelper(this);
        database = dbHelper.getWritableDatabase();
        dbHelper1 = new PredmetiGruppiDBHelper(this);
        database1 = dbHelper1.getWritableDatabase();
        dbHelper2 = new GruppaDBHelper(this);
        database2 = dbHelper2.getWritableDatabase();
        dbHelper3 = new PrepodDBHelper(this);
        database3 = dbHelper3.getWritableDatabase();
        dbHelper4 = new PredmetDBHelper(this);
        database4 = dbHelper4.getWritableDatabase();
        dbHelper5 = new PPPDBHelper(this);
        database5 = dbHelper5.getWritableDatabase();
        dbHelper6 = new KabinetDBHelper(this);
        database6 = dbHelper6.getWritableDatabase();

        Para1_ZN=findViewById(R.id.Para1_ZN);
        Para2_ZN=findViewById(R.id.Para2_ZN);
        Para3_ZN=findViewById(R.id.Para3_ZN);
        Para4_ZN=findViewById(R.id.Para4_ZN);
        Para5_ZN=findViewById(R.id.Para5_ZN);
        Para6_ZN=findViewById(R.id.Para6_ZN);

        Para1_KN=findViewById(R.id.Para1_KN);
        Para2_KN=findViewById(R.id.Para2_KN);
        Para3_KN=findViewById(R.id.Para3_KN);
        Para4_KN=findViewById(R.id.Para4_KN);
        Para5_KN=findViewById(R.id.Para5_KN);
        Para6_KN=findViewById(R.id.Para6_KN);

        Cursor cursor2 = database2.query(GruppaDBContract.GruppaEntry.TABLE_NAME,null,null, null, null, null, null);
        namegr="";
        while (cursor2.moveToNext())
        {
            int idgr = cursor2.getInt(cursor2.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry._ID));
            if(idgr==global.AD){
                namegr = cursor2.getString(cursor2.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA));
            }
        }
        cursor2.close();
        this.setTitle(namegr+" "+den_n);
        getPara();
        getData(den_n);

        Cursor cursor = database.query(RaspisanieDBContract.RaspisanieEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor.moveToNext())
        {
            int id_raspisania = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry._ID));
            String den_nedeli=cursor.getString(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_DEN_NEDELI));
            String vid_nedeli=cursor.getString(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_VID_NEDELI));
            int id_predmeta_gruppi = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_ID_PREDMETI_GRUPPI));
            int nomer_pari = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_NOMER_PARI));
            Log.d("Tag", "id_raspisania "+id_raspisania+" den_nedeli "+den_nedeli+" vid_nedeli "+vid_nedeli+" id_predmeta_gruppi "+id_predmeta_gruppi+" nomer_pari "+nomer_pari);
        }
        cursor.close();

        Para1_KN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Красная";
                nomer_pari=1;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para1_ZN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Зеленая";
                nomer_pari=1;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para2_KN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Красная";
                nomer_pari=2;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para2_ZN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Зеленая";
                nomer_pari=2;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para3_KN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Красная";
                nomer_pari=3;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para3_ZN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Зеленая";
                nomer_pari=3;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para4_KN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Красная";
                nomer_pari=4;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para4_ZN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Зеленая";
                nomer_pari=4;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para5_KN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Красная";
                nomer_pari=5;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para5_ZN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Зеленая";
                nomer_pari=5;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para6_KN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Красная";
                nomer_pari=6;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        Para6_ZN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zvet_n="Зеленая";
                nomer_pari=6;
                getYdalenie(nomer_pari,zvet_n,den_n);
                return true;
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public void PN(View view) {
        den_n="Понедельник";
        getPara();
        getData(den_n);
    }

    public void VT(View view) {
        den_n="Вторник";
        getPara();
        getData(den_n);
    }

    public void SR(View view) {
        den_n="Среда";
        getPara();
        getData(den_n);
    }

    public void CH(View view) {
        den_n="Четверг";
        getPara();
        getData(den_n);
    }

    public void PT(View view) {
        den_n="Пятница";
        getPara();
        getData(den_n);
    }

    public void SB(View view) {
        den_n="Суббота";
        getPara();
        getData(den_n);
    }

    public void DobPara1_KN(View view) {
        global.ZN="Красная";
        global.NP=1;
        getKnopka();
    }

    public void DobPara1_ZN(View view) {
        global.ZN="Зеленая";
        global.NP=1;
        getKnopka();
    }

    public void DobPara2_KN(View view) {
        global.ZN="Красная";
        global.NP=2;
        getKnopka();
    }

    public void DobPara2_ZN(View view) {
        global.ZN="Зеленая";
        global.NP=2;
        getKnopka();
    }

    public void DobPara3_KN(View view) {
        global.ZN="красная";
        global.NP=3;
        getKnopka();
    }

    public void DobPara3_ZN(View view) {
        global.ZN="Зеленая";
        global.NP=3;
        getKnopka();
    }

    public void DobPara4_KN(View view) {
        global.ZN="Красная";
        global.NP=4;
        getKnopka();
    }

    public void DobPara4_ZN(View view) {
        global.ZN="Зеленая";
        global.NP=4;
        getKnopka();
    }

    public void DobPara5_KN(View view) {
        global.ZN="Красная";
        global.NP=5;
        getKnopka();
    }

    public void DobPara5_ZN(View view) {
        global.ZN="Зеленая";
        global.NP=5;
        getKnopka();
    }

    public void DobPara6_KN(View view) {
        global.ZN="Красная";
        global.NP=6;
        getKnopka();
    }

    public void DobPara6_ZN(View view) {
        global.ZN="Зеленая";
        global.NP=6;
        getKnopka();
    }

    public void getPara() {
        Para1_ZN.setText("Нет пары");
        Para2_ZN.setText("Нет пары");
        Para3_ZN.setText("Нет пары");
        Para4_ZN.setText("Нет пары");
        Para5_ZN.setText("Нет пары");
        Para6_ZN.setText("Нет пары");

        Para1_KN.setText("Нет пары");
        Para2_KN.setText("Нет пары");
        Para3_KN.setText("Нет пары");
        Para4_KN.setText("Нет пары");
        Para5_KN.setText("Нет пары");
        Para6_KN.setText("Нет пары");
    }

    public void getData(String Den_nedeli){
        this.setTitle(namegr+" "+den_n);
        Cursor cursor = database.query(RaspisanieDBContract.RaspisanieEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor.moveToNext())
        {
            int id_raspisania = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry._ID));
            String den_nedeli=cursor.getString(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_DEN_NEDELI));
            String vid_nedeli=cursor.getString(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_VID_NEDELI));
            int id_predmeta_gruppi = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_ID_PREDMETI_GRUPPI));
            int nomer_pari = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_NOMER_PARI));
            if(den_nedeli.toString().trim().equalsIgnoreCase(Den_nedeli)){
                Cursor cursor1 = database1.query(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,null,null, null, null, null, null);
                while (cursor1.moveToNext())
                {
                    int id_pg = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry._ID));
                    int idpredpoprep = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_PPP));
                    int idgr = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI));
                    if(id_predmeta_gruppi==id_pg){
                        if(idgr==global.AD){
                            //Начало проблемы
                            Cursor cursor5 = database5.query(PPPDBContract.PPPEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                            while (cursor5.moveToNext())
                            {
                                int idppp = cursor5.getInt(cursor5.getColumnIndexOrThrow(PPPDBContract.PPPEntry._ID));
                                int idpred = cursor5.getInt(cursor5.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET));
                                int idprep = cursor5.getInt(cursor5.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD));
                                if(idppp==idpredpoprep){
                                    String fio="";
                                    String predme="";
                                    Cursor cursor3 = database3.query(PrepodDBContract.PrepodEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                                    while (cursor3.moveToNext())
                                    {
                                        int idprepoda = cursor3.getInt(cursor3.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry._ID));
                                        if(idprep==idprepoda){
                                            String fam = cursor3.getString(cursor3.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA));
                                            String ima = cursor3.getString(cursor3.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_IMA));
                                            String otch = cursor3.getString(cursor3.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO));
                                            fio=fam+" "+ima.charAt(0)+"."+otch.charAt(0)+".";
                                        }
                                    }
                                    cursor3.close();
                                    Cursor cursor4 = database4.query(PredmetDBContract.PredmetEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                                    while (cursor4.moveToNext())
                                    {
                                        int idpredmeta = cursor4.getInt(cursor4.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry._ID));
                                        if(idpred==idpredmeta){
                                            String name=cursor4.getString(cursor4.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
                                            predme=name;
                                        }
                                    }
                                    cursor4.close();
                                    String fio_predmet=fio+" "+predme;
                                    //фио, предмет, день недели
                                    if(nomer_pari==1){
                                        if(vid_nedeli.trim().equalsIgnoreCase("Зеленая")){
                                            Para1_ZN.setText(fio_predmet);
                                        }
                                        else if(vid_nedeli.trim().equalsIgnoreCase("Красная")){
                                            Para1_KN.setText(fio_predmet);
                                        }
                                    }
                                    else if(nomer_pari==2){
                                        if(vid_nedeli.trim().equalsIgnoreCase("Зеленая")){
                                            Para2_ZN.setText(fio_predmet);
                                        }
                                        else if(vid_nedeli.trim().equalsIgnoreCase("Красная")){
                                            Para2_KN.setText(fio_predmet);
                                        }
                                    }
                                    else if(nomer_pari==3){
                                        if(vid_nedeli.trim().equalsIgnoreCase("Зеленая")){
                                            Para3_ZN.setText(fio_predmet);
                                        }
                                        else if(vid_nedeli.trim().equalsIgnoreCase("Красная")){
                                            Para3_KN.setText(fio_predmet);
                                        }
                                    }
                                    else if(nomer_pari==4){
                                        if(vid_nedeli.trim().equalsIgnoreCase("Зеленая")){
                                            Para4_ZN.setText(fio_predmet);
                                        }
                                        else if(vid_nedeli.trim().equalsIgnoreCase("Красная")){
                                            Para4_KN.setText(fio_predmet);
                                        }
                                    }
                                    else if(nomer_pari==5){
                                        if(vid_nedeli.trim().equalsIgnoreCase("Зеленая")){
                                            Para5_ZN.setText(fio_predmet);
                                        }
                                        else if(vid_nedeli.trim().equalsIgnoreCase("Красная")){
                                            Para5_KN.setText(fio_predmet);
                                        }
                                    }
                                    else if(nomer_pari==6){
                                        if(vid_nedeli.trim().equalsIgnoreCase("Зеленая")){
                                            Para6_ZN.setText(fio_predmet);
                                        }
                                        else if(vid_nedeli.trim().equalsIgnoreCase("Красная")){
                                            Para6_KN.setText(fio_predmet);
                                        }
                                    }
                                }
                            }
                            cursor5.close();
                            //Конец проблемы
                        }
                    }
                }
                cursor1.close();
            }
        }
        cursor.close();
    }

    public void getKnopka(){
        global.DN=den_n;
        Intent intent = new Intent(this,DobavRaspisanieNedeli.class);
        startActivity(intent);
    }

    private void getYdalenie(int nomer_n, String zvet_n, String den_n){
        Cursor cursor = database.query(RaspisanieDBContract.RaspisanieEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id_raspisania = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry._ID));
            String den_nedeli = cursor.getString(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_DEN_NEDELI));
            String vid_nedeli = cursor.getString(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_VID_NEDELI));
            int nomer_pari = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_NOMER_PARI));
            if(den_nedeli.trim().equalsIgnoreCase(den_n)&&vid_nedeli.trim().equalsIgnoreCase(zvet_n)&&nomer_pari==nomer_n){
                id=id_raspisania;
            }
        }
        String where = RaspisanieDBContract.RaspisanieEntry._ID+" = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(RaspisanieDBContract.RaspisanieEntry.TABLE_NAME,where,whereArgs);
        getPara();
        getData(den_n);
        Toast.makeText(RaspisanieNedeli.this, "Вы удалили пару у группы", Toast.LENGTH_SHORT).show();
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