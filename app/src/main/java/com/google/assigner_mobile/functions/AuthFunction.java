package com.google.assigner_mobile.functions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.assigner_mobile.activities.HomeActivity;

public class AuthFunction {

    /**
     * Fungsi untuk menyimpan authID ke SharedPreferences
     * @param context
     * @param authID
     */

    public void setAuthID(Context context, Integer authID) {
        SharedPreferences preference = context.getSharedPreferences("authID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt("authID", authID);
        editor.apply();
    }

    /**
     * Fungsi untuk mengambil authID dari SharedPreferences
     * @param context
     * @return authID
     */

    public Integer getAuthID(Context context) {
        SharedPreferences preference = context.getSharedPreferences("authID", Context.MODE_PRIVATE);
        return preference.getInt("authID", -1);
    }

    /**
     * Fungsi untuk mengecek apakah user sudah login
     * @param context
     */
    public void authCheck(Context context) {
        SharedPreferences preference = context.getSharedPreferences("authID", Context.MODE_PRIVATE);
        int authID = preference.getInt("authID", -1);

        if(authID == -1)
            return;

        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

}
