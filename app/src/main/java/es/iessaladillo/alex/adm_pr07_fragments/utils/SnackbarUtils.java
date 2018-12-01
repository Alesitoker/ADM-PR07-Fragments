package es.iessaladillo.alex.adm_pr07_fragments.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtils {
    private SnackbarUtils(){}

    public static void snackbar(View view, String mesage) {
        Snackbar.make(view, mesage, Snackbar.LENGTH_LONG).show();
    }
}
