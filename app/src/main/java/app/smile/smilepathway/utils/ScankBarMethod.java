package app.smile.smilepathway.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by user102 on 4/2/18.
 */

public class ScankBarMethod {

    public static void showSnack(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }
}
