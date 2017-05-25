package com.masaibar.smartclipboard;

import android.content.Context;
import android.text.TextUtils;

import com.masaibar.smartclipboard.entities.FavoriteData;
import com.masaibar.smartclipboard.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDBManager extends DBManager {

    public FavoriteDBManager(Context context) {
        super(context);
    }

    @Override
    public void save(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        final FavoriteData data = new FavoriteData();
        data.time = System.currentTimeMillis();
        data.text = text;

        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                App.getOrma(getContext()).insertIntoFavoriteData(data);
            }
        });
    }

    @Override
    public void deleteAt(final long id) {
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                App.getOrma(getContext()).deleteFromFavoriteData().idEq(id).execute();
            }
        });
    }

    public ArrayList<FavoriteData> getAll() {
        List<FavoriteData> datas =
                App.getOrma(getContext()).selectFromFavoriteData().orderByIdDesc().toList();
        return new ArrayList<>(datas);
    }
}
