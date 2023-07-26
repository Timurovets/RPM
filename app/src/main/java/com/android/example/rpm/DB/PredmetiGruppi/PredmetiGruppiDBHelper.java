package com.android.example.rpm.DB.PredmetiGruppi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PredmetiGruppiDBHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "predmeti_gruppi.db";
    private static final int DB_VERSION = 3;

    public PredmetiGruppiDBHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PredmetiGruppiDBContract.PredmetiGruppiEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(PredmetiGruppiDBContract.PredmetiGruppiEntry.DROP_COMMAND);
        onCreate(db);
    }
}