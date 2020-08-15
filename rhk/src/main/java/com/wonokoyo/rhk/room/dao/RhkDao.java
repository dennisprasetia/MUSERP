package com.wonokoyo.rhk.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;

import com.wonokoyo.rhk.model.Attachment;
import com.wonokoyo.rhk.model.Doc;
import com.wonokoyo.rhk.model.FeedAndDead;
import com.wonokoyo.rhk.model.Necropsy;
import com.wonokoyo.rhk.model.Screen;
import com.wonokoyo.rhk.model.Solution;

import java.util.List;

@Dao
public interface RhkDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Attachment attachment);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiAttachment(List<Attachment> attachments);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Doc doc);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiDoc(List<Doc> docs);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(FeedAndDead feedAndDead);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiFad(List<FeedAndDead> feedAndDeads);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Necropsy necropsy);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiNecropsy(List<Necropsy> necropsies);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Screen screen);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiScreen(List<Screen> screens);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Solution solution);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiSolution(List<Solution> solutions);
}
