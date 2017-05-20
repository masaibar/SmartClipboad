package com.masaibar.smartclipboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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
        final Context context = getApplicationContext();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final ArrayList<ClipboardData> datas =
                new ClipboardDBManager(getApplicationContext()).getAll();
        HistoryAdapter adapter = new HistoryAdapter(context, datas);

        recyclerView.addItemDecoration(getDecoration(context));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, datas.get(position).text, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private DividerItemDecoration getDecoration(Context c) {
        DividerItemDecoration decoration =
                new DividerItemDecoration(c, new LinearLayoutManager(c).getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(c, R.drawable.shape_divider));

        return decoration;
    }
}