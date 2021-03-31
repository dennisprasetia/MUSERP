package com.wonokoyo.doc.serveraccess.service;

import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.serveraccess.Url;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DocService {
    @GET(Url.GET_PLANNING_DOC)
    Call<ResponseBody> getPlanningDoc(@Query("date") String today, @Query("id_user") String id_user);

    @GET(Url.SAVE_DOC)
    Call<ResponseBody> saveDoc(@Query("doc") String stringDoc, @Query("id_user") String idUser);

    @GET(Url.SAVE_SPJ_TS_LOC)
    Call<ResponseBody> saveSpjTsLoc(@Query("doc") String doc);

    @Multipart
    @POST(Url.UPLOAD_ATTACHMENT)
    Call<ResponseBody> uploadAttachment(@Part MultipartBody.Part photo, @Query("type") String type);

    @FormUrlEncoded
    @POST(Url.UPLOAD_FROM_LOKAL)
    Call<ResponseBody> uploadFromLokal(@Field("docs") String docs, @Field("id_user") String id_user);
}
