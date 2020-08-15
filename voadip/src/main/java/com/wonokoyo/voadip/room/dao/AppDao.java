package com.wonokoyo.voadip.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.VoadipWithItem;

import java.util.List;

@Dao
public interface AppDao {
    // VOADIP
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long saveVoadip(Voadip voadip);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveVoadip(List<Voadip> voadips);

    @Transaction
    @Update
    void updateVoadip(Voadip voadip);

    @Transaction
    @Query("SELECT * FROM voadip WHERE noOp = :op")
    LiveData<Voadip> getVoadipByOp(String op);

    // ITEM
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long saveItemVoadip(ItemVoadip itemVoadip);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveItemVoadip(List<ItemVoadip> itemVoadips);

    @Transaction
    @Update
    void updateItemVoadip(List<ItemVoadip> itemVoadips);

    @Transaction
    @Query("SELECT * FROM item")
    LiveData<List<ItemVoadip>> loadAllItem();

    // AMBIL VOADIP TANPA ITEM
    @Transaction
    @Query("SELECT * FROM voadip WHERE tglTerima IS NULL")
    LiveData<List<Voadip>> loadVoadip();

    // AMBIL VOADIP DENGAN ITEM
    @Transaction
    @Query("SELECT * FROM voadip WHERE tglTerima IS NULL")
    LiveData<List<VoadipWithItem>> loadVoadipWithItem();

    @Transaction
    @Query("SELECT * FROM voadip WHERE tglTerima IS NOT NULL AND upload IS NULL")
    LiveData<List<VoadipWithItem>> getVoadipWithItem();

    @Transaction
    @Query("SELECT * FROM voadip WHERE noOp = :op")
    LiveData<VoadipWithItem> loadVoadipByOp(String op);
}
