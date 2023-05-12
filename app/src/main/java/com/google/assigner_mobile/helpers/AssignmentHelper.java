package com.google.assigner_mobile.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.assigner_mobile.models.Assignment;

import java.time.LocalDate;
import java.util.Collections;
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
     * @return boolean apakah data berhasil dimasukkan atau tidak
     */
    public boolean insert(Integer userId, Integer groupId, String name, String description, LocalDate createdAt, LocalDate deadline) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put("user_id", userId);
            values.put("group_id", groupId);
            values.put("name", name);
            values.put("description", description);
            values.put("created_at_epoch_day", createdAt.toEpochDay());
            values.put("deadline_epoch_day", deadline.toEpochDay());
            values.put("progress", 0);

            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("AssignmentHelper", String.format("Insert failed: %s", e.getMessage()));
            return false;
        }

        Log.d("AssignmentHelper", String.format("Data inserted: %s", name));

        return true;
    }

    public boolean insert(Integer userId, Integer groupId, String name, String description, LocalDate createdAt, LocalDate deadline, Integer progress) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put("user_id", userId);
            values.put("group_id", groupId);
            values.put("name", name);
            values.put("description", description);
            values.put("created_at_epoch_day", createdAt.toEpochDay());
            values.put("deadline_epoch_day", deadline.toEpochDay());
            values.put("progress", progress);

            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("AssignmentHelper", String.format("Insert failed: %s", e.getMessage()));
            return false;
        }

        Log.d("AssignmentHelper", String.format("Data inserted: %s", name));

        return true;
    }


    /**
     * Fungsi untuk menghapus data assignment dari database
     * @param id ID dari assignment yang akan dihapus
     * @return boolean apakah data berhasil dihapus atau tidak
     */
    public boolean delete(int id) {
        try {
            dbh.execQuery(String.format("DELETE FROM %s WHERE id = %s", TABLE_NAME, id));
            Log.i("AssignmentHelper", String.format("Data deleted: %s", id));
        } catch (Exception e) {
            Log.e("AssignmentHelper", String.format("Delete failed: %s", e.getMessage()));
            return false;
        }

        Log.d("AssignmentHelper", String.format("Data deleted: %s", id));

        return true;
    }

    /**
     * Fungsi untuk menghapus semua data assignment dari user tertentu di group tertentu
     * @param userId ID dari user yang akan dihapus assignmentnya
     * @param groupId  ID dari group yang akan dihapus assignmentnya
     * @return boolean apakah data berhasil dihapus atau tidak
     */
    public boolean deleteAllAssignmentForUserInGroup(int userId, int groupId) {
        try {
            dbh.execQuery(String.format("DELETE FROM %s WHERE user_id = %s AND group_id = %s", TABLE_NAME, userId, groupId));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Fungsi untuk mengubah data assignment di database. Ada 3 fungsi ini karena tipe data yang berbeda (Overload)
     * @param id ID dari assignment yang akan diubah
     * @param changeColumnName Nama kolom yang akan diubah
     * @param changeColumnValue Nilai kolom yang akan diubah
     * @return boolean apakah data berhasil diubah atau tidak
     */
    public boolean update(int id, String changeColumnName, String changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(changeColumnName, changeColumnValue);
            db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("AssignmentHelper", String.format("Update failed: %s", e.getMessage()));
            return false;
        }

        Log.d("AssignmentHelper", String.format("Data updated: %s", id));

        return true;
    }

    public boolean update(int id, String changeColumnName, Integer changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(changeColumnName, changeColumnValue);
            db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("AssignmentHelper", String.format("Update failed: %s", e.getMessage()));
            return false;
        }

        Log.d("AssignmentHelper", String.format("Data updated: %s", id));

        return true;
    }

    public boolean update(int id, String changeColumnName, LocalDate changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(changeColumnName, changeColumnValue.toEpochDay());
            db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("AssignmentHelper", String.format("Update failed: %s", e.getMessage()));
            return false;
        }

        Log.d("AssignmentHelper", String.format("Data updated: %s", id));

        return true;
    }

    /**
     * Fungsi untuk mendapatkan satu assignment dari database berdasarkan id.
     * @param id ID dari assignment yang akan didapatkan
     * @return Assignment yang didapatkan
     */
    public Assignment getAssignmentById(int id) {
        Cursor cursor = dbh.getData("id", id, TABLE_NAME);

        int     idColumnIndex = cursor.getColumnIndex("id"),
                userIdColumnIndex = cursor.getColumnIndex("user_id"),
                groupIdColumnIndex = cursor.getColumnIndex("group_id"),
                nameColumnIndex = cursor.getColumnIndex("name"),
                descriptionColumnIndex = cursor.getColumnIndex("description"),
                createdAtColumnIndex = cursor.getColumnIndex("created_at_epoch_day"),
                progressColumnIndex = cursor.getColumnIndex("progress"),
                deadlineColumnIndex = cursor.getColumnIndex("deadline_epoch_day");

        LocalDate
                createdAt = LocalDate.ofEpochDay(cursor.getLong(createdAtColumnIndex)) ,
                deadline = LocalDate.ofEpochDay(cursor.getLong(deadlineColumnIndex));

        return new Assignment(
                cursor.getInt(idColumnIndex),
                cursor.getInt(userIdColumnIndex),
                cursor.getInt(groupIdColumnIndex),
                cursor.getString(nameColumnIndex),
                cursor.getString(descriptionColumnIndex),
                createdAt,
                deadline,
                cursor.getInt(progressColumnIndex)
        );
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
                    deadlineColumnIndex = cursor.getColumnIndex("deadline_epoch_day"),
                    progressColumnIndex = cursor.getColumnIndex("progress");

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
                            deadline,
                            cursor.getInt(progressColumnIndex)
                    )
            );

        }

        assignmentVector = sortAssignmentsVectorByDeadline(assignmentVector);

        return assignmentVector;
    }

    public Vector <Assignment> getAllAssignmentsByGroupId(Integer groupId) {
        Cursor cursor = dbh.getData("group_id", groupId, TABLE_NAME);
        Vector <Assignment> assignmentVector = new Vector<>();

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int     idColumnIndex = cursor.getColumnIndex("id"),
                    userIdColumnIndex = cursor.getColumnIndex("user_id"),
                    groupIdColumnIndex = cursor.getColumnIndex("group_id"),
                    nameColumnIndex = cursor.getColumnIndex("name"),
                    descriptionColumnIndex = cursor.getColumnIndex("description"),
                    createdAtColumnIndex = cursor.getColumnIndex("created_at_epoch_day"),
                    progressColumnIndex = cursor.getColumnIndex("progress"),
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
                            deadline,
                            cursor.getInt(progressColumnIndex)
                    )
            );

        }

        assignmentVector = sortAssignmentsVectorByDeadline(assignmentVector);

        return assignmentVector;
    }

    /**
     * Fungsi untuk melakukan sort terhadap vector assignment berdasarkan deadline.
     * @param assignments Vector assignment yang akan di sort
     * @return Vector assignment yang sudah disort
     */
    public Vector<Assignment> sortAssignmentsVectorByDeadline(Vector<Assignment> assignments) {
        Collections.sort(assignments, (o1, o2) -> o1.getDeadline().compareTo(o2.getDeadline()));
        return assignments;
    }

}
