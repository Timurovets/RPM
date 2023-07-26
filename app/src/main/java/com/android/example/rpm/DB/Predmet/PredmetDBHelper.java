package com.android.example.rpm.DB.Predmet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PredmetDBHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "predmet.db";
    private static final int DB_VERSION = 2;

    public PredmetDBHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PredmetDBContract.PredmetEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(PredmetDBContract.PredmetEntry.DROP_COMMAND);
        onCreate(db);
    }
}
