package com.masaibar.smartclipboard.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.masaibar.smartclipboard.R;

public class DetailTextActivity extends AppCompatActivity {

    private static final String KEY_TEXT = "text";

    public static void start(Context context, String text) {
        Intent intent = new Intent(context, DetailTextActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_TEXT, text);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_text);

        ((TextView) findViewById(R.id.text_target)).setText(getText(getIntent()));
    }

    private String getText(Intent intent) {
        if (intent == null) {
            return null;
        }

        return intent.getStringExtra(KEY_TEXT);
    }
}
