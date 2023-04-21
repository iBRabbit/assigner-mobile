package com.google.assigner_mobile.functions;

import android.content.Context;

public class GlobalFunction {

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
}
