package com.wonokoyo.doc.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.DocDetail;
import com.wonokoyo.doc.model.DocWithLoc;
import com.wonokoyo.doc.model.Loc;
import com.wonokoyo.doc.model.repository.DocRepository;
import com.wonokoyo.doc.room.repo.DocRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocViewModel extends ViewModel {
    private MutableLiveData<List<Doc>> mutableLiveData;
    private MutableLiveData<Doc> docMutableLiveData;
    private MutableLiveData<String> eventLiveData;
    private DocRepository docRepository;
    private DocRepo docRepo;

    public void init(Application application) {
        if (mutableLiveData != null || docMutableLiveData != null)
            return;

        mutableLiveData = new MutableLiveData<>();
        docMutableLiveData = new MutableLiveData<>();
        eventLiveData = new MutableLiveData<>();

        docRepository = DocRepository.getInstance();
        mutableLiveData.setValue(new ArrayList<Doc>());
        docRepo = new DocRepo(application);
    }

    public LiveData<List<Doc>> getListDoc() {
        return mutableLiveData;
    }

    public LiveData<Doc> getLiveDoc() {
        return docMutableLiveData;
    }

    public void setDocMutableLiveData(Doc doc) {
        this.docMutableLiveData.setValue(doc);
    }

    public void setMutableLiveData(List<Doc> docs) {
        this.mutableLiveData.setValue(docs);
    }

    public LiveData<List<Doc>> populateListDoc() {
        return docRepo.getAllDoc();
    }

    public void syncDocToPhone(String id_user) {
        Callback<List<Doc>> listener = new Callback<List<Doc>>() {
            @Override
            public void onResponse(Call<List<Doc>> call, Response<List<Doc>> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                    docRepo.saveAllDoc(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Doc>> call, Throwable t) {
                System.out.println("failed");
                mutableLiveData.setValue(new ArrayList<Doc>());
            }
        };

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        docRepository.getAllDoc("2020-02-20", id_user, listener);
    }

    public LiveData<Doc> loadDocByOp(String op) {
        return docRepo.getDocByOp(op);
    }

    public void savePrepDoc(Doc doc) {
        docRepo.updateDoc(doc);
    }

    public void saveLocDoc(Loc loc) {
        docRepo.saveLocDoc(loc);
    }

    public void updateLocDoc(Loc loc) {
        docRepo.updateLoc(loc);
    }

    public LiveData<DocWithLoc> loadDocByNoreg(String noreg) {
        return docRepo.getDocByNoreg(noreg);
    }

    public LiveData<String> getEvent() {
        return eventLiveData;
    }

    public void resetEvent() {
        eventLiveData.setValue("");
    }

    public void getDocByOp(String noOp) {
        Callback<Doc> listener = new Callback<Doc>() {
            @Override
            public void onResponse(Call<Doc> call, Response<Doc> response) {
                if (response.isSuccessful())
                    docMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Doc> call, Throwable t) {
                System.out.println("failed");
                docMutableLiveData.setValue(null);
            }
        };

        docRepository.getDocByNoOp(noOp, listener);
    }

    public void deleteDocByOp() {
        docMutableLiveData.setValue(null);
    }

    public void saveDoc(Doc doc, String idUser) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("status") == 1) {
                            eventLiveData.setValue("saved");
                        } else {
                            eventLiveData.setValue("notfound");
                        }

                        System.out.println(response.body().string());
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

        docRepository.saveDoc(doc, idUser, listener);
        docRepo.updateDoc(doc);
    }

    public void saveSpjDoc(Doc doc) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("status") == 1) {
                            eventLiveData.setValue("saved");
                        } else {
                            eventLiveData.setValue("notfound");
                        }
                        System.out.println(response.body().string());
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

        docRepository.saveSpjDoc(doc, listener);
    }

    public void uploadAttachment(String path) {
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

        docRepository.uploadAttachment(path, listener);
    }
}
