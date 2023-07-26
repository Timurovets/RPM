package com.android.example.rpm.Raspisanie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.example.rpm.DB.Gruppa.GruppaDBContract;
import com.android.example.rpm.DB.Gruppa.GruppaDBHelper;
import com.android.example.rpm.DB.Kabinet.KabinetDBContract;
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

import java.util.ArrayList;

public class DobavRaspisanieNedeli extends AppCompatActivity {

    public Spinner SpinnerPredmetovGruppi;
    public Spinner SpinnerKabinetov;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobav_raspisanie_nedeli);

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

        SpinnerKab();
        SpinnerPG();

        Cursor cursor2 = database2.query(GruppaDBContract.GruppaEntry.TABLE_NAME,null,null, null, null, null, null);
        String namegr="";
        while (cursor2.moveToNext())
        {
            int idgr = cursor2.getInt(cursor2.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry._ID));
            if(idgr==global.AD){
                namegr = cursor2.getString(cursor2.getColumnIndexOrThrow(GruppaDBContract.GruppaEntry.COLUMN_NAME_GRUPPA));
            }
        }
        cursor2.close();
        this.setTitle(namegr+" "+global.DN);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void Vnos(View view) {
        String predmet_gruppi=SpinnerPredmetovGruppi.getSelectedItem().toString();
        String kabinet=SpinnerKabinetov.getSelectedItem().toString();

        int net=0;
        int prep=0;
        int pysto=0;
        Cursor cursor = database.query(RaspisanieDBContract.RaspisanieEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor.moveToNext())//Данная проверка проверяет есть ли у группы что-нибудь в понедельник по зеленой неделе  первой парой
        {
            String den_nedeli=cursor.getString(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_DEN_NEDELI));
            String vid_nedeli=cursor.getString(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_VID_NEDELI));
            int id_predmeta_gruppi = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_ID_PREDMETI_GRUPPI));
            int nomer_pari = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_NOMER_PARI));
            if(den_nedeli.toString().trim().equalsIgnoreCase(global.DN)){
                if(nomer_pari==global.NP){
                    if(vid_nedeli.toString().trim().equalsIgnoreCase(global.ZN)){
                        Cursor cursor1 = database1.query(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,null,null, null, null, null, null);
                        while (cursor1.moveToNext())
                        {
                            int id_pg = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry._ID));
                            int idgr = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI));
                            if(id_predmeta_gruppi==id_pg){
                                if(idgr==global.AD){
                                    net++;
                                }
                            }
                        }
                        cursor1.close();
                    }
                }
            }
            int id_kabineta = cursor.getInt(cursor.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_ID_KABINETA));
            Cursor cursor6 = database6.query(KabinetDBContract.KabinetEntry.TABLE_NAME,null,null, null, null, null, null);
            while (cursor6.moveToNext())
            {
                int idkab = cursor6.getInt(cursor6.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry._ID));
                String name_kab = cursor6.getString(cursor6.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA));
                if(idkab==id_kabineta){
                    if(name_kab.trim().equalsIgnoreCase(kabinet)&&den_nedeli.trim().equalsIgnoreCase(global.DN)&&vid_nedeli.trim().equalsIgnoreCase(global.ZN)&&nomer_pari==global.NP){
                        pysto++;
                    }
                }
            }
            cursor6.close();
        }

        cursor.close();
        String FIO="";
        Cursor cursor7 = database5.query(PPPDBContract.PPPEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
        while (cursor7.moveToNext())
        {
            int idpred = cursor7.getInt(cursor7.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET));
            int idprep = cursor7.getInt(cursor7.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD));
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
            if(fio_predmet.equalsIgnoreCase(predmet_gruppi)){
                FIO=fio;
            }
        }
        cursor7.close();
        Cursor cursor8 = database.query(RaspisanieDBContract.RaspisanieEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor8.moveToNext())
        {
            String vid_nedeli=cursor8.getString(cursor8.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_VID_NEDELI));
            int nomer_pari = cursor8.getInt(cursor8.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_NOMER_PARI));
            String den_nedeli=cursor8.getString(cursor8.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_DEN_NEDELI));
            int id_predmeta_gruppi = cursor8.getInt(cursor8.getColumnIndexOrThrow(RaspisanieDBContract.RaspisanieEntry.COLUMN_ID_PREDMETI_GRUPPI));
            if(den_nedeli.toString().trim().equalsIgnoreCase(global.DN)){
                Cursor cursor1 = database1.query(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,null,null, null, null, null, null);
                while (cursor1.moveToNext())
                {
                    int id_pg = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry._ID));
                    int idpredpoprep = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_PPP));
                    int idgr = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI));
                    if(id_predmeta_gruppi==id_pg){
                        //Начало проблемы
                        Cursor cursor5 = database5.query(PPPDBContract.PPPEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                        while (cursor5.moveToNext())
                        {
                            int idppp = cursor5.getInt(cursor5.getColumnIndexOrThrow(PPPDBContract.PPPEntry._ID));
                            int idprep = cursor5.getInt(cursor5.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD));
                            if(idppp==idpredpoprep){
                                String fio="";
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
                                if(FIO.trim().equalsIgnoreCase(fio)&&den_nedeli.trim().equalsIgnoreCase(global.DN)&&vid_nedeli.trim().equalsIgnoreCase(global.ZN)&&nomer_pari==global.NP){
                                    prep++;
                                }
                            }
                        }
                        cursor5.close();
                    }
                }
                cursor1.close();
            }
        }
        cursor8.close();
        if(prep==0) {
            if (pysto == 0) {
                if (net == 0) {
                    int bb = 0;
                    ContentValues contentValues = new ContentValues();

                    int id_kabinet = 0;
                    Cursor cursor6 = database6.query(KabinetDBContract.KabinetEntry.TABLE_NAME, null, null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                    while (cursor6.moveToNext()) {
                        int id_k = cursor6.getInt(cursor6.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry._ID));
                        String name_kab = cursor6.getString(cursor6.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA));
                        if (name_kab.toString().trim().equalsIgnoreCase(kabinet)) {
                            id_kabinet = id_k;
                        }
                    }
                    cursor6.close();

                    Cursor cursor1 = database1.query(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME, null, null, null, null, null, null);
                    while (cursor1.moveToNext()) {
                        int id_pg = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry._ID));
                        int idpredpoprep = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_PPP));
                        int idgr = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI));
                        if (idgr == global.AD) {
                            //Начало проблемы
                            Cursor cursor5 = database5.query(PPPDBContract.PPPEntry.TABLE_NAME, null, null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                            while (cursor5.moveToNext()) {
                                int idppp = cursor5.getInt(cursor5.getColumnIndexOrThrow(PPPDBContract.PPPEntry._ID));
                                int idpred = cursor5.getInt(cursor5.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREDMET));
                                int idprep = cursor5.getInt(cursor5.getColumnIndexOrThrow(PPPDBContract.PPPEntry.COLUMN_ID_PREPOD));
                                if (idppp == idpredpoprep) {
                                    String fio = "";
                                    String predme = "";
                                    Cursor cursor3 = database3.query(PrepodDBContract.PrepodEntry.TABLE_NAME, null, null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                                    while (cursor3.moveToNext()) {
                                        int idprepoda = cursor3.getInt(cursor3.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry._ID));
                                        if (idprep == idprepoda) {
                                            String fam = cursor3.getString(cursor3.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_FAMILIA));
                                            String ima = cursor3.getString(cursor3.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_IMA));
                                            String otch = cursor3.getString(cursor3.getColumnIndexOrThrow(PrepodDBContract.PrepodEntry.COLUMN_OTCHESTVO));
                                            fio = fam + " " + ima.charAt(0) + "." + otch.charAt(0) + ".";
                                        }
                                    }
                                    cursor3.close();
                                    Cursor cursor4 = database4.query(PredmetDBContract.PredmetEntry.TABLE_NAME, null, null, null, null, null, PredmetDBContract.PredmetEntry._ID);
                                    while (cursor4.moveToNext()) {
                                        int idpredmeta = cursor4.getInt(cursor4.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry._ID));
                                        if (idpred == idpredmeta) {
                                            String name = cursor4.getString(cursor4.getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
                                            predme = name;
                                        }
                                    }
                                    cursor4.close();
                                    String fio_predmet = fio + " " + predme;
                                    //фио, предмет, день недели
                                    if (predmet_gruppi.trim().equalsIgnoreCase(fio_predmet)) {
                                        bb = id_pg;
                                    }
                                }
                            }
                            cursor5.close();
                            //Конец проблемы
                        }

                    }
                    cursor1.close();

                    contentValues.put(RaspisanieDBContract.RaspisanieEntry.COLUMN_ID_PREDMETI_GRUPPI, bb);
                    contentValues.put(RaspisanieDBContract.RaspisanieEntry.COLUMN_ID_KABINETA, id_kabinet);
                    contentValues.put(RaspisanieDBContract.RaspisanieEntry.COLUMN_VID_NEDELI, global.ZN);
                    contentValues.put(RaspisanieDBContract.RaspisanieEntry.COLUMN_DEN_NEDELI, global.DN.trim());
                    contentValues.put(RaspisanieDBContract.RaspisanieEntry.COLUMN_NOMER_PARI, global.NP);
                    database.insert(RaspisanieDBContract.RaspisanieEntry.TABLE_NAME, null, contentValues);
                    Intent intent = new Intent(this, RaspisanieNedeli.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Данное место у группы занято", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Кабинет занят", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Преподаватель на это место занят", Toast.LENGTH_SHORT).show();
        }
    }

    public void SpinnerKab(){//Спиннер на вывод кабинетов
        SpinnerKabinetov=findViewById(R.id.SpinnerKabinetov);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>());
        SpinnerKabinetov.setAdapter(adapter);
        // получение данных из базы данных

        Cursor cursor6 = database6.query(KabinetDBContract.KabinetEntry.TABLE_NAME,null,null, null, null, null, PredmetDBContract.PredmetEntry._ID);
        while (cursor6.moveToNext())
        {
            String nomer_kabinet=cursor6.getString(cursor6.getColumnIndexOrThrow(KabinetDBContract.KabinetEntry.COLUMN_NOMER_KABINETA));
            adapter.add(nomer_kabinet);
        }
        cursor6.close();
    }

    public void SpinnerPG(){
        SpinnerPredmetovGruppi=findViewById(R.id.SpinnerPredmetovGruppi);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>());
        SpinnerPredmetovGruppi.setAdapter(adapter1);
        // получение данных из базы данных

        Cursor cursor1 = database1.query(PredmetiGruppiDBContract.PredmetiGruppiEntry.TABLE_NAME,null,null, null, null, null, null);
        while (cursor1.moveToNext())
        {
            int id = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry._ID));
            int idpredpoprep = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_PPP));
            int idgr = cursor1.getInt(cursor1.getColumnIndexOrThrow(PredmetiGruppiDBContract.PredmetiGruppiEntry.COLUMN_ID_GRUPPI));
            if(idgr==global.AD){
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
                        while (cursor4 .moveToNext())
                        {
                            int idpredmeta = cursor4 .getInt(cursor4 .getColumnIndexOrThrow(PredmetDBContract.PredmetEntry._ID));
                            if(idpred==idpredmeta){
                                String name=cursor4 .getString(cursor4 .getColumnIndexOrThrow(PredmetDBContract.PredmetEntry.COLUMN_NAME_PREDMETA));
                                predme=name;
                            }
                        }
                        cursor4 .close();
                        String fio_predmet_chas=fio+" "+predme;
                        adapter1.add(fio_predmet_chas);
                    }
                }
                cursor5.close();
            }
        }
        cursor1.close();
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