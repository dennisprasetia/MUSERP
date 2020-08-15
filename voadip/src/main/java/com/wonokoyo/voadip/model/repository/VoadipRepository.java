package com.wonokoyo.voadip.model.repository;

import com.google.gson.Gson;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.serveraccess.RetrofitInstance;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class VoadipRepository {
    private static VoadipRepository voadipRepository;

    public static VoadipRepository getInstance() {
        if (voadipRepository == null)
            voadipRepository = new VoadipRepository();

        return voadipRepository;
    }

    public void getListVoadip(String id_user, Callback<ResponseBody> listener) {
        Call<ResponseBody> voadipCall = RetrofitInstance.voadipService().getVoadipByUser(id_user);
        voadipCall.enqueue(listener);
    }

    public void getListVoadipByNoreg(String noreg, String date, Callback<List<Voadip>> listener) {
        Call<List<Voadip>> voadipCall = RetrofitInstance.voadipService().getVoadipByNoreg(noreg, date);
        voadipCall.enqueue(listener);
    }

    public void getVoadipByNoOp(String noOp, Callback<Voadip> listener) {
        Call<Voadip> voadipCall = RetrofitInstance.voadipService().getVoadipByNoOp(noOp);
        voadipCall.enqueue(listener);
    }

    public void saveVoadip(Voadip voadip, String idUser, Callback<ResponseBody> listener) {
        Call<ResponseBody> voadipCall = RetrofitInstance.voadipService().saveVoadip(new Gson().toJson(voadip), idUser);
        voadipCall.enqueue(listener);
    }

    public void uploadFromLocal(List<Voadip> voadips, String idUser, Callback<ResponseBody> listener) {
        Call<ResponseBody> voadipCall = RetrofitInstance.voadipService().uploadFromLocal(new Gson().toJson(voadips), idUser);
        voadipCall.enqueue(listener);
    }

    public void uploadAttachment(String path, Callback<ResponseBody> listener) {
        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part finalFile = MultipartBody.Part.createFormData("image", file.getName(), body);

        Call<ResponseBody> call = RetrofitInstance.voadipService().uploadAttachment(finalFile, "VOADIP");
        call.enqueue(listener);
    }
}
