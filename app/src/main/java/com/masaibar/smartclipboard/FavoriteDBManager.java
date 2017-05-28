package com.masaibar.smartclipboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.masaibar.smartclipboard.entities.FavoriteData;
import com.masaibar.smartclipboard.entities.OrmaDatabase;
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

        if (isExist(text)) {
            return;
        }

        final FavoriteData data = new FavoriteData();
        data.time = System.currentTimeMillis();
        data.text = text;

        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma().insertIntoFavoriteData(data);
            }
        });
    }

    public void replace(final long fromId, final long toId) {
        final FavoriteData fromData = getById(fromId);
        final FavoriteData toData = getById(toId);

        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma().updateFavoriteData().idEq(toId)
                        .time(fromData.time).text(fromData.text)
                        .execute();

                getOrma().updateFavoriteData().idEq(fromId)
                        .time(toData.time).text(toData.text)
                        .execute();

            }
        });
    }

    @Override
    public void deleteAt(final long id) {
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma().deleteFromFavoriteData().idEq(id).execute();
            }
        });
    }

    public ArrayList<FavoriteData> getAll() {
        List<FavoriteData> datas =
                getOrma().selectFromFavoriteData().orderByIdDesc().toList();
        return new ArrayList<>(datas);
    }

    private boolean isExist(String text) {
        return getOrma().selectFromFavoriteData().textEq(text).toList().size() > 0;
    }

    @Nullable
    private FavoriteData getById(long id) {
        List<FavoriteData> datas = getOrma().selectFromFavoriteData().toList();
        for (FavoriteData data : datas) {
            if (data.id == id) {
                return data;
            }
        }

        return null;
    }

    private OrmaDatabase getOrma() {
        return App.getOrma(getContext());
    }
}
