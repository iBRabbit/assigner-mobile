package com.google.assigner_mobile.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public boolean invite(int groupId, int userId) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put("group_id", groupId);
            values.put("user_id", userId);

            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("InvitationHelper", "Error inserting invitation");
            return false;
        }

        Log.d("InvitationHelper", "Invitation inserted");
        return true;
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
