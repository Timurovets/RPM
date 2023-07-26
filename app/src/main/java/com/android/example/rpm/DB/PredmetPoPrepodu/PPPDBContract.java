package com.android.example.rpm.DB.PredmetPoPrepodu;

import android.provider.BaseColumns;

public class PPPDBContract {
    public static final class PPPEntry implements BaseColumns {

        public static final String TABLE_NAME = "PredmetPoPrepodu";//название

        public static final String COLUMN_ID_PREPOD="id_prepod";
        public static final String COLUMN_ID_PREDMET="id_predmet";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+
                "("+_ID+" "+TYPE_INTEGER+" PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_ID_PREPOD+" "+TYPE_INTEGER+", "+
                COLUMN_ID_PREDMET+" "+TYPE_INTEGER+")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS" + TABLE_NAME;

    }
}
