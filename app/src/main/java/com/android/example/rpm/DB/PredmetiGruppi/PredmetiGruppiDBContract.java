package com.android.example.rpm.DB.PredmetiGruppi;

import android.provider.BaseColumns;

public class PredmetiGruppiDBContract {
    public static final class PredmetiGruppiEntry implements BaseColumns {

        public static final String TABLE_NAME = "PredmetiGruppi";//название

        public static final String COLUMN_ID_GRUPPI="id_gruppi";
        public static final String COLUMN_ID_PPP="id_ppp";
        public static final String COLUMN_KOLICHESTVO_CHASOV="kolichestvo_chasov";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+
                "("+_ID+" "+TYPE_INTEGER+" PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_ID_GRUPPI+" "+TYPE_INTEGER+", "+
                COLUMN_ID_PPP+" "+TYPE_INTEGER+", "+
                COLUMN_KOLICHESTVO_CHASOV+" "+TYPE_INTEGER+")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS" + TABLE_NAME;

    }
}
