package com.wonokoyo.pakan.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.wonokoyo.pakan.model.Pakan;

import java.util.List;

public class PakanRepo {
    private PakanDao dao;

    public PakanRepo(Application application) {
        dao = PakanDatabase.getInstance(application).pakanDao();
    }

    public void savePakan(final Pakan pakan) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.insert(pakan);
                return null;
            }
        }.execute();
    }

    public void savePakan(final List<Pakan> pakans) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.insert(pakans);
                return null;
            }
        }.execute();
    }

    public void updatePakan(final Pakan pakan) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.update(pakan);
                return null;
            }
        }.execute();
    }

    public void updatePakan(final List<Pakan> pakans) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.update(pakans);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Pakan>> getAllPakanTimbang() {
        return dao.getAllPakanTimbang();
    }

    public LiveData<List<Pakan>> getPakansBySj(String no_sj) {
        return dao.getPakanTimbangBySj(no_sj);
    }

    public LiveData<Integer> getCountData() {
        return dao.countDataSj();
    }
}
