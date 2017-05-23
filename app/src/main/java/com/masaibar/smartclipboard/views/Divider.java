package com.masaibar.smartclipboard.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.masaibar.smartclipboard.R;

public class Divider {

    private Context mContext;

    public Divider(Context context) {
        mContext = context;
    }

    public DividerItemDecoration get() {
        DividerItemDecoration decoration = new DividerItemDecoration(
                mContext,
                new LinearLayoutManager(mContext).getOrientation()
        );

        decoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.shape_divider));

        return decoration;
    }
}
