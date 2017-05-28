package com.masaibar.smartclipboard.utils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

public class ClipboardUtil {
    private ClipboardManager mManager;

    public ClipboardUtil(Context context) {
        mManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public void addClipChangedListener(ClipboardManager.OnPrimaryClipChangedListener listener) {
        mManager.addPrimaryClipChangedListener(listener);
    }

    public synchronized void copyToClipboard(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        ClipData.Item item = new ClipData.Item(text);

        String[] mimeType = new String[1];
        mimeType[0] = ClipDescription.MIMETYPE_TEXT_URILIST;

        ClipData clipData = new ClipData(new ClipDescription("text_data", mimeType), item);

        mManager.setPrimaryClip(clipData);
    }
}
