package com.google.assigner_mobile.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.assigner_mobile.models.Assignment;

import java.time.LocalDate;
import java.util.Vector;

public class AssignmentHelper {
    private static final String TABLE_NAME = "assignments";
    private DatabaseHelper dbh;
    private final Context context;

    public AssignmentHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbh = new DatabaseHelper(context);
        dbh.getReadableDatabase();
        Log.i("AssignmentHelper", "Database opened");
    }

    public void close() {
        dbh.close();
        Log.i("AssignmentHelper", "Database closed");
    }

    /**
     * Fungsi untuk memasukkan data assignment ke database
     * @param groupId ID dari group yang akan di masukkan
     * @param name Nama assignment
     * @param description Deskripsi assignment
     * @param createdAt Tanggal dibuatnya assignment
     * @param deadline Tanggal deadline assignment
     */
    public void insert(Integer userId, Integer groupId, String name, String description, LocalDate createdAt, LocalDate deadline) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("group_id", groupId);
        values.put("name", name);
        values.put("description", description);
        values.put("created_at_epoch_day", createdAt.toEpochDay());
        values.put("deadline_epoch_day", deadline.toEpochDay());

        db.insert(TABLE_NAME, null, values);
        Log.d("AssignmentHelper", String.format("Data inserted: %s", name));
    }

    /**
     * Fungsi untuk menghapus data assignment dari database
     * @param id ID dari assignment yang akan dihapus
     */
    public void delete(int id) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        Log.d("AssignmentHelper", String.format("Data deleted: %s", id));
    }

    /**
     * Fungsi untuk mengubah data assignment di database. Ada 3 fungsi ini karena tipe data yang berbeda (Overload)
     * @param id ID dari assignment yang akan diubah
     * @param changeColumnName Nama kolom yang akan diubah
     * @param changeColumnValue Nilai kolom yang akan diubah
     */
    public void update(int id, String changeColumnName, String changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(changeColumnName, changeColumnValue);
        db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        Log.d("AssignmentHelper", String.format("Data updated: %s", id));
    }

    public void update(int id, String changeColumnName, LocalDate changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(changeColumnName, String.valueOf(changeColumnValue));
        db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        Log.d("AssignmentHelper", String.format("Data updated: %s", id));
    }

    public void update(int id, String changeColumnName, Integer changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(changeColumnName, changeColumnValue);
        db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        Log.d("AssignmentHelper", String.format("Data updated: %s", id));
    }

    public Vector <Assignment> getAllAssignmentsByUserId(Integer userId) {
        Cursor cursor = dbh.getData("user_id", userId, TABLE_NAME);
        Vector <Assignment> assignmentVector = new Vector<>();

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int     idColumnIndex = cursor.getColumnIndex("id"),
                    userIdColumnIndex = cursor.getColumnIndex("user_id"),
                    groupIdColumnIndex = cursor.getColumnIndex("group_id"),
                    nameColumnIndex = cursor.getColumnIndex("name"),
                    descriptionColumnIndex = cursor.getColumnIndex("description"),
                    createdAtColumnIndex = cursor.getColumnIndex("created_at_epoch_day"),
                    deadlineColumnIndex = cursor.getColumnIndex("deadline_epoch_day");

            LocalDate
                    createdAt = LocalDate.ofEpochDay(cursor.getLong(createdAtColumnIndex)) ,
                    deadline = LocalDate.ofEpochDay(cursor.getLong(deadlineColumnIndex));

            assignmentVector.add(
                    new Assignment(
                            cursor.getInt(idColumnIndex),
                            cursor.getInt(userIdColumnIndex),
                            cursor.getInt(groupIdColumnIndex),
                            cursor.getString(nameColumnIndex),
                            cursor.getString(descriptionColumnIndex),
                            createdAt,
                            deadline
                    )
            );

        }
        return assignmentVector;
    }
}
