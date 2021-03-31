package com.wonokoyo.pakan.model;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.wonokoyo.pakan.room.PakanRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PakanViewModel {
    private MutableLiveData<String[]> liveDataMitra;
    private MutableLiveData<String[]> liveDataNoreg;
    private MutableLiveData<String[]> liveDataSj;

    private PakanRepo repo;
    private PakanRepository repository;
    private Application app;
    private ProgressDialog dialog;

    public void init(Application application, Context context) {
        if (liveDataMitra != null || liveDataNoreg != null || liveDataSj != null)
            return;

        liveDataMitra = new MutableLiveData<>();
        liveDataNoreg = new MutableLiveData<>();
        liveDataSj = new MutableLiveData<>();

        repo = new PakanRepo(application);
        repository = new PakanRepository();

        app = application;

        dialog = new ProgressDialog(context);
        dialog.setMessage("Sedang memuat data");
        dialog.setCancelable(false);
    }

    public MutableLiveData<String[]> getLiveDataMitra() {
        return liveDataMitra;
    }

    public MutableLiveData<String[]> getLiveDataNoreg() {
        return liveDataNoreg;
    }

    public MutableLiveData<String[]> getLiveDataSj() {
        return liveDataSj;
    }

    public void savePakans(List<Pakan> pakans) {
        repo.savePakan(pakans);
    }

    public void updateStatPakan(List<Pakan> pakans) {
        for (Pakan p : pakans) {
            p.setStat_upload(1);
        }

        repo.updatePakan(pakans);
    }

    public LiveData<List<Pakan>> getAllPakanNotUploaded() {
        return repo.getAllPakanTimbang();
    }

    public LiveData<List<Pakan>> getAllPakanBySj(String no_sj) {
        return repo.getPakansBySj(no_sj);
    }

    public LiveData<Integer> countData() {
        return repo.getCountData();
    }

    public void getMitra() {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        JSONArray array = jsonObject.getJSONArray("content");

                        if (status == 1) {
                            String[] mitra = new String[array.length()];
                            for (int a = 0; a < array.length(); a ++) {
                                JSONObject object = (JSONObject) array.get(a);
                                mitra[a] = object.getString("mitra");
                            }

                            liveDataMitra.setValue(mitra);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };

        repository.getMitra(listener);
    }

    public void getNoregByMitra(String mitra) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        JSONArray array = jsonObject.getJSONArray("content");

                        if (status == 1) {
                            String[] noreg = new String[array.length()];
                            for (int a = 0; a < array.length(); a ++) {
                                JSONObject object = (JSONObject) array.get(a);
                                noreg[a] = object.getString("noreg");
                            }

                            liveDataNoreg.setValue(noreg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };

        repository.getNoreg(mitra, listener);
    }

    public void getSjByNoreg(String noreg) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");
                        JSONArray array = jsonObject.getJSONArray("content");

                        if (status == 1) {
                            String[] sj = new String[array.length()];
                            for (int a = 0; a < array.length(); a ++) {
                                JSONObject object = (JSONObject) array.get(a);
                                sj[a] = object.getString("sj");
                            }

                            liveDataSj.setValue(sj);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };

        repository.getSjByNoreg(noreg, listener);
    }

    public void uploadPakanTimbang(final List<Pakan> pakans) {
        String data = new Gson().toJson(pakans);
        dialog.show();

        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");

                        if (status == 1) {
                            updateStatPakan(pakans);

                            if (dialog.isShowing())
                                dialog.dismiss();

                            Toast.makeText(app.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(app.getApplicationContext(), "Gagal Simpan Ke Server", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };

        repository.uploadPakanTimbang(data, listener);
    }
}
