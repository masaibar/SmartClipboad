package com.masaibar.smartclipboard;

import android.app.Application;
import android.content.Context;

import com.masaibar.smartclipboard.entities.OrmaDatabase;

public class App extends Application {

    private static OrmaDatabase sOrmaDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static OrmaDatabase getOrma(Context context) {
        if (sOrmaDatabase == null) {
            sOrmaDatabase = OrmaDatabase.builder(context).build();
        }

        return sOrmaDatabase;
    }
}
