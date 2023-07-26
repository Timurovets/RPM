package com.android.example.rpm.DB.Kabinet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class KabinetDBHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "kabinet.db";
    private static final int DB_VERSION = 2;

    public KabinetDBHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KabinetDBContract.KabinetEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(KabinetDBContract.KabinetEntry.DROP_COMMAND);
        onCreate(db);
    }
}
