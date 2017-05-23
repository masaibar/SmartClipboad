package com.masaibar.smartclipboard.views;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.masaibar.smartclipboard.R;
import com.masaibar.smartclipboard.services.ClipboardObserverService;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClipboardObserverService.start(this);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment selectedFragment = null;
                switch (tabId) {
                    case R.id.tab_histories:
                        selectedFragment = HistoriesFragment.newInstance();
                        break;

                    case R.id.tab_favorites:
                        selectedFragment = FavoritesFragment.newInstance();
                        break;

                    case R.id.tab_settings:
                        selectedFragment = SettingsFragment.newInstance();
                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_selected, selectedFragment);
                transaction.commit();
            }
        });
    }
}