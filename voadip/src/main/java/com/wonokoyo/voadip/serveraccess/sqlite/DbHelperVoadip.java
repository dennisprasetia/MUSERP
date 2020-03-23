package com.wonokoyo.voadip.serveraccess.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.Voadip;

import java.util.List;

public class DbHelperVoadip extends SQLiteOpenHelper {

    public DbHelperVoadip(Context context) {
        super(context, "testing", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_voadip = "CREATE TABLE voadip (id_voadip integer, no_op text, noreg text, mitra text, " +
                "alamat_mitra text, no_sj text, penerima text, url_sj text, url_sign text, supplier text, " +
                "tgl_kirim text, tgl_terima text, status integer)";
        db.execSQL(table_voadip);

        String table_item_voadip = "CREATE TABLE item_voadip (id_item integer, id_voadip integer, nama text, kemasan text, " +
                "jumlah integer, keterangan text)";
        db.execSQL(table_item_voadip);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private int getIdByTable(String table) {
        SQLiteDatabase database = this.getReadableDatabase();

        String sql = "SELECT * FROM " + table;
        Cursor cursor = database.rawQuery(sql, null);

        return cursor.getCount() + 1;
    }

    public void insertVoadip(List<Voadip> listVoadip) {
        SQLiteDatabase database = this.getWritableDatabase();

        for (int i = 0; i < listVoadip.size(); i++) {
            Voadip voadip = listVoadip.get(i);

            int id_voadip = getIdByTable("voadip");

            ContentValues values = new ContentValues();
            values.put("id_voadip", id_voadip);
            values.put("no_op", voadip.getNoOp());
            values.put("noreg", voadip.getNoreg());
            values.put("mitra", voadip.getMitra());
            values.put("alamat_mitra", voadip.getAlamat());
            values.put("supplier", voadip.getSupplier());
            values.put("tgl_kirim", voadip.getTglKirim());
            values.put("status", 0);

            database.insert("voadip", null, values);

            insertItemVoadip(id_voadip, voadip.getItemVoadips());
        }
    }

    public void updateVoadip(Voadip voadip, int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("no_sj", voadip.getNoSj());
        values.put("penerima", voadip.getPenerima());
        values.put("url_sj", voadip.getUrl());
        values.put("url_sign", voadip.getUrlSign());
        values.put("tgl_terima", voadip.getTglTerima());

        database.update("voadip", values, "id", new String[]{String.valueOf(id)});
    }

    private void updateItemVoadip(int id_voadip, List<ItemVoadip> list) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
    }

    public void insertItemVoadip(int id_voadip, List<ItemVoadip> listItem) {
        SQLiteDatabase database = this.getWritableDatabase();

        for (int i = 0; i < listItem.size(); i++) {
            ItemVoadip itemVoadip = listItem.get(i);

            int id_item = getIdByTable("item_voadip");

            ContentValues values = new ContentValues();
            values.put("id_item", id_item);
            values.put("id_voadip", id_voadip);
            values.put("nama", itemVoadip.getName());
            values.put("kemasan", itemVoadip.getPacking());
            values.put("jumlah", itemVoadip.getAmmount());
            values.put("keterangan", itemVoadip.getKeterangan());

            database.insert("item_voadip", null, values);
        }
    }

    public int getIdVoadipByOp(String op) {
        SQLiteDatabase database = this.getReadableDatabase();

        String sql = "SELECT * FROM voadip WHERE no_op = '" + op + "'";
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToLast();

        return cursor.getInt(cursor.getColumnIndex("id_voadip"));
    }

    public Cursor getDataByTglKirim(String tglKirim) {
        SQLiteDatabase database = this.getReadableDatabase();

        String sql = "SELECT * FROM voadip WHERE tgl_kirim = '" + tglKirim + "'";
        Cursor cursor = database.rawQuery(sql, null);

        return cursor;
    }

    public Cursor getDataByOp(String no_op) {
        SQLiteDatabase database = this.getReadableDatabase();

        String sql = "SELECT * FROM voadip WHERE no_op = '" + no_op + "'";
        Cursor cursor = database.rawQuery(sql, null);

        return cursor;
    }

    public Cursor getItemByVoadip(String id_voadip) {
        SQLiteDatabase database = this.getReadableDatabase();

        String sql = "SELECT * FROM item_voadip WHERE id_voadip = '" + id_voadip + "'";
        Cursor cursor = database.rawQuery(sql, null);

        return cursor;
    }
}
