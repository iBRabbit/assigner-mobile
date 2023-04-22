package com.google.assigner_mobile.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.assigner_mobile.models.Group;

import java.util.Vector;


public class GroupHelper {
    private static final String TABLE_NAME = "groups";
    private DatabaseHelper dbh;
    private final Context context;

    public GroupHelper(Context context) {
        this.context = context;
        dbh = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dbh = new DatabaseHelper(context);
        dbh.getReadableDatabase();
        Log.i("GroupHelper", "Database opened");
    }

    public void close() throws SQLException {
        dbh.close();
        Log.i("GroupHelper", "Database closed");
    }

    /**
     * Fungsi untuk memasukkan data group ke database
     * @param name Nama group
     * @param description Deskripsi group
     * @param ownerId ID dari owner group
     */
    public Boolean insert(String name, String description, Integer ownerId) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = -1;

        try {
            values.put("name", name);
            values.put("description", description);
            values.put("owner_id", ownerId);
            id = db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("GroupHelper", String.format("Insert failed: %s", e.getMessage()));;
            return false;
        }

        // Jangan lupa, owner juga termasuk member group
        GroupMembersHelper gmh = new GroupMembersHelper(context);
        gmh.open();
        gmh.insert((int) id, ownerId);
        gmh.close();

        Log.d("GroupHelper", String.format("Data inserted: %s", name));
        return true;
    }

    /**
     * Fungsi untuk menghapus data group
     * @param id ID dari group yang akan dihapus
     * @return boolean apakah data berhasil dihapus atau tidak
     */
    public Boolean delete(Integer id) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        try {
            db.delete(TABLE_NAME, "id = ?", new String[]{id.toString()});
        } catch (Exception e) {
            Log.e("GroupHelper", String.format("Delete failed: %s", e.getMessage()));;
            return false;
        }

        Log.d("GroupHelper", String.format("Data deleted: %s", id));
        return true;
    }

    /**
     * Fungsi untuk mengubah data group
     * @param id ID dari group yang akan diubah
     * @param changeColumnName Name dari column yang akan diubah
     * @param changeColumnValue Value yang akan diubah
     * @return Boolean apakah data berhasil diubah atau tidak
     */
    public Boolean update(int id, String changeColumnName, String changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(changeColumnName, changeColumnValue);
            db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("GroupHelper", String.format("Update failed: %s", e.getMessage()));;
            return false;
        }

        Log.d("GroupHelper", String.format("Data updated: %s", id));
        return true;
    }

    public Boolean update(int id, String changeColumnName, int changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(changeColumnName, changeColumnValue);
            db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("GroupHelper", String.format("Update failed: %s", e.getMessage()));;
            return false;
        }

        Log.d("GroupHelper", String.format("Data updated: %s", id));
        return true;
    }

    /**
     * Fungsi untuk mengambil 1 data group berdasarkan id group
     * @param id ID dari group yang akan diambil
     * @return Group berisi data group
     */
    public Group getGroupById(int id) {
        Cursor cursor = dbh.getData("id", id, TABLE_NAME);
        cursor.moveToFirst();
        int idColumnIndex = cursor.getColumnIndex("id"),
                nameColumnIndex = cursor.getColumnIndex("name"),
                descriptionColumnIndex = cursor.getColumnIndex("description"),
                ownerIdColumnIndex = cursor.getColumnIndex("owner_id");

        return new Group(
                cursor.getInt(idColumnIndex),
                cursor.getString(nameColumnIndex),
                cursor.getString(descriptionColumnIndex),
                cursor.getInt(ownerIdColumnIndex)
        );
    }

    /**
     * WARNING: Fungsi ini masih salah. Akan diubah di versi selanjutnya
     * Fungsi untuk mengambil data group berdasarkan id
     * @param id ID dari group yang akan diambil
     * @return Vector berisi data group
     */
    public Vector <Group> getAllGroupsByUserId(int id) {
        Cursor cursor = dbh.getData("owner_id", id, TABLE_NAME);
        Vector <Group> groupVector = new Vector<Group>();

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idColumnIndex = cursor.getColumnIndex("id"),
                    nameColumnIndex = cursor.getColumnIndex("name"),
                    descriptionColumnIndex = cursor.getColumnIndex("description"),
                    ownerIdColumnIndex = cursor.getColumnIndex("owner_id");

            groupVector.add(
                    new Group(
                            cursor.getInt(idColumnIndex),
                            cursor.getString(nameColumnIndex),
                            cursor.getString(descriptionColumnIndex),
                            cursor.getInt(ownerIdColumnIndex)
                    )
            );
        }

        return groupVector;
    }

    /**
     * Fungsi untuk mengambil semua data group
     * @return
     */
    public Vector <Group> getAllData() {
        Vector <Group> groupVector = new Vector<>();
        Cursor cursor = dbh.getDataWithQuery("SELECT * FROM groups");

        if(cursor.getCount() == 0)
            return null;

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int idColumnIndex = cursor.getColumnIndex("id"),
                    nameColumnIndex = cursor.getColumnIndex("name"),
                    descriptionColumnIndex = cursor.getColumnIndex("description"),
                    ownerIdColumnIndex = cursor.getColumnIndex("owner_id");

            groupVector.add(
                    new Group(
                            cursor.getInt(idColumnIndex),
                            cursor.getString(nameColumnIndex),
                            cursor.getString(descriptionColumnIndex),
                            cursor.getInt(ownerIdColumnIndex)
                    )
            );
        }

        return groupVector;
    }

}
