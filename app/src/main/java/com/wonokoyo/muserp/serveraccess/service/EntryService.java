package com.wonokoyo.muserp.serveraccess.service;

import com.wonokoyo.muserp.menu.daily.model.Doc;
import com.wonokoyo.muserp.serveraccess.Url;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EntryService {
    @GET(Url.GET_LIST_MITRA)
    Call<List<Doc>> getListDoc();

    @GET(Url.SAVE_RHK)
    Call<ResponseBody> saveRhk(@Query("doc") String doc, @Query("screen") String screen,
                               @Query("fad") String fad, @Query("necropsy") String necropsy,
                               @Query("attachment") String attachment, @Query("solution") String solution,
                               @Query("id_user") String idUser);

    @Multipart
    @POST(Url.UPLOAD_ATTACHMENT)
    Call<ResponseBody> uploadAttachment(@Part MultipartBody.Part photo, @Query("type") String type);
}
