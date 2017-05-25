package com.masaibar.smartclipboard;

import android.content.Context;

public abstract class DBManager {
    private Context mContext;

    public DBManager(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void save(String text);

    public abstract void deleteAt(final long id);
}
