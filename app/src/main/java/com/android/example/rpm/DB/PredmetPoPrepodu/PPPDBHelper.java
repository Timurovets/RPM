package com.android.example.rpm.DB.PredmetPoPrepodu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PPPDBHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "predmet_po_prepodu.db";
    private static final int DB_VERSION = 3;

    public PPPDBHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PPPDBContract.PPPEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(PPPDBContract.PPPEntry.DROP_COMMAND);
        onCreate(db);
    }
}
