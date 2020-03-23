package com.wonokoyo.doc.serveraccess.sqlite;

import android.content.Context;
import android.database.Cursor;

import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.Voadip;

import java.util.ArrayList;

public class DbService {
    private Context mContext;
    private DbHelper dbHelper;

    public DbService(Context context) {
        mContext = context;
        dbHelper = new DbHelper(context);
    }

    public Doc getDocByScanSpj(String no_op) {
        Doc doc = new Doc();

        Cursor cursor = dbHelper.getDataByNoreg("doc", no_op);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();

            doc.setNoOpDoc(cursor.getString(cursor.getColumnIndex("no_op_doc")));
            doc.setNoreg(cursor.getString(cursor.getColumnIndex("noreg")));
            doc.setMitra(cursor.getString(cursor.getColumnIndex("mitra")));
            doc.setKandang(cursor.getInt(cursor.getColumnIndex("kandang")));
            doc.setAlamat(cursor.getString(cursor.getColumnIndex("alamat")));
            doc.setJumlahBox(cursor.getInt(cursor.getColumnIndex("jumlah_box")));
            doc.setNopol(cursor.getString(cursor.getColumnIndex("nopol")));
            doc.setSopir(cursor.getString(cursor.getColumnIndex("sopir")));
            doc.setKedatangan(cursor.getString(cursor.getColumnIndex("kedatangan")));

            return doc;
        } else {
            return null;
        }
    }

    public Voadip getVoadipByScanOp(String no_op) {
        Voadip voadip = new Voadip();

        Cursor cursor = dbHelper.getDataByNoreg("voadip", no_op);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();

            return voadip;
        } else {
            return null;
        }
    }
}
