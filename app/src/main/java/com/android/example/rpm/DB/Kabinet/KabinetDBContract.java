package com.android.example.rpm.DB.Kabinet;

import android.provider.BaseColumns;

public class KabinetDBContract {
    public static final class KabinetEntry implements BaseColumns {

        public static final String TABLE_NAME = "Kabinet";//название

        public static final String COLUMN_NOMER_KABINETA="nomer_kabineta";
        public static final String COLUMN_VID_KABINETA = "vid_kabineta";
        public static final String COLUMN_KOLICHESTVO_MEST="kolichestvo_mest";
        public static final String COLUMN_NALICHIE_PROEKTORA="nalichie_proektora";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+
                "("+_ID+" "+TYPE_INTEGER+" PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NOMER_KABINETA+" "+TYPE_TEXT+", "+
                COLUMN_VID_KABINETA +" "+TYPE_TEXT+", "+
                COLUMN_KOLICHESTVO_MEST +" "+TYPE_INTEGER+", "+
                COLUMN_NALICHIE_PROEKTORA+" "+TYPE_TEXT+")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS" + TABLE_NAME;

    }
}
