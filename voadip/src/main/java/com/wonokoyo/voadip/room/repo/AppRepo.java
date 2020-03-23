package com.wonokoyo.voadip.room.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.VoadipWithItem;
import com.wonokoyo.voadip.room.AppDatabase;
import com.wonokoyo.voadip.room.dao.AppDao;

import java.util.List;

public class AppRepo {
    private AppDao mAppDao;

    public AppRepo(Application application) {
        mAppDao = AppDatabase.getInstance(application).appDao();
    }

    public void saveAllVoadipAndDetail(final List<Voadip> voadips) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (Voadip v : voadips) {
                    Long id = mAppDao.saveVoadip(v);

                    for (ItemVoadip iv : v.getItemVoadips()) {
                        iv.setId_voadip(id.intValue());
                    }

                    mAppDao.saveItemVoadip(v.getItemVoadips());
                }
                return null;
            }
        }.execute();
    }

    public void updateVoadipAndDetail(final Voadip voadip) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mAppDao.updateVoadip(voadip);
                mAppDao.updateItemVoadip(voadip.getItemVoadips());
                return null;
            }
        }.execute();
    }

    public LiveData<List<VoadipWithItem>> getAllVoadip() {
        return mAppDao.loadVoadip();
    }

    public LiveData<VoadipWithItem> getVoadipByOp(String op) {
        return mAppDao.loadVoadipByOp(op);
    }
}
