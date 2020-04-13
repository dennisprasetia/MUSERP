package com.wonokoyo.doc.model.repository;

import com.google.gson.Gson;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.serveraccess.RetrofitInstance;

import org.json.JSONException;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class DocRepository {
    private static DocRepository docRepository;

    public static DocRepository getInstance() {
        if (docRepository == null)
            docRepository = new DocRepository();

        return docRepository;
    }

    public void getAllDoc(String date, String id_user, Callback<ResponseBody> listener) {
        Call<ResponseBody> docCall = RetrofitInstance.docService().getPlanningDoc(date, id_user);
        docCall.enqueue(listener);
    }

    public void saveDoc(Doc doc, String idUser, Callback<ResponseBody> listener) {
        Call<ResponseBody> docCall = RetrofitInstance.docService().saveDoc(new Gson().toJson(doc), idUser);
        docCall.enqueue(listener);
    }

    public void saveSpjTsLoc(Doc doc, Callback<ResponseBody> listener) {
        Call<ResponseBody> docCall = RetrofitInstance.docService().saveSpjTsLoc(new Gson().toJson(doc));
        docCall.enqueue(listener);
    }

    public void uploadAttachment(String path, Callback<ResponseBody> listener) {
        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part finalFile = MultipartBody.Part.createFormData("image", file.getName(), body);

        Call<ResponseBody> call = RetrofitInstance.docService().uploadAttachment(finalFile, "DOC");
        call.enqueue(listener);
    }

    public void uploadFromLokal(List<Doc> docs, Callback<ResponseBody> listener) {
        Call<ResponseBody> docCall = RetrofitInstance.docService().uploadFromLokal(new Gson().toJson(docs));
        docCall.enqueue(listener);
    }
}
