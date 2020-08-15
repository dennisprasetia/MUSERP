package com.wonokoyo.muserp.menu.daily.model.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wonokoyo.muserp.menu.daily.model.Attachment;
import com.wonokoyo.muserp.menu.daily.model.Doc;
import com.wonokoyo.muserp.menu.daily.model.FeedAndDead;
import com.wonokoyo.muserp.menu.daily.model.Necropsy;
import com.wonokoyo.muserp.menu.daily.model.Screen;
import com.wonokoyo.muserp.menu.daily.model.Solution;
import com.wonokoyo.muserp.menu.daily.model.repository.EntryRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryViewModel extends ViewModel {
    private MutableLiveData<String> event;

    EntryRepository entryRepository;

    public void init() {
        if (entryRepository != null)
            return;

        event = new MutableLiveData<>();
        entryRepository = EntryRepository.getInstance();
    }

    private Doc mDoc;
    private List<Screen> mListScreen;
    private FeedAndDead mFad;
    private List<Necropsy> mListNecropsy;
    private List<Attachment> mListAttachment;
    private List<Solution> mListSolution;

    public void setmDoc(Doc mDoc) {
        this.mDoc = mDoc;
    }

    public void setmListScreen(List<Screen> mListScreen) {
        this.mListScreen = mListScreen;
    }

    public void addFad(String receive, String remain, String death, String description) {
        FeedAndDead fad = new FeedAndDead();
        fad.setReceive(Integer.parseInt(receive));
        fad.setRemain(Integer.parseInt(remain));
        fad.setDeath(Integer.parseInt(death));
        fad.setDescription(description);

        this.mFad = fad;
    }

    public void setmListNecropsy(List<Necropsy> mListNecropsy) {
        this.mListNecropsy = mListNecropsy;
    }

    public void setmListAttachment(List<Attachment> mListAttachment) {
        this.mListAttachment = mListAttachment;
    }

    public void setmListSolution(List<String> strings) {
        this.mListSolution = new ArrayList<>();

        for (int i = 0; i < strings.size(); i++) {
            Solution solution = new Solution();
            solution.setId(i+1);
            solution.setNama(strings.get(i));

            this.mListSolution.add(solution);
        }
    }

    public void saveRhk(String idUser) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("success");
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.d("Debug", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("failure");
            }
        };

        entryRepository.saveRhk(mDoc, mListScreen, mFad, mListNecropsy, mListAttachment,
                mListSolution, listener, idUser);
    }

    public void uploadAttachment() {
        for (int i = 0; i < mListAttachment.size(); i++) {
            Callback<ResponseBody> listener = new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            System.out.println(response.body().string());
                            System.out.println("success upload");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("failed");
                    System.out.println(t.getMessage());
                }
            };

            entryRepository.uploadAttachment(mListAttachment.get(i).getFileuri(),
                    mListAttachment.get(i).getType(), listener);
        }
    }

    public void clearAllData() {
        mDoc = null;
        mListScreen = null;
        mFad = null;
        mListNecropsy = null;
        mListAttachment = null;
        mListSolution = null;
    }
}
