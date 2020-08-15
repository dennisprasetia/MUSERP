package com.wonokoyo.rhk.serveraccess;

import com.wonokoyo.rhk.serveraccess.service.EntryService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Url.BASE_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static EntryService entryService() {
        return RetrofitInstance.getRetrofit().create(EntryService.class);
    }
}