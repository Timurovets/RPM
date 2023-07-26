package com.android.example.rpm.DB.Gruppa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class GruppaDBHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "gruppa.db";
    private static final int DB_VERSION = 2;

    public GruppaDBHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GruppaDBContract.GruppaEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(GruppaDBContract.GruppaEntry.DROP_COMMAND);
        onCreate(db);
    }
}
