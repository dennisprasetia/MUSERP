package com.wonokoyo.voadip.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.VoadipWithItem;
import com.wonokoyo.voadip.model.repository.VoadipRepository;
import com.wonokoyo.voadip.room.repo.AppRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoadipViewModel extends ViewModel {
    private MutableLiveData<List<Voadip>> mutableLiveData;
    private MutableLiveData<Voadip> voadipMutableLiveData;
    private MutableLiveData<String> eventLiveData;
    private String message;
    private VoadipRepository voadipRepository;
    private AppRepo appRepo;

    public void init(Application application) {
        if (mutableLiveData != null && voadipMutableLiveData != null)
            return;

        mutableLiveData = new MutableLiveData<>();
        voadipMutableLiveData = new MutableLiveData<>();
        eventLiveData = new MutableLiveData<>();
        message = new String();
        voadipRepository = VoadipRepository.getInstance();
        appRepo = new AppRepo(application);
    }

    public LiveData<Voadip> getLiveVoadip() {
        return voadipMutableLiveData;
    }

    public LiveData<String> getEvent() {
        return eventLiveData;
    }

    public String getMessage() {
        return message;
    }

    public void setVoadipMutableLiveData(Voadip voadip) {
        this.voadipMutableLiveData.setValue(voadip);
    }

    public LiveData<List<Voadip>> getLiveListVoadip() {
        return mutableLiveData;
    }

    public LiveData<List<VoadipWithItem>> populateListVoadip() {
        return appRepo.getAllVoadipWithItem();
    }

    public void syncVoadipToPhone(String id_user) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("status");
                        message = jsonObject.getString("message");

                        if (status == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("content");
                            List<Voadip> voadips = Arrays.asList(new Gson().fromJson(jsonArray.toString(), Voadip[].class));
                            appRepo.saveAllVoadipAndDetail(voadips);
                            eventLiveData.setValue("sync");
                        } else {
                            eventLiveData.setValue("notsync");
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

        voadipRepository.getListVoadip(id_user, listener);
    }

    public LiveData<VoadipWithItem> loadVoadipByOp(String op) {
        return appRepo.getVoadipByOp(op);
    }

    public void saveVoadipAndDetail(String idUser) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("status");
                        message = jsonObject.getString("message");

                        if (status == 1) {
                            eventLiveData.setValue("saved");
                        } else {
                            eventLiveData.setValue("notsaved");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("success");
                } else {
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("failed");
            }
        };

        voadipRepository.saveVoadip(getLiveVoadip().getValue(), idUser, listener);
        appRepo.updateVoadipAndDetail(getLiveVoadip().getValue());
    }

    public void saveVoadipAttachSj() {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        System.out.println(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("success upload");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("failed");
            }
        };

        voadipRepository.uploadAttachment(getLiveVoadip().getValue().getUrl(), listener);
    }

    public void saveVoadipAttachSign() {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        System.out.println(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("success upload");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("failed");
            }
        };

        voadipRepository.uploadAttachment(getLiveVoadip().getValue().getUrlSign(), listener);
    }

    public void uploadVoadipFromLocal(List<Voadip> voadips, String idUser) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("status");
                        message = jsonObject.getString("message");

                        if (status == 1) {
                            eventLiveData.setValue("uploaded");
                        } else {
                            eventLiveData.setValue("notuploaded");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };

        voadipRepository.uploadFromLocal(voadips, idUser, listener);
    }

    public void uploadVoadipSj(String url) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> xcall, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("status");
                        message = jsonObject.getString("message");

                        if (status == 1) {
                            eventLiveData.setValue("sj_uploaded");
                        } else {
                            eventLiveData.setValue("sj_notuploaded");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("failed");
            }
        };

        voadipRepository.uploadAttachment(url, listener);
    }

    public LiveData<List<VoadipWithItem>> getVoadipToUpload() {
        return appRepo.getVoadipWithItemToUpload();
    }

    public void deleteLiveDataVoadip() {
        voadipMutableLiveData.setValue(null);
    }

    public void saveNoSjToVoadip(String noSj) {
        Voadip voadip = getLiveVoadip().getValue();
        voadip.setNoSj(noSj);

        voadipMutableLiveData.setValue(voadip);
    }
    public void savePenerimaToVoadip(String penerima) {
        Voadip voadip = getLiveVoadip().getValue();
        voadip.setPenerima(penerima);

        voadipMutableLiveData.setValue(voadip);
    }

    public void saveUriSjToVoadip(String uri) {
        Voadip voadip = getLiveVoadip().getValue();
        voadip.setUrl(uri);

        voadipMutableLiveData.setValue(voadip);
    }

    public void saveItemDetailToVoadip(int index, String kemasan, int jumlah, String keterangan) {
        voadipMutableLiveData.getValue().getItemVoadips().get(index).setRealKemasan(kemasan);
        voadipMutableLiveData.getValue().getItemVoadips().get(index).setRealJumlah(jumlah);
        voadipMutableLiveData.getValue().getItemVoadips().get(index).setKeterangan(keterangan);
    }

    public void saveUriSignToVoadip(String uri) {
        Voadip voadip = getLiveVoadip().getValue();
        voadip.setUrlSign(uri);

        voadipMutableLiveData.setValue(voadip);
    }

    public void saveTglTerima(String date) {
        Voadip voadip = getLiveVoadip().getValue();
        voadip.setTglTerima(date);

        voadipMutableLiveData.setValue(voadip);
    }

    public void saveVoadipLocaly() {
        appRepo.updateVoadipAndDetail(getLiveVoadip().getValue());
        eventLiveData.setValue("saved_lokal");
    }
}
