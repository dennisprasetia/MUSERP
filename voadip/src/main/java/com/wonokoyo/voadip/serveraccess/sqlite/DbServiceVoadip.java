package com.wonokoyo.voadip.serveraccess.sqlite;

import android.content.Context;
import android.database.Cursor;

import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.Voadip;

import java.util.ArrayList;
import java.util.List;

public class DbServiceVoadip {
    private Context mContext;
    private DbHelperVoadip dbHelperVoadip;

    public DbServiceVoadip(Context context) {
        mContext = context;
        dbHelperVoadip = new DbHelperVoadip(context);
    }

    public List<Voadip> getAllVoadipByTglKirim() {
        List<Voadip> voadips = new ArrayList<>();

        return voadips;
    }

    public void saveVoadipToSqlite(List<Voadip> voadips) {
        dbHelperVoadip.insertVoadip(voadips);
    }

    public Voadip getVoadipByScanOp(String no_op) {
        Voadip voadip = new Voadip();

        Cursor cursor = dbHelperVoadip.getDataByOp(no_op);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();

            String id = cursor.getString(cursor.getColumnIndex("id_voadip"));

            voadip.setNoOp(cursor.getString(cursor.getColumnIndex("no_op")));
            voadip.setNoreg(cursor.getString(cursor.getColumnIndex("no_op")));
            voadip.setMitra(cursor.getString(cursor.getColumnIndex("no_op")));
            voadip.setAlamat(cursor.getString(cursor.getColumnIndex("no_op")));
            voadip.setSupplier(cursor.getString(cursor.getColumnIndex("no_op")));
            voadip.setTglKirim(cursor.getString(cursor.getColumnIndex("no_op")));

            cursor = dbHelperVoadip.getItemByVoadip(id);
            if (cursor.getCount() > 0) {
                List<ItemVoadip> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    ItemVoadip item = new ItemVoadip();
                    item.setName(cursor.getString(cursor.getColumnIndex("nama")));
                    item.setPacking(cursor.getString(cursor.getColumnIndex("kemasan")));
                    item.setAmmount(cursor.getInt(cursor.getColumnIndex("jumah")));
                }
            }

            return voadip;
        } else {
            return null;
        }
    }

    public void updateVoadipSqlite(Voadip voadip) {
        int id = dbHelperVoadip.getIdVoadipByOp(voadip.getNoOp());

        dbHelperVoadip.updateVoadip(voadip, id);
    }
}
