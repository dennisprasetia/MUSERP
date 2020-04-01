package com.wonokoyo.doc.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.DocDetail;
import com.wonokoyo.doc.model.DocWithLoc;

import java.util.List;

@Dao
public interface DocDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Doc doc);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Doc> docs);

    @Transaction
    @Update
    void update(Doc doc);

    @Transaction
    @Delete
    void delete(Doc doc);

    @Transaction
    @Query("SELECT * FROM doc")
    LiveData<List<DocWithLoc>> loadAllDocWithDetail();

    @Transaction
    @Query("SELECT * FROM doc WHERE stat_track <> 1 OR stat_entry <> 1")
    LiveData<List<Doc>> loadAllDoc();

    @Transaction
    @Query("SELECT * FROM doc WHERE tanggalDoc > :date")
    LiveData<List<DocDetail>> loadAllDocWithDetailByDate(String date);

    @Transaction
    @Query("SELECT * FROM doc WHERE tanggalDoc > :date")
    LiveData<List<Doc>> loadAllDocByDate(String date);

    @Transaction
    @Query("SELECT * FROM doc WHERE noOpDoc = :op")
    LiveData<DocDetail> loadDocWithDetailByOp(String op);

    @Transaction
    @Query("SELECT * FROM doc WHERE noOpDoc = :op")
    LiveData<Doc> loadDocByOp(String op);

    @Transaction
    @Query("SELECT * FROM doc WHERE noreg = :noreg")
    LiveData<DocWithLoc> loadDocWithLocByNoreg(String noreg);

    @Transaction
    @Query("SELECT * FROM doc WHERE noreg = :noreg")
    LiveData<Doc> loadDocByNoreg(String noreg);
}
