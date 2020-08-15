package com.wonokoyo.rhk.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wonokoyo.rhk.model.Attachment;
import com.wonokoyo.rhk.model.Doc;
import com.wonokoyo.rhk.model.FeedAndDead;
import com.wonokoyo.rhk.model.Necropsy;
import com.wonokoyo.rhk.model.Screen;
import com.wonokoyo.rhk.model.Solution;

import java.util.List;

public class RhkViewModel extends ViewModel {
    private MutableLiveData<List<Attachment>> attLiveData;
    private MutableLiveData<Doc> docLiveData;
    private MutableLiveData<FeedAndDead> fadLiveData;
    private MutableLiveData<List<Necropsy>> necLiveData;
    private MutableLiveData<List<Screen>> scrLiveData;
    private MutableLiveData<List<Solution>> solLiveData;

    private MutableLiveData<String> event;

    public LiveData<List<Attachment>> getAttLiveData() {
        return attLiveData;
    }

    public LiveData<Doc> getDocLiveData() {
        return docLiveData;
    }

    public LiveData<FeedAndDead> getFadLiveData() {
        return fadLiveData;
    }

    public LiveData<List<Necropsy>> getNecLiveData() {
        return necLiveData;
    }

    public LiveData<List<Screen>> getScrLiveData() {
        return scrLiveData;
    }

    public LiveData<List<Solution>> getSolLiveData() {
        return solLiveData;
    }

    public LiveData<String> getEvent() {
        return event;
    }
}
