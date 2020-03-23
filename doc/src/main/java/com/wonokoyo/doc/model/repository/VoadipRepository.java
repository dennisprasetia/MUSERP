package com.wonokoyo.doc.model.repository;

import com.google.gson.Gson;
import com.wonokoyo.doc.model.Voadip;
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

public class VoadipRepository {
    private static VoadipRepository voadipRepository;

    public static VoadipRepository getInstance() {
        if (voadipRepository == null)
            voadipRepository = new VoadipRepository();

        return voadipRepository;
    }

    public void getListVoadipByNoreg(String noreg, String date, Callback<List<Voadip>> listener) {
        Call<List<Voadip>> voadipCall = RetrofitInstance.voadipService().getVoadipByNoreg(noreg, date);
        voadipCall.enqueue(listener);
    }

    public void getVoadipByNoOp(String noOp, Callback<Voadip> listener) {
        Call<Voadip> voadipCall = RetrofitInstance.voadipService().getVoadipByNoOp(noOp);
        voadipCall.enqueue(listener);
    }
}
