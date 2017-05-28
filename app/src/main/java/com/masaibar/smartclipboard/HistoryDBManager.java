package com.masaibar.smartclipboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.masaibar.smartclipboard.entities.HistoryData;
import com.masaibar.smartclipboard.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

public class HistoryDBManager extends DBManager {

    public HistoryDBManager(Context context) {
        super(context);
    }

    @Override
    public void save(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        final HistoryData data = new HistoryData();
        data.time = System.currentTimeMillis();
        data.text = text;

        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma().insertIntoHistoryData(data);
            }
        });
    }

    @Override
    public void delete(final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma().deleteFromFavoriteData().textEq(text).execute();
            }
        });
    }

    @Override
    public void deleteAt(final long id) {
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma().deleteFromHistoryData().idEq(id).execute();
            }
        });
    }

    /**
     * todo 取得件数の制限をする
     */
    public ArrayList<HistoryData> getAll() {
        List<HistoryData> datas =
                getOrma().selectFromHistoryData().orderByIdDesc().toList();

        return new ArrayList<>(datas);
    }

    @Nullable
    public String getLatestText() {
        HistoryData data = getOrma().selectFromHistoryData().orderByIdDesc().getOrNull(0);
        return data == null ? null : data.text;
    }
}
