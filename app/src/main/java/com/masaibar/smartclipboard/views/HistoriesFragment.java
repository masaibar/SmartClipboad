package com.masaibar.smartclipboard.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.masaibar.smartclipboard.R;

public class HistoriesFragment extends Fragment {

    private static final String ARG_TEXT = "ARG_TEXT";

    public HistoriesFragment() {
    }

    public static HistoriesFragment newInstance() {

        Bundle args = new Bundle();

        HistoriesFragment fragment = new HistoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_histories, container, false);
    }
}
