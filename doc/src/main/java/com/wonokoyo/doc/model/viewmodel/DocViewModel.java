package com.wonokoyo.doc.model.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.wonokoyo.doc.model.Doc;
import com.wonokoyo.doc.model.DocDetail;
import com.wonokoyo.doc.model.DocWithLoc;
import com.wonokoyo.doc.model.Loc;
import com.wonokoyo.doc.model.repository.DocRepository;
import com.wonokoyo.doc.room.repo.DocRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public LiveData<List<DocWithLoc>> getAllDocWithLoc() {
        return docRepo.getAllDocWithLoc();
    }

    public void syncDocToPhone(String id_user) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("status");
                        if (status == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("content");
                            List<Doc> docs = Arrays.asList(new Gson().fromJson(jsonArray.toString(), Doc[].class));
                            docRepo.saveAllDoc(docs);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        docRepository.getAllDoc(date, id_user, listener);
    }

    public LiveData<Doc> loadDocByOp(String op) {
        return docRepo.getDocByOp(op);
    }

    public void savePrepDoc(Doc doc) {
        docRepo.updateDoc(doc);
        eventLiveData.setValue("saved_lokal");
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

    public void setEventLiveData(String status) {
        this.eventLiveData.setValue(status);
    }

    public void resetEvent() {
        eventLiveData.setValue("");
    }

    public void deleteDocByOp() {
        docMutableLiveData.setValue(null);
    }

    // Save akses ke server
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

    public void saveSpjTsLoc(Doc doc) {
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

        docRepository.saveSpjTsLoc(doc, listener);
    }

    public void uploadAttachment(String path) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("status") == 1) {
                            eventLiveData.setValue("file_uploaded");
                        } else {
                            eventLiveData.setValue("file_not_uploaded");
                        }

                        System.out.println(response.body().string());
                    } catch (IOException | JSONException e) {
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

    public void uploadFromLokal(List<Doc> docs) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getInt("status") == 1) {
                            eventLiveData.setValue("uploaded");
                        } else {
                            eventLiveData.setValue("notfound");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("failed");
            }
        };

        docRepository.uploadFromLokal(docs, listener);
    }
}
