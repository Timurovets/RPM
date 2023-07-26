package com.android.example.rpm.DB.Raspisanie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RaspisanieDBHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "raspisanie.db";
    private static final int DB_VERSION = 3;

    public RaspisanieDBHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RaspisanieDBContract.RaspisanieEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(RaspisanieDBContract.RaspisanieEntry.DROP_COMMAND);
        onCreate(db);
    }
}