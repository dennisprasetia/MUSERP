package com.wonokoyo.doc.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.wonokoyo.doc.R;

public class CustomDialog {

    public AlertDialog.Builder alertScanGagal(Context context, final alertDialogCallBack adc) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
        ad.setTitle("Alternative");
        ad.setMessage("Coba input nomor op ?");
        ad.setCancelable(false);

        ad.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adc.action(true);
                dialogInterface.cancel();
            }
        });

        ad.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adc.action(false);
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = ad.create();
        alert.show();

        return ad;
    }

    public interface alertDialogCallBack {
        void action(Boolean val);
    }
}
