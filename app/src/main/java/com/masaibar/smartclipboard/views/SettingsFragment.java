package com.masaibar.smartclipboard.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.masaibar.smartclipboard.FavoriteDBManager;
import com.masaibar.smartclipboard.R;
import com.masaibar.smartclipboard.entities.FavoriteData;
import com.masaibar.smartclipboard.utils.ClipboardUtil;

import java.util.List;

public class SettingsFragment extends Fragment {

    private int mCount = 1;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {

        Bundle args = new Bundle();

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {

        view.findViewById(R.id.button_insert_test_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ClipboardUtil(getContext()).copyToClipboard(String.valueOf("test" + mCount));
                mCount++;
            }
        });

        view.findViewById(R.id.button_clear_histories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        view.findViewById(R.id.button_clear_favorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteDBManager manager = new FavoriteDBManager(getContext());
                List<FavoriteData> favoriteDatas = manager.getAll();
                for (FavoriteData data : favoriteDatas) {
                    manager.deleteAt(data.id);
                }
            }
        });
    }
}
