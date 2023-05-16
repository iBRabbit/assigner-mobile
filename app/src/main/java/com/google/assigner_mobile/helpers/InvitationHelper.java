package com.google.assigner_mobile.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.assigner_mobile.models.Invitation;

public class InvitationHelper {
    private static final String TABLE_NAME = "invitations";
    private DatabaseHelper dbh;
    private final Context context;

    public InvitationHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbh = new DatabaseHelper(context);
        dbh.getReadableDatabase();
        Log.i("InvitationHelper", "Database opened");
    }

    public void close() {
        dbh.close();
        Log.i("InvitationHelper", "Database closed");
    }

    public Integer invite(int groupId, int userId) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = -1;

        try {
            values.put("group_id", groupId);
            values.put("user_id", userId);

           id = db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("InvitationHelper", "Error inserting invitation");
            return -1;
        }

        Log.d("InvitationHelper", "Invitation inserted");
        return (int) id;
    }

    public Invitation getInvitation(int id) {
        Cursor cursor = dbh.getDataWithQuery(String.format("SELECT * FROM %s WHERE id = %d", TABLE_NAME, id));

        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex("id"),
            groupIdIndex = cursor.getColumnIndex("group_id"),
            userIdIndex = cursor.getColumnIndex("user_id");

        int _id = cursor.getInt(idIndex),
            group_id = cursor.getInt(groupIdIndex),
            user_id = cursor.getInt(userIdIndex);

        return new Invitation(_id, group_id, user_id);
    }

    public Invitation getInvitation(int groupId, int userId) {
        Cursor cursor = dbh.getDataWithQuery(String.format("SELECT * FROM %s WHERE group_id = %d AND user_id = %d", TABLE_NAME, groupId, userId));

        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex("id"),
            groupIdIndex = cursor.getColumnIndex("group_id"),
            userIdIndex = cursor.getColumnIndex("user_id");

        int id = cursor.getInt(idIndex),
            group_id = cursor.getInt(groupIdIndex),
            user_id = cursor.getInt(userIdIndex);

        return new Invitation(id, group_id, user_id);
    }

    public boolean deleteInviteByUserId(int userId) {
        try {
            SQLiteDatabase db = dbh.getWritableDatabase();

            db.delete(TABLE_NAME, "user_id = ?", new String[]{String.valueOf(userId)});

        } catch (Exception e) {
            Log.e("InvitationHelper", "Error deleting invitation");
            return false;
        }

        Log.d("InvitationHelper", "Invitation deleted");
        return true;
    }

    public boolean delete(int id) {
        try{
            dbh.execQuery(String.format("DELETE FROM %s WHERE id = %d", TABLE_NAME, id));
        } catch (Exception e) {
            Log.e("InvitationHelper", "Error deleting invitation");
            return false;
        }

        Log.d("InvitationHelper", "Invitation deleted");
        return true;
    }

    public boolean deleteInviteByGroupId(int groupId) {
        try {
            SQLiteDatabase db = dbh.getWritableDatabase();

            db.delete(TABLE_NAME, "group_id = ?", new String[]{String.valueOf(groupId)});

        } catch (Exception e) {
            Log.e("InvitationHelper", "Error deleting invitation");
            return false;
        }

        Log.d("InvitationHelper", "Invitation deleted");
        return true;
    }

}
