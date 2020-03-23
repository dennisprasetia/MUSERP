package com.wonokoyo.muserp.menu.daily.model.repository;

import com.google.gson.Gson;
import com.wonokoyo.muserp.menu.daily.model.Attachment;
import com.wonokoyo.muserp.menu.daily.model.Doc;
import com.wonokoyo.muserp.menu.daily.model.FeedAndDead;
import com.wonokoyo.muserp.menu.daily.model.Necropsy;
import com.wonokoyo.muserp.menu.daily.model.Screen;
import com.wonokoyo.muserp.menu.daily.model.Solution;
import com.wonokoyo.muserp.serveraccess.RetrofitInstance;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class EntryRepository {
    private static EntryRepository entryRepository;

    public static EntryRepository getInstance() {
        if (entryRepository == null)
            entryRepository = new EntryRepository();

        return entryRepository;
    }

    public void getListDoc(Callback<List<Doc>> listener) {
        Call<List<Doc>> call = RetrofitInstance.entryService().getListDoc();
        call.enqueue(listener);
    }

    public void saveRhk(Doc doc, List<Screen> listScreen, FeedAndDead fad,
                        List<Necropsy> listNecropsy, List<Attachment> listAttachment,
                        List<Solution> listSolution, Callback<ResponseBody> listener,
                        String idUser) {
        String mDoc = new Gson().toJson(doc);
        String mScreen = new Gson().toJson(listScreen);
        String mFad = new Gson().toJson(fad);
        String mNecropsy = new Gson().toJson(listNecropsy);
        String mAttachment = new Gson().toJson(listAttachment);
        String mSolution = new Gson().toJson(listSolution);

        Call<ResponseBody> call = RetrofitInstance.entryService().saveRhk(mDoc, mScreen, mFad, mNecropsy,
                mAttachment, mSolution, idUser);
        call.enqueue(listener);
    }

    public void uploadAttachment(String path, String type, Callback<ResponseBody> listener) {
        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse(type + "/*"), file);
        MultipartBody.Part finalFile = MultipartBody.Part.createFormData(type, file.getName(), body);

        Call<ResponseBody> call = RetrofitInstance.entryService().uploadAttachment(finalFile, type);
        call.enqueue(listener);
    }
}
