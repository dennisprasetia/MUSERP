package com.wonokoyo.muserp.menu.daily.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wonokoyo.muserp.menu.daily.model.Screen;

import java.util.ArrayList;
import java.util.List;

public class ScreenViewModel extends ViewModel {
    private MutableLiveData<List<Screen>> mutableLiveData;
    private List<Screen> list;

    public void init() {
        if (mutableLiveData != null)
            return;

        mutableLiveData = new MutableLiveData<>();
        list = new ArrayList<>();
    }

    public LiveData<List<Screen>> getScreenList() {
        return mutableLiveData;
    }

    public void populateScreen(int sequence, int quantity, double weight) {
        Screen screen = new Screen();
        screen.setSequence(sequence);
        screen.setQuantity(quantity);
        screen.setBbavg(weight);

        if (screen != null) {
            list.add(screen);

            mutableLiveData.setValue(list);
        }
    }

    public void clear() {
        list.clear();
        list = null;

        mutableLiveData.setValue(list);
        mutableLiveData = null;
    }
}
