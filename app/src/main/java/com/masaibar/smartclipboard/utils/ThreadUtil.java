package com.masaibar.smartclipboard.utils;


import android.os.Handler;
import android.os.Looper;

public final class ThreadUtil {

    private static Handler sHandler;

    public static void setup() {
        sHandler = new Handler();
    }

    public static void runOnUIThread(Runnable runnable) {
        if (isUIThread()) {
            runnable.run();
        } else {
            sHandler.post(runnable);
        }
    }

    public static void runOnUIThread(long millisec, Runnable runnable) {
        sHandler.postDelayed(runnable, millisec);
    }

    public static void runOnBackgroundThread(final Runnable runnable) {
        new Thread() {
            @Override
            public void run() {
                runnable.run();
            }
        }.start();
    }

    public static void runOnBackgroundThread(long millisec, Runnable runnable) {
        runOnUIThread(millisec, runnable);
    }

    public static boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
