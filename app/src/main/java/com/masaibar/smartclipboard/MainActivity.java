package com.masaibar.smartclipboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.masaibar.smartclipboard.entities.ClipboardData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClipboardObserverService.start(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Context context = getApplicationContext();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ArrayList<ClipboardData> datas = new ClipboardDBManager(getApplicationContext()).getAll();
        HistoryAdapter adapter = new HistoryAdapter(context, datas);

        recyclerView.addItemDecoration(getDecoration(context));
        recyclerView.setAdapter(adapter);
    }

    private DividerItemDecoration getDecoration(Context c) {
        DividerItemDecoration decoration =
                new DividerItemDecoration(c, new LinearLayoutManager(c).getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(c, R.drawable.shape_divider));

        return decoration;
    }
}