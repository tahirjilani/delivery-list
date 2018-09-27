package com.tj.deliverylist.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.tj.deliverylist.R;

public class Utils {


    public static void retryAlert(Context context, String title, String msg, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(R.string.retry, listener)
        .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        AlertDialog alert = builder.create();
        alert.show();
    }
}
