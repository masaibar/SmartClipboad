package com.masaibar.smartclipboard;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.masaibar.smartclipboard.utils.DebugUtil;

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

        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        manager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                String text = new ClipboardTextGetter(getApplicationContext()).getText();
                DebugUtil.log("!!!", String.format("result: %s", text));

                //todo insert database
                new ClipboardDBManager(getApplicationContext()).save(text);
            }
        });
    }
}
