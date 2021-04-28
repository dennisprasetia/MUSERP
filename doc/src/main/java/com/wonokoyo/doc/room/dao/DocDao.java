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
import com.wonokoyo.doc.model.DocWeighs;
import com.wonokoyo.doc.model.DocWithLoc;
import com.wonokoyo.doc.model.Weigh;

import java.util.List;

@Dao
public interface DocDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Doc doc);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Doc> docs);

    @Transaction
    @Update
    void update(Doc doc);

    @Transaction
    @Update
    void update(List<Doc> doc);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert_weigh(Weigh weigh);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert_weigh(List<Weigh> weighs);

    @Transaction
    @Delete
    void delete(Doc doc);

    @Transaction
    @Query("SELECT * FROM doc WHERE id_spj = :id")
    LiveData<Doc> getDocById(String id);

    @Transaction
    @Query("SELECT * FROM doc WHERE stat_entry = 1 AND stat_upload <> 1")
    LiveData<List<DocWeighs>> loadAllDocWithWeigh();

    @Transaction
    @Query("SELECT * FROM doc WHERE stat_entry <> 1 AND stat_upload <> 1")
    LiveData<List<Doc>> loadAllDoc();

    @Transaction
    @Query("SELECT * FROM doc WHERE tanggalDoc > :date")
    LiveData<List<DocDetail>> loadAllDocWithDetailByDate(String date);

    @Transaction
    @Query("SELECT * FROM doc WHERE tanggalDoc > :date")
    LiveData<List<Doc>> loadAllDocByDate(String date);

    @Transaction
    @Query("SELECT * FROM doc WHERE noOpDoc = :op AND stat_recieve <> 1")
    LiveData<DocDetail> loadDocWithDetailByOp(String op);

    @Transaction
    @Query("SELECT * FROM doc WHERE idScan = :op AND stat_recieve <> 1")
    LiveData<Doc> loadDocByOp(String op);

    @Transaction
    @Query("SELECT * FROM doc WHERE idScan = :op AND stat_recieve <> 1")
    LiveData<DocWeighs> loadDocWeighsByOp(String op);

    @Transaction
    @Query("SELECT * FROM doc WHERE noreg = :noreg")
    LiveData<DocWithLoc> loadDocWithLocByNoreg(String noreg);

    @Transaction
    @Query("SELECT * FROM doc WHERE noreg = :noreg")
    LiveData<Doc> loadDocByNoreg(String noreg);
}