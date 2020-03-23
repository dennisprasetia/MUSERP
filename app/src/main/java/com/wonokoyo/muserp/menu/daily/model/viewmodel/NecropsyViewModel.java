package com.wonokoyo.muserp.menu.daily.model.viewmodel;

import androidx.lifecycle.ViewModel;

import com.wonokoyo.muserp.menu.daily.model.Necropsy;

import java.util.ArrayList;
import java.util.List;

public class NecropsyViewModel extends ViewModel {
    private List<Necropsy> listNecropsy;
    private String[] params = new String[]{
            "Conjungtivitis", "Trachea radang", "Airsaculitis", "Hidropericard", "Pengapuran pada hati",
            "Perkejuan di jantung", "Perkejuan di rongga perut", "Selaput hati keruh", "Usus entritis",
            "Cecum radang", "Gizard erosi", "Bursal radang", "Ginjal bengkak (ayam mati)", "Gout",
            "Pecah pembuluh darah ke arah malaria", "Kotoran putih di kloaka", "Kotoran hijau di kloaka",
            "Kualitas liter dan kadar amonia",
    };

    public void init() {
        if (listNecropsy != null)
            return;

        listNecropsy = new ArrayList<>();
        populateNecropsyParameter();
    }

    public List<Necropsy> getListNecropsy() {
        return this.listNecropsy;
    }

    private void populateNecropsyParameter() {
        for (int i = 0; i < params.length; i++) {
            Necropsy necropsy = new Necropsy();
            necropsy.setId(i + 1);
            necropsy.setParameter(params[i]);
            necropsy.setStatus(false);
            necropsy.setKeterangan("");

            listNecropsy.add(necropsy);
        }
    }

    public void updateDescNecropsy(int id, String desc) {
        listNecropsy.get(id - 1).setKeterangan(desc);
    }

    public void updateNecropsy(int id) {
        if (listNecropsy.get(id - 1).isStatus())
            listNecropsy.get(id - 1).setStatus(false);
        else
            listNecropsy.get(id - 1).setStatus(true);
    }

    public void clear() {
        listNecropsy.clear();
        listNecropsy = null;
    }
}
