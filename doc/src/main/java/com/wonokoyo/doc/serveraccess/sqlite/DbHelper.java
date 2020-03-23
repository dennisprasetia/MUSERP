package com.wonokoyo.doc.serveraccess.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.ItemVoadip;
import com.wonokoyo.doc.model.Voadip;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "testing", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_doc = "CREATE TABLE doc (id_doc integer, no_op_doc text, mitra text, noreg text, " +
                "kandang integer, alamat text, jumlah_box integer, nopol text, sopir text, kedatangan text)";
        db.execSQL(table_doc);

        String table_voadip = "CREATE TABLE voadip (id_voadip integer, id_doc integer, no_op_voadip text, " +
                "supplier text, tanggal_kirim text)";
        db.execSQL(table_voadip);

        String table_item_voadip = "CREATE TABLE item_voadip (id_item integer, id_voadip integer, nama text, packing text, " +
                "ammount integer)";
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

    public void insertDoc(ArrayList<Doc> listDoc) {
        SQLiteDatabase database = this.getWritableDatabase();

        for (int i = 0; i < listDoc.size(); i++) {
            Doc doc = listDoc.get(i);
            int id_doc = getIdByTable("doc");

            ContentValues values = new ContentValues();
            values.put("id_doc", id_doc);
            values.put("no_op_doc", doc.getNoOpDoc());
            values.put("mitra", doc.getMitra());
            values.put("noreg", doc.getNoreg());
            values.put("kandang", doc.getKandang());
            values.put("alamat", doc.getAlamat());
            values.put("jumlah_box", doc.getJumlahBox());
            values.put("nopol", "N 1256 FG");
            values.put("sopir", "SUPRAYITNO");
            values.put("kedatangan", (i + 1) + " November 2019 2" + i + ":00");

            database.insert("doc", null, values);

            insertVoadip(id_doc, doc.getVoadips());
        }
    }

    public void insertVoadip(int id_doc, List<Voadip> listVoadip) {
        SQLiteDatabase database = this.getWritableDatabase();

        for (int i = 0; i < listVoadip.size(); i++) {
            Voadip voadip = listVoadip.get(i);

            int id_voadip = getIdByTable("voadip");

            ContentValues values = new ContentValues();
            values.put("id_voadip", id_voadip);
            values.put("id_doc", id_doc);
            values.put("no_op_voadip", voadip.getNoOp());
            values.put("supplier", voadip.getSupplier());
            values.put("tanggal_kirim", voadip.getTglKirim());

            database.insert("voadip", null, values);

            insertItemVoadip(id_voadip, voadip.getItemVoadips());
        }
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
            values.put("packing", itemVoadip.getPacking());
            values.put("ammount", itemVoadip.getAmmount());

            database.insert("item_voadip", null, values);
        }
    }

    public Cursor getData(String table) {
        SQLiteDatabase database = this.getReadableDatabase();

        String sql = "SELECT * FROM " + table;
        Cursor cursor = database.rawQuery(sql, null);

        return cursor;
    }

    public Cursor getDataByNoreg(String table, String no_op) {
        SQLiteDatabase database = this.getReadableDatabase();

        String sql = "SELECT * FROM " + table + " WHERE no_op_doc = '" + no_op + "'";
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
