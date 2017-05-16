package com.masaibar.smartclipboard.utils;

import android.util.Log;

import com.masaibar.smartclipboard.BuildConfig;


public class DebugUtil {
    public static boolean isDebug() {
        return BuildConfig.BUILD_TYPE.equals("debug");
    }

    public static boolean isRelease() {
        return BuildConfig.BUILD_TYPE.equals("release");
    }

    public static void catchException(Throwable t) {
        if (isRelease()) {
            return;
        }
        t.printStackTrace();
    }

    public static void log(String tag, String str) {
        if (isRelease()) {
            return;
        }

        Log.d(tag, str);
    }

    public static void log(String tag, String format, Object... args) {
        if (isRelease()) {
            return;
        }

        log(tag, String.format(format, args));
    }
}

