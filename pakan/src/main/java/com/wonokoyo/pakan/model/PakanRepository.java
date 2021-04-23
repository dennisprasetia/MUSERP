package com.wonokoyo.pakan.model;

import com.wonokoyo.pakan.serveraccess.RetrofitInstance;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class PakanRepository {
    private static PakanRepository repository;

    public static PakanRepository getInstance() {
        if (repository == null)
            repository = new PakanRepository();

        return repository;
    }

    public void getMitra(Callback<ResponseBody> listener) {
        Call<ResponseBody> call = RetrofitInstance.getMitraService().getMitra();
        call.enqueue(listener);
    }

    public void getNoreg(String mitra, Callback<ResponseBody> listener) {
        Call<ResponseBody> call = RetrofitInstance.getMitraService().getNoregByMitra(mitra);
        call.enqueue(listener);
    }

    public void getSjByNoreg(String noreg, Callback<ResponseBody> listener) {
        Call<ResponseBody> call = RetrofitInstance.getMitraService().getSjByNoreg(noreg);
        call.enqueue(listener);
    }

    public void uploadPakanTimbang(String data, String id_user, Callback<ResponseBody> listener) {
        Call<ResponseBody> call = RetrofitInstance.getMitraService().uploadPakanTimbang(data, id_user);
        call.enqueue(listener);
    }
}
