package com.wonokoyo.muserp.menu.daily.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wonokoyo.muserp.menu.daily.model.Attachment;

import java.util.ArrayList;
import java.util.List;

public class AttachmentViewModel extends ViewModel {
    private MutableLiveData<List<Attachment>> mutableLiveData;
    private List<Attachment> listAttachment;

    public void init() {
        if (mutableLiveData != null)
            return;

        mutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Attachment>> getListAttachment() {
        return mutableLiveData;
    }

    public void populateAttachment(int id, String type, String uri) {
        Attachment attachment = new Attachment();
        attachment.setId(id);
        attachment.setType(type);
        attachment.setFileuri(uri);

        if (listAttachment != null) {
            listAttachment.add(attachment);
        } else {
            listAttachment = new ArrayList<>();
            listAttachment.add(attachment);
        }

        mutableLiveData.setValue(listAttachment);
    }

    public void clear() {
        listAttachment.clear();
        listAttachment = null;

        mutableLiveData.setValue(listAttachment);
        mutableLiveData = null;
    }
}
