package com.example.firebasedemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by DSK02 on 9/25/2015.
 */
public class Utils {
    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
