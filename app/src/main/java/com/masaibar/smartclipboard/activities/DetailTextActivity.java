package com.masaibar.smartclipboard.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.masaibar.smartclipboard.R;

public class DetailTextActivity extends AppCompatActivity {

    private static final String KEY_TEXT = "text";

    public static void start(Context context, String text) {
        Log.d("!!!", text);
        Intent intent = new Intent(context, DetailTextActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_TEXT, text);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_text);
    }
}
