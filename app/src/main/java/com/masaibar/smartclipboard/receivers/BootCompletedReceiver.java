package com.masaibar.smartclipboard.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.masaibar.smartclipboard.services.ClipboardWatcherService;
import com.masaibar.smartclipboard.utils.DebugUtil;


public class BootCompletedReceiver extends BroadcastReceiver {

    private static final String TAG = "BootCompletedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }

        if (!TextUtils.equals(action, Intent.ACTION_BOOT_COMPLETED)) {
            return;
        }

        DebugUtil.log(TAG, "BootCompleted received.");

        ClipboardWatcherService.start(context);
    }
}
