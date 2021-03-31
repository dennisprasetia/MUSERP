package com.wonokoyo.pakan.serveraccess;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PakanService {
    @GET(Url.GET_MITRA)
    Call<ResponseBody> getMitra();

    @GET(Url.GET_NOREG)
    Call<ResponseBody> getNoregByMitra(@Query("mitra") String mitra);

    @GET(Url.GET_SJ_PAKAN)
    Call<ResponseBody> getSjByNoreg(@Query("noreg") String noreg);

    @FormUrlEncoded
    @POST(Url.SAVE_PAKAN_TIMBANG)
    Call<ResponseBody> uploadPakanTimbang(@Field("data") String data);
}
