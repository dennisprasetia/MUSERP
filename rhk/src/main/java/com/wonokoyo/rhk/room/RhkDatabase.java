package com.wonokoyo.rhk.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wonokoyo.rhk.model.Attachment;
import com.wonokoyo.rhk.model.Doc;
import com.wonokoyo.rhk.model.FeedAndDead;
import com.wonokoyo.rhk.model.Necropsy;
import com.wonokoyo.rhk.model.Screen;
import com.wonokoyo.rhk.model.Solution;
import com.wonokoyo.rhk.room.dao.RhkDao;

@Database(entities = {Attachment.class, Doc.class, FeedAndDead.class, Necropsy.class, Screen.class, Solution.class},
        version =  1, exportSchema =  false)
public abstract class RhkDatabase extends RoomDatabase {
    private static volatile RhkDatabase INSTANCE;

    public abstract RhkDao rhkDao();
}
