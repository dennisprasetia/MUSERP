package com.wonokoyo.voadip.serveraccess;

import com.wonokoyo.voadip.serveraccess.service.VoadipService;

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

    public static VoadipService voadipService() {
        return RetrofitInstance.getRetrofit().create(VoadipService.class);
    }
}
