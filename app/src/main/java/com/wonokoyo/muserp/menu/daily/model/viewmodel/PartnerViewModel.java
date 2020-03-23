package com.wonokoyo.muserp.menu.daily.model.viewmodel;

import androidx.lifecycle.ViewModel;

import com.wonokoyo.muserp.menu.daily.model.Doc;
import com.wonokoyo.muserp.menu.daily.model.repository.EntryRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartnerViewModel extends ViewModel {
    private List<Doc> docList;
    private List<String> docMitraList;
    private List<String> docNoregList;
    private EntryRepository entryRepository;

    public void init() {
        if (docList != null)
            return;

        docList = new ArrayList<>();
        docMitraList = new ArrayList<>();
        docMitraList.add("Pilih Mitra");

        entryRepository = EntryRepository.getInstance();
    }

    public void populateDocList() {
        Callback<List<Doc>> listener = new Callback<List<Doc>>() {
            @Override
            public void onResponse(Call<List<Doc>> call, Response<List<Doc>> response) {
                if (response.isSuccessful())
                    docList = response.body();
            }

            @Override
            public void onFailure(Call<List<Doc>> call, Throwable t) {
                System.out.println("failed");
            }
        };

        entryRepository.getListDoc(listener);
    }

    public List<String> populateStringMitra() {
        for (int i = 0; i < docList.size(); i++) {
            if (!docMitraList.contains(docList.get(i).getNamaMitra())
                    && docList.get(i).getNamaMitra() != null)
                docMitraList.add(docList.get(i).getNamaMitra());
        }

        return docMitraList;
    }

    public List<String> populateStringNoreg(String mitra) {
        docNoregList = new ArrayList<>();
        docNoregList.add("Pilih Noreg");

        if (docList.size() > 1) {
            for (int i = 0; i < docList.size(); i++) {
                if (docList.get(i).getNamaMitra().equalsIgnoreCase(mitra))
                    docNoregList.add(docList.get(i).getNoreg());
            }
        }

        return docNoregList;
    }

    public Doc getDocByNoreg(String noreg) {
        for (int i = 0; i < docList.size(); i++) {
            if (docList.get(i).getNoreg().equalsIgnoreCase(noreg))
                return docList.get(i);
        }

        return null;
    }
}
