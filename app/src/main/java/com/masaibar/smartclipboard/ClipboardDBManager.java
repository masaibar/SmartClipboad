package com.masaibar.smartclipboard;

import android.content.Context;
import android.text.TextUtils;

import com.masaibar.smartclipboard.entities.ClipboardData;
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

        final ClipboardData data = new ClipboardData();
        data.time = System.currentTimeMillis();
        data.text = text;

        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                App.getOrma(mContext).insertIntoClipboardData(data);
            }
        });
    }

    public ArrayList<ClipboardData> getAll() {
        List<ClipboardData> datas =
                App.getOrma(mContext).selectFromClipboardData().toList();

        return new ArrayList<>(datas);
    }
}
