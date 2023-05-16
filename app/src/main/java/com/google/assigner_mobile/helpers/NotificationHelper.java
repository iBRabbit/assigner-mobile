package com.google.assigner_mobile.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.assigner_mobile.models.AppNotification;


import java.time.LocalDate;
import java.util.Vector;

public class NotificationHelper {

    private static final String TABLE_NAME = "notifications";
    private DatabaseHelper dbh;
    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
        dbh = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dbh = new DatabaseHelper(context);
        dbh.getReadableDatabase();
        Log.i("NotificationHelper", "Database opened");
    }

    public void close() throws SQLException {
        dbh.close();
        Log.i("NotificationHelper", "Database closed");
    }

    /**
     * Fungsi untuk memasukkan data notifikasi ke database
     * @param userId ID dari user yang akan diberi notifikasi
     * @param message Pesan notifikasi
     * @param type Tipe notifikasi
     * @param createdAtEpochDay Waktu notifikasi dibuat
     */
    public Boolean insert(Integer userId, String title, String message, Integer type, LocalDate createdAtEpochDay) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put("user_id", userId);
            values.put("title", title);
            values.put("message", message);
            values.put("type", type);
            values.put("created_at_epoch_day", createdAtEpochDay.toEpochDay());

            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("NotificationHelper", String.format("Insert failed: %s", e.getMessage()));
            ;
            return false;
        }

        return true;
    }

    /**
     * Fungsi untuk menghapus data notifikasi dari database
     * @param id ID dari notifikasi yang akan dihapus
     */
    public Boolean delete(Integer id) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        try {
            db.delete(TABLE_NAME, "id = ?", new String[]{id.toString()});
        } catch (Exception e) {
            Log.e("NotificationHelper", String.format("Delete failed: %s", e.getMessage()));
            ;
            return false;
        }
        return true;
    }

    /**
     * Fungsi untuk mengambil semua notifikasi dari database
     * @param userId ID dari user yang akan diberi notifikasi
     * @return Vector dari semua notifikasi
     */
    public Vector<AppNotification> getAllNotificationsByUserId(Integer userId) {
        Cursor cursor = dbh.getData("user_id", userId, TABLE_NAME);
        Vector<AppNotification> notificationVector = new Vector<>();

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int idColumnIndex = cursor.getColumnIndex("id"),        titleColumnIndex = cursor.getColumnIndex("title"),
                userIdColumnIndex = cursor.getColumnIndex("user_id"),
                messageColumnIndex = cursor.getColumnIndex("message"),
                typeColumnIndex = cursor.getColumnIndex("type"),
                createdAtEpochDayColumnIndex = cursor.getColumnIndex("created_at_epoch_day");

            LocalDate createdAt = LocalDate.ofEpochDay(cursor.getLong(createdAtEpochDayColumnIndex));

            notificationVector.add(
                    new AppNotification(
                            cursor.getInt(idColumnIndex),
                            cursor.getInt(userIdColumnIndex),
                            cursor.getString(titleColumnIndex),
                            cursor.getString(messageColumnIndex),
                            cursor.getInt(typeColumnIndex),
                            createdAt
                    )
            );

        }

        notificationVector = sortNotificationsVectorByCreatedAt(notificationVector);

        return notificationVector;
    }

    public Vector<AppNotification> sortNotificationsVectorByCreatedAt(Vector<AppNotification> notifications) {
        // sort by the newest
        notifications.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        return notifications;
    }

}
