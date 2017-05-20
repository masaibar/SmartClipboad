package com.masaibar.smartclipboard.services;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.masaibar.smartclipboard.ClipboardDBManager;
import com.masaibar.smartclipboard.ClipboardTextGetter;
import com.masaibar.smartclipboard.notifications.ResidentNotification;
import com.masaibar.smartclipboard.utils.ClipboardUtil;


public class ClipboardObserverService extends Service {

    public static void start(Context context) {
        context.startService(new Intent(context, ClipboardObserverService.class));
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, ClipboardObserverService.class));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final Context context = getApplicationContext();

        new ClipboardUtil(context)
                .addClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                    @Override
                    public void onPrimaryClipChanged() {
                        String text = new ClipboardTextGetter(context).getText();
                        new ClipboardDBManager(context).save(text);
                        new ResidentNotification(context)
                                .notifyIfNeeded(String.valueOf(text.length()), text);
                    }
                });
    }
}
