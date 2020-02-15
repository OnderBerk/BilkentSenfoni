package com.mobileproje.Database;

import android.content.ContentValues;

public class ConcertDB {
    public static final String TABLE_NAME ="concert";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_SURNAME = "surname";
    public static final String FIELD_TOTAL = "totprice";

    public static final String CREATE_TABLE_SQL = "CREATE TABLE "+TABLE_NAME+" ("+FIELD_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+FIELD_NAME+" text, "+FIELD_SURNAME+" text, "+FIELD_TOTAL+" text);";
    public static final String DROP_TABLE_SQL = "DROP TABLE if exists "+TABLE_NAME;

    public static long insertData(DatabaseHelper db, String name, String surname, int totprice){
        ContentValues contentValues = new ContentValues( );
        contentValues.put(FIELD_NAME, name);
        contentValues.put(FIELD_SURNAME, surname);
        contentValues.put(FIELD_TOTAL, totprice);
        long res = db.insert(TABLE_NAME,contentValues);
        return res;
    }
}
