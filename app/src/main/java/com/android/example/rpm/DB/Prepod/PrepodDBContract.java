package com.android.example.rpm.DB.Prepod;

import android.provider.BaseColumns;

public class PrepodDBContract {
    public static final class PrepodEntry implements BaseColumns {

        public static final String TABLE_NAME = "Prepod";//название

        public static final String COLUMN_FAMILIA="familia";
        public static final String COLUMN_IMA="ima";
        public static final String COLUMN_OTCHESTVO="otchestvo";

        public static final String COLUMN_LOGIN="login";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_E_MAIL="e_mail";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+
                "("+_ID+" "+TYPE_INTEGER+" PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_FAMILIA+" "+TYPE_TEXT+", "+
                COLUMN_IMA+" "+TYPE_TEXT+", "+
                COLUMN_OTCHESTVO+" "+TYPE_TEXT+", "+
                COLUMN_LOGIN+" "+TYPE_TEXT+", "+
                COLUMN_PASSWORD +" "+TYPE_TEXT+", "+
                COLUMN_E_MAIL+" "+TYPE_TEXT+")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS" + TABLE_NAME;

    }
}
