package com.masaibar.smartclipboard;

import android.content.Context;

import com.facebook.stetho.Stetho;

public class DebugApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();

        initStetho(getApplicationContext());
    }

    private void initStetho(Context context) {
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                        .build()
        );
    }
}
