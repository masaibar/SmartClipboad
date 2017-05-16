package com.masaibar.smartclipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.Nullable;

public class ClipboardTextGetter {

    private ClipboardManager mManager;

    public ClipboardTextGetter(Context context) {
        mManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Nullable
    public String getText() {
        if (!mManager.hasPrimaryClip()) {
            return null;
        }

        ClipData clipData = mManager.getPrimaryClip();

        if (clipData == null) {
            return null;
        }

        ClipData.Item item = clipData.getItemAt(0);

        return item.getText().toString();
    }
}
