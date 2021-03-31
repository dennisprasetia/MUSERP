package com.wonokoyo.pakan.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.wonokoyo.pakan.model.Pakan;

@Database(entities = {Pakan.class}, version = 1, exportSchema = false)
public abstract class PakanDatabase extends RoomDatabase {
    private static volatile PakanDatabase INSTANCE;

    public abstract PakanDao pakanDao();

    public static PakanDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (PakanDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PakanDatabase.class,
                        "muserp")
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }

        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
