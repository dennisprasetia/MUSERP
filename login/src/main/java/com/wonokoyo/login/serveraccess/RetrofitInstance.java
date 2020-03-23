package com.wonokoyo.login.serveraccess;

import com.wonokoyo.login.serveraccess.service.LoginService;

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

    public static LoginService getLoginService() {
        return RetrofitInstance.getRetrofit().create(LoginService.class);
    }
}
