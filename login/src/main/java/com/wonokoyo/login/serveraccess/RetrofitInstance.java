package com.wonokoyo.login.serveraccess;

import com.wonokoyo.login.serveraccess.service.LoginService;
import com.wonokoyo.login.util.UnsafeOkHttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    static OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Url.BASE_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static LoginService getLoginService() {
        return RetrofitInstance.getRetrofit().create(LoginService.class);
    }
}