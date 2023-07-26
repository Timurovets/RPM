package com.android.example.rpm.DB.Gruppa;

import android.provider.BaseColumns;

public class GruppaDBContract {
    public static final class GruppaEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "Gruppa";//название

        public static final String COLUMN_NAME_GRUPPA="name_gruppa";
        public static final String COLUMN_KOLICHESTVO = "kolichestvo";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+
                "("+_ID+" "+TYPE_INTEGER+" PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME_GRUPPA+" "+TYPE_TEXT+", "+
                COLUMN_KOLICHESTVO+" "+TYPE_INTEGER+")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS" + TABLE_NAME;
    }
}
