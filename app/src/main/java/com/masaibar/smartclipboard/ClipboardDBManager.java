package com.masaibar.smartclipboard;

import android.content.Context;
import android.text.TextUtils;

import com.masaibar.smartclipboard.entities.HistoryData;
import com.masaibar.smartclipboard.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

public class ClipboardDBManager {
    private Context mContext;

    public ClipboardDBManager(Context context) {
        mContext = context;
    }

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
                App.getOrma(mContext).insertIntoHistoryData(data);
            }
        });
    }

    public void deleteAt(final long id) {
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                App.getOrma(mContext).deleteFromHistoryData().idEq(id).execute();
            }
        });
    }

    /**
     * todo 取得件数の制限をする
     */
    public ArrayList<HistoryData> getAll() {
        List<HistoryData> datas =
                App.getOrma(mContext).selectFromHistoryData().orderByIdDesc().toList();

        return new ArrayList<>(datas);
    }
}
