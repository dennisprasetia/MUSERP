package com.wonokoyo.doc.room.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.DocWeighs;
import com.wonokoyo.doc.model.DocWithLoc;
import com.wonokoyo.doc.model.Loc;
import com.wonokoyo.doc.model.Weigh;
import com.wonokoyo.doc.room.DocDatabase;
import com.wonokoyo.doc.room.dao.DocDao;
import com.wonokoyo.doc.room.dao.LocDao;
import com.wonokoyo.doc.room.dao.VoadipDao;

import java.util.List;

public class DocRepo {
    private DocDao docDao;
    private LocDao locDao;

    public DocRepo(Application application) {
        docDao = DocDatabase.getInstance(application).docDao();
        locDao = DocDatabase.getInstance(application).locDao();
    }

    public void saveAllDoc(final List<Doc> docs) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                docDao.insert(docs);
                return null;
            }
        }.execute();
    }

    public void updateDoc(final Doc doc) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                docDao.update(doc);
                return null;
            }
        }.execute();
    }

    public void updateDoc(final List<Doc> docs) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (Doc d : docs) {
                    d.setStat_upload(1);
                }

                docDao.update(docs);
                return null;
            }
        }.execute();
    }

    public void saveWeigh(final List<Weigh> weighs) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                docDao.insert_weigh(weighs);
                return null;
            }
        }.execute();
    }

    public void saveLocDoc(final Loc loc) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                locDao.insert(loc);
                return null;
            }
        }.execute();
    }

    public void updateLoc(final Loc loc) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                locDao.update(loc);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Doc>> getAllDoc() {
        return docDao.loadAllDoc();
    }

    public LiveData<List<DocWeighs>> getAllDocWithWeigh() {
        return docDao.loadAllDocWithWeigh();
    }

    public LiveData<List<Doc>> getAllDocByDate(String date) {
        return docDao.loadAllDocByDate(date);
    }

    public LiveData<Doc> getDocByOp(String op) {
        return docDao.loadDocByOp(op);
    }

    public LiveData<Doc> getDocById(String id) {
        return docDao.getDocById(id);
    }

    public LiveData<DocWithLoc> getDocByNoreg(String noreg) {
        return docDao.loadDocWithLocByNoreg(noreg);
    }
}
