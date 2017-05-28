package com.masaibar.smartclipboard.services;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.masaibar.smartclipboard.ClipboardTextGetter;
import com.masaibar.smartclipboard.HistoryDBManager;
import com.masaibar.smartclipboard.notifications.ResidentNotification;
import com.masaibar.smartclipboard.utils.ClipboardUtil;


public class ClipboardWatcherService extends Service {

    public static void start(Context context) {
        context.startService(new Intent(context, ClipboardWatcherService.class));
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, ClipboardWatcherService.class));
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
                        if (TextUtils.isEmpty(text)) {
                            return;
                        }

                        new HistoryDBManager(context).save(text);
                        notifyIfNeeded(text);
                    }
                });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }

        String text = new HistoryDBManager(getApplicationContext()).getLatestText();

        if (TextUtils.isEmpty(text)) {
            return START_STICKY;
        }

        notifyIfNeeded(text);

        return START_STICKY;
    }

    private void notifyIfNeeded(String text) {
        new ResidentNotification(getApplicationContext())
                .notifyIfNeeded(String.valueOf(text.length()), text);
    }
}
