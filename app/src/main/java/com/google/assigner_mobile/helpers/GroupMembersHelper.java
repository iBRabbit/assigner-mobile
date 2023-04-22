package com.google.assigner_mobile.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.database.SQLException;

public class GroupMembersHelper {
    private static final String TABLE_NAME = "group_members";
    private DatabaseHelper dbh;
    private final Context context;

    public GroupMembersHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbh = new DatabaseHelper(context);
        dbh.getReadableDatabase();
        Log.i("GroupMembersHelper", "Database opened");
    }

    public void close() throws SQLException {
        dbh.close();
        Log.i("GroupMembersHelper", "Database closed");
    }

    public Boolean insert(Integer groupId, Integer userId) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put("group_id", groupId);
            values.put("user_id", userId);
            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("GroupMembersHelper", String.format("Insert failed: %s", e.getMessage()));
            return false;
        }

        Log.d("GroupMembersHelper", String.format("Data inserted: %s", groupId));
        return true;
    }

    public Boolean delete(Integer groupId, Integer userId) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        String whereClause = "group_id = ? AND user_id = ?";
        String[] whereArgs = new String[] { groupId.toString(), userId.toString() };

        try {
            db.delete(TABLE_NAME, whereClause, whereArgs);
        } catch (Exception e) {
            Log.e("GroupMembersHelper", String.format("Delete failed: %s", e.getMessage()));;
            return false;
        }

        Log.d("GroupMembersHelper", String.format("Data deleted: %s", groupId));

        return true;
    }

    /**
     * Fungsi untuk mengubah data group di database. Ada 3 fungsi ini karena tipe data yang berbeda (Overload)
     * @param id
     * @param changeColumnName
     * @param changeColumnValue
     * @return
     */
    public Boolean update(int id, String changeColumnName, String changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        String whereClause = "id = ?";
        String[] whereArgs = new String[] { Integer.toString(id) };

        try {
            values.put(changeColumnName, changeColumnValue);
            db.update(TABLE_NAME, values, whereClause, whereArgs);
        } catch (Exception e) {
            Log.e("GroupMembersHelper", String.format("Update failed: %s", e.getMessage()));;
            return false;
        }

        Log.d("GroupMembersHelper", String.format("Data updated: %s", id));
        return true;
    }

    public Boolean update(int id, String changeColumnName, int changeColumnValue) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        String whereClause = "id = ?";
        String[] whereArgs = new String[] { Integer.toString(id) };

        try {
            values.put(changeColumnName, changeColumnValue);
            db.update(TABLE_NAME, values, whereClause, whereArgs);
        } catch (Exception e) {
            Log.e("GroupMembersHelper", String.format("Update failed: %s", e.getMessage()));;
            return false;
        }

        Log.d("GroupMembersHelper", String.format("Data updated: %s", id));
        return true;
    }

    public Integer getGroupMembersSizeByGroupId(Integer groupId) {
        Cursor cursor = dbh.getDataWithQuery(String.format("SELECT COUNT(*) FROM group_members WHERE group_id = %d", groupId));
        cursor.moveToFirst();

        Log.i("GroupMembersHelper", String.format("SELECT COUNT(*) FROM group_members WHERE group_id = %d -> %d" , groupId, cursor.getInt(0)));

        return cursor.getInt(0);
    }


}
