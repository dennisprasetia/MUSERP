package com.wonokoyo.pakan.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wonokoyo.pakan.model.Pakan;

import java.util.List;

@Dao
public interface PakanDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Pakan pakan);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Pakan> pakans);

    @Transaction
    @Update
    void update(Pakan pakan);

    @Transaction
    @Update
    void update(List<Pakan> pakan);

    @Transaction
    @Query("SELECT * FROM pakan WHERE stat_upload <> 1")
    LiveData<List<Pakan>> getAllPakanTimbang();

    @Transaction
    @Query("SELECT * FROM pakan WHERE no_sj = :no_sj")
    LiveData<List<Pakan>> getPakanTimbangBySj(String no_sj);

    @Transaction
    @Query("SELECT COUNT( DISTINCT no_sj ) FROM pakan WHERE stat_upload <> 1")
    LiveData<Integer> countDataSj();
}
