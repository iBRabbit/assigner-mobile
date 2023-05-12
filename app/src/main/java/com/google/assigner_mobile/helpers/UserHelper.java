package com.google.assigner_mobile.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.assigner_mobile.functions.GlobalFunction;
import com.google.assigner_mobile.models.User;

import java.util.Vector;

public class UserHelper {
    private static final String TABLE_NAME = "users";
    private DatabaseHelper dbh;
    private final Context context;

    public GlobalFunction func = new GlobalFunction();

    public UserHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbh = new DatabaseHelper(context);
        dbh.getReadableDatabase();
        Log.i("UserHelper", "Database opened");
    }

    public void close() {
        dbh.close();
        Log.i("UserHelper", "Database closed");
    }

    /**
     * Fungsi untuk mengecek apakah data sudah ada di database
     * @param columnName Nama kolom yang akan di cek
     * @param str Data yang akan di cek
     * @return Boolean true jika data sudah ada, false jika tidak
     */
    public Boolean isUserExists(String columnName, String str) {
        Cursor cursor;

        try {
            cursor = dbh.getData(columnName, str, TABLE_NAME);
        } catch (Exception e) {
            Log.e("UserTable: ", String.format("Error in isDataAlreadyExists: %s", e.getMessage()));
            return false;
        }

        return cursor.getCount() > 0;
    }

    /**
     * Fungsi untuk mengambil data user berdasarkan username
     * @param username Username yang akan diambil
     * @return User yang diambil
     */
    public Integer getIDByUsername(String username) {
        Cursor cursor;

        cursor = dbh.getData("username", username, TABLE_NAME);
        if(cursor.getCount() <= 0)
            return -1;

        cursor.moveToFirst();

        int idIdx = cursor.getColumnIndex("id");
        return cursor.getInt(idIdx);
    }

    /**
     * Fungsi untuk melakukan insert data user ke database
     * @param username Username yang akan di insert
     * @param password Password yang akan di insert
     * @param phoneNumber Nomor telepon yang akan di insert
     * @param email Email yang akan di insert
     */
    public Boolean insert(String username, String password, String email, String phoneNumber) {
        SQLiteDatabase database = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();

        try {
            values.put("username", username);
            values.put("password", password);
            values.put("phone_number", phoneNumber);
            values.put("email", email);

            database.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("UserTable: ", String.format("Error in insert: %s", e.getMessage()));
            return false;
        }

        Log.d("UserTable: ", String.format("insert: Data %s [id: %d] has been inserted into %s", username, getIDByUsername(username) ,TABLE_NAME));
        return true;
    }

    /**
     * Fungsi untuk melakukan autentikasi login user
     * @param username Username yang akan di cek
     * @param password Password yang akan di cek
     * @return Boolean true jika username dan password cocok, false jika tidak
     */
    public Boolean auth(String username, String password) {

        if(func.hasInjection(username) || func.hasInjection(password))
            return false;

        String query = String.format("SELECT * FROM %s WHERE username = '%s' AND password = '%s'", TABLE_NAME, username, password);
        return dbh.getDataWithQuery(query).getCount() > 0;
    }

    /**
     * Fungsi untuk mengambil data user dari database berdasarkan tipe data integer
     * Setara dengan:
     * SELECT * FROM users WHERE dataType = num
     * @param columnName Column bertipe data integer yang akan diambil
     * @param num Nilai dari tipe data yang akan diambil
     * @return Object User yang berisi data user
     */

    public User getUser(String columnName, Integer num) {
        Cursor cursor = dbh.getData(columnName, num, TABLE_NAME);
        if(cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        int idColIndex = cursor.getColumnIndex("id"),
                usernameColIndex = cursor.getColumnIndex("username"),
                passwordColIndex = cursor.getColumnIndex("password"),
                phoneNumberColIndex = cursor.getColumnIndex("phone_number"),
                emailColIndex = cursor.getColumnIndex("email");

        return new User(
                cursor.getInt(idColIndex),
                cursor.getString(usernameColIndex),
                cursor.getString(passwordColIndex),
                cursor.getString(emailColIndex),
                cursor.getString(phoneNumberColIndex)
        );
    }

    public User getUser(String columnName, String str) {
        Cursor cursor = dbh.getData(columnName, str, TABLE_NAME);
        if(cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        int idColIndex = cursor.getColumnIndex("id"),
                usernameColIndex = cursor.getColumnIndex("username"),
                passwordColIndex = cursor.getColumnIndex("password"),
                phoneNumberColIndex = cursor.getColumnIndex("phone_number"),
                emailColIndex = cursor.getColumnIndex("email");

        return new User(
                cursor.getInt(idColIndex),
                cursor.getString(usernameColIndex),
                cursor.getString(passwordColIndex),
                cursor.getString(emailColIndex),
                cursor.getString(phoneNumberColIndex)
        );
    }

    public Vector <User> getAllData() {
        Vector <User> userVector = new Vector<>();
        Cursor cursor = dbh.getDataWithQuery("SELECT * FROM users");

        if(cursor.getCount() == 0)
            return null;

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int idColIndex = cursor.getColumnIndex("id"),
                    usernameColIndex = cursor.getColumnIndex("username"),
                    passwordColIndex = cursor.getColumnIndex("password"),
                    phoneNumberColIndex = cursor.getColumnIndex("phone_number"),
                    emailColIndex = cursor.getColumnIndex("email");

            userVector.add(new User(
                    cursor.getInt(idColIndex),
                    cursor.getString(usernameColIndex),
                    cursor.getString(passwordColIndex),
                    cursor.getString(emailColIndex),
                    cursor.getString(phoneNumberColIndex)
            ));
        }
        return userVector;
    }

}
