package com.google.assigner_mobile.functions;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.assigner_mobile.R;

import java.util.Random;

public class GlobalFunction {

    Random rand = new Random();

    /**
     * Fungsi untuk mengecek apakah ada injection pada string
     * @param str String yang akan di cek
     * @return boolean true jika ada injection, false jika tidak
     */
    public Boolean hasInjection(String str) {
        return (str.contains("'") || str.contains("\"") || str.contains(";") || str.contains("--"));
    }

    public Context safeGetContext(Context context) {
        return context == null ? null : context.getApplicationContext();
    }

    /**
     * Fungsi untuk mengambil random integer dengan range
     * @param min batas bawah
     * @param max batas atas
     * @return
     */
    public int randIntWithRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Fungsi untuk membuka fragment pada activity
     * @param fragment Fragment yang akan dibuka
     * @param context Context dari activity
     * @return boolean true jika berhasil, false jika gagal
     */
    public boolean openFragment(Fragment fragment, Context context) {
        if(fragment == null)
            return false;

        ((AppCompatActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFrameLayout, fragment)
                .commit();

        return true;
    }

    public boolean switchActivity(Context context, Class<?> cls) {
        if(context == null || cls == null)
            return false;

        context.startActivity(new Intent(context, cls));
        return true;
    }
}
