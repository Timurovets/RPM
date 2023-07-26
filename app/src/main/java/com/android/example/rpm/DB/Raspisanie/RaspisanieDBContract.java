package com.android.example.rpm.DB.Raspisanie;

import android.provider.BaseColumns;

public class RaspisanieDBContract {
    public static final class RaspisanieEntry implements BaseColumns {

        public static final String TABLE_NAME = "Raspisanie";//название

        public static final String COLUMN_ID_PREDMETI_GRUPPI="id_predmeti_gruppi";
        public static final String COLUMN_DEN_NEDELI="den_nedeli";
        public static final String COLUMN_VID_NEDELI="vid_nedeli";
        public static final String COLUMN_ID_KABINETA="id_kabineta";
        public static final String COLUMN_NOMER_PARI="nomer_pari";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+
                "("+_ID+" "+TYPE_INTEGER+" PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_ID_PREDMETI_GRUPPI+" "+TYPE_INTEGER+", "+
                COLUMN_DEN_NEDELI+" "+TYPE_TEXT+", "+
                COLUMN_VID_NEDELI +" "+TYPE_TEXT+", "+
                COLUMN_ID_KABINETA +" "+TYPE_TEXT+", "+
                COLUMN_NOMER_PARI+" "+TYPE_INTEGER+")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS" + TABLE_NAME;

    }
}
