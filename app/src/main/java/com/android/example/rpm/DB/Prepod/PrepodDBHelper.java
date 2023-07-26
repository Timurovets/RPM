package com.android.example.rpm.DB.Prepod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PrepodDBHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "prepod.db";
    private static final int DB_VERSION = 3;

    public PrepodDBHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PrepodDBContract.PrepodEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(PrepodDBContract.PrepodEntry.DROP_COMMAND);
        onCreate(db);
    }
}
