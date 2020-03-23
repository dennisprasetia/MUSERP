package com.wonokoyo.voadip.serveraccess.service;

import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.serveraccess.Url;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface VoadipService {
    @GET(Url.GET_VOADIP_BY_DATE)
    Call<List<Voadip>> getVoadipByDate(@Query("date") String date);

    @GET(Url.GET_VOADIP_BY_NOREG)
    Call<List<Voadip>> getVoadipByNoreg(@Query("noreg") String noreg, @Query("tgldocin") String date);

    @GET(Url.GET_VOADIP_BY_NO_OP)
    Call<Voadip> getVoadipByNoOp(@Query("noOp") String noOp);

    @GET(Url.SAVE_VOADIP)
    Call<ResponseBody> saveVoadip(@Query("voadip") String voadip, @Query("id_user") String idUser);

    @Multipart
    @POST(Url.UPLOAD_ATTACHMENT)
    Call<ResponseBody> uploadAttachment(@Part MultipartBody.Part photo, @Query("type") String type);
}
