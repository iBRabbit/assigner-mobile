package com.google.assigner_mobile.functions;

import android.content.Context;

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
}
