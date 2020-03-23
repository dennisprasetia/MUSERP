package com.wonokoyo.login.serveraccess.service;

import com.wonokoyo.login.serveraccess.Url;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {
    @GET(Url.LOGIN)
    Call<ResponseBody> login(@Query("username") String username, @Query("password") String password,
                             @Query("deviceId") String deviceId);
}
