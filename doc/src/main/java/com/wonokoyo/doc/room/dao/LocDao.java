package com.wonokoyo.doc.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wonokoyo.doc.model.Loc;

import java.util.List;

@Dao
public interface LocDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Loc loc);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Loc> locs);

    @Transaction
    @Update
    void update(Loc loc);

    @Transaction
    @Delete
    void delete(Loc loc);
}
