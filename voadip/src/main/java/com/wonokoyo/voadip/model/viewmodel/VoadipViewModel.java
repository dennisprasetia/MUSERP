package com.wonokoyo.voadip.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wonokoyo.voadip.model.ItemVoadip;
import com.wonokoyo.voadip.model.Voadip;
import com.wonokoyo.voadip.model.VoadipWithItem;
import com.wonokoyo.voadip.model.repository.VoadipRepository;
import com.wonokoyo.voadip.room.repo.AppRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoadipViewModel extends ViewModel {
    private MutableLiveData<List<Voadip>> mutableLiveData;
    private MutableLiveData<Voadip> voadipMutableLiveData;
    private VoadipRepository voadipRepository;
    private AppRepo appRepo;

    public void init(Application application) {
        if (mutableLiveData != null && voadipMutableLiveData != null)
            return;

        mutableLiveData = new MutableLiveData<>();
        voadipMutableLiveData = new MutableLiveData<>();
        voadipRepository = VoadipRepository.getInstance();
        appRepo = new AppRepo(application);
    }

    public LiveData<Voadip> getLiveVoadip() {
        return voadipMutableLiveData;
    }

    public void setVoadipMutableLiveData(Voadip voadip) {
        this.voadipMutableLiveData.setValue(voadip);
    }

    public LiveData<List<Voadip>> getLiveListVoadip() {
        return mutableLiveData;
    }

    public void populateListVoadip(String date) {
        Callback<List<Voadip>> listener = new Callback<List<Voadip>>() {
            @Override
            public void onResponse(Call<List<Voadip>> call, Response<List<Voadip>> response) {
                if (response.isSuccessful()) {
                    appRepo.saveAllVoadipAndDetail(response.body());
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Voadip>> call, Throwable t) {
                System.out.println("failed");
                mutableLiveData.setValue(new ArrayList<Voadip>());
            }
        };

        voadipRepository.getListVoadip(date, listener);
    }

    public LiveData<List<VoadipWithItem>> loadAllVoadip() {
        return appRepo.getAllVoadip();
    }

    public LiveData<VoadipWithItem> loadVoadipByOp(String op) {
        return appRepo.getVoadipByOp(op);
    }

    public void getVoadipByOp(String noOp) {
        Callback<Voadip> listener = new Callback<Voadip>() {
            @Override
            public void onResponse(Call<Voadip> call, Response<Voadip> response) {
                if (response.isSuccessful())
                    voadipMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Voadip> call, Throwable t) {
                System.out.println("failed");
                voadipMutableLiveData.setValue(null);
            }
        };

        voadipRepository.getVoadipByNoOp(noOp, listener);
    }

    public void saveVoadipAndDetail(String idUser) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        System.out.println(response.body().string());
                    } catch (IOException e) {
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
}
