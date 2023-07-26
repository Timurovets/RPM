package com.android.example.rpm.DB.Predmet;

import android.provider.BaseColumns;

public class PredmetDBContract {
    public static final class PredmetEntry implements BaseColumns {

        public static final String TABLE_NAME = "Predmet";//название

        public static final String COLUMN_NAME_PREDMETA="name_predmeta";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+
                "("+_ID+" "+TYPE_INTEGER+" PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME_PREDMETA+" "+TYPE_TEXT+")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS" + TABLE_NAME;

    }
}
