package com.wonokoyo.doc.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.ItemVoadip;
import com.wonokoyo.doc.model.Loc;
import com.wonokoyo.doc.model.Voadip;
import com.wonokoyo.doc.room.dao.DocDao;
import com.wonokoyo.doc.room.dao.LocDao;
import com.wonokoyo.doc.room.dao.VoadipDao;

@Database(entities = {Doc.class, Voadip.class, ItemVoadip.class, Loc.class}, version = 5, exportSchema = false)
public abstract class DocDatabase extends RoomDatabase {

    private static volatile DocDatabase INSTANCE;

    public abstract DocDao docDao();

    public abstract VoadipDao voadipDao();

    public abstract LocDao locDao();

    public static DocDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (DocDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DocDatabase.class,
                        "muserp")
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
