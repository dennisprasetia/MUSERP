package com.wonokoyo.doc.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wonokoyo.doc.model.ItemVoadip;
import com.wonokoyo.doc.model.Voadip;
import com.wonokoyo.doc.model.VoadipDetail;

import java.util.List;

@Dao
public interface VoadipDao {
    // VOADIP
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Voadip voadip);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Voadip> voadips);

    @Transaction
    @Query("SELECT * FROM voadip_doc WHERE id_doc = :id_doc")
    LiveData<List<VoadipDetail>> loadVoadipWithDetailByDoc(String id_doc);

    // ITEM VOADIP
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertItem(ItemVoadip itemVoadip);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(List<ItemVoadip> itemVoadips);
}
