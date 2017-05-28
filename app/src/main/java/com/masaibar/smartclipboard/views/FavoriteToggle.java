package com.masaibar.smartclipboard.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import com.masaibar.smartclipboard.FavoriteDBManager;
import com.masaibar.smartclipboard.R;

public class FavoriteToggle extends FrameLayout {

    private Context mContext;

    public FavoriteToggle(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        inflate(context, R.layout.layout_favorite_toggle, this);
        mContext = context;
    }

    public void init(final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        final FavoriteDBManager manager = new FavoriteDBManager(mContext);

        //初期状態の設定
        boolean isFavorite = manager.isExist(text);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle_button_favorite);
        toggleButton.setChecked(isFavorite);

        //状態On/Offによる保存と削除
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    manager.save(text);
                } else {
                    manager.delete(text);
                }
            }
        });
    }
}
