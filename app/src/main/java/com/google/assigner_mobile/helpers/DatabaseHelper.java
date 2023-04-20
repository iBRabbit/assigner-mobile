package com.google.assigner_mobile.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "assigner.db";
    public final static int DATABASE_VERSION = 1;

    Context context;
    SQLiteDatabase database;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        database = this.getReadableDatabase();
        Log.i("DatabaseHelper", String.format("Database %s created", DATABASE_NAME));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT, phone_number TEXT)";
        sqLiteDatabase.execSQL(query);
        Log.i("DatabaseHelper", "Table users created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS users";
        sqLiteDatabase.execSQL(query);
        Log.i("DatabaseHelper", "Table users dropped");
        onCreate(sqLiteDatabase);
    }

    /**
     * Fungsi untuk menjalankan query secara aman dengan menangkap error
     * @param query Query yang akan dijalankan
     */

    public void query(String query) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
        } catch (Exception e) {
            Log.e("DatabaseHelper: ", String.format("query error: %s", e.getMessage()));
        }

        Log.i("DatabaseHelper: ", "query: " + query);
    }

    /**
     Fungsi untuk mengambil data tipe Integer dari database (Bisa tunggal maupun majemuk)
     * @param columnName Column bertipe Integer yang akan diambil
     * @param num Nilai Integer yang akan diambil
     * @param tableName Nama tabel yang akan diambil
     * @return Cursor Mengembalikan cursor yang berisi data yang diambil
     */

    public Cursor getData(String columnName, Integer num, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        try {
            String query = String.format("SELECT * FROM %s WHERE %s = %d", tableName, columnName, num);
            cursor = db.rawQuery(query, null);
        } catch (Exception e){
            Log.e("UserTable: ", String.format("getData: %s", e.getMessage()));
            return null;
        }

        return cursor;
    }

    /**
     * Fungsi untuk mengambil data tipe String dari database (Bisa tunggal maupun majemuk)
     * Merupakan fungsi overload dari fungsi getData(String dataType, Integer num, String tableName)
     * @param columName Column bertipe String yang akan diambil
     * @param str Nilai String yang akan diambil
     * @param tableName Nama tabel yang akan diambil
     * @return Cursor Mengembalikan cursor yang berisi data yang diambil
     */

    public Cursor getData(String columName, String str, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        try {
            String query = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, columName, str);
            cursor = db.rawQuery(query, null);
        } catch (Exception e){
            Log.e("UserTable: ", String.format("getData: %s", e.getMessage()));
            return null;
        }

        return cursor;
    }

    /**
     * Fungsi untuk mengambil data dari database dengan query yang ditentukan
     * @param query Query yang akan dijalankan
     * @return Cursor Mengembalikan cursor yang berisi data yang diambil
     */

    public Cursor getDataWithQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }
}
