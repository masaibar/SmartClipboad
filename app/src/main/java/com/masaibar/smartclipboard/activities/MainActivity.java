package com.masaibar.smartclipboard.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.masaibar.smartclipboard.ClipboardDBManager;
import com.masaibar.smartclipboard.services.ClipboardObserverService;
import com.masaibar.smartclipboard.HistoryAdapter;
import com.masaibar.smartclipboard.R;
import com.masaibar.smartclipboard.entities.ClipboardData;
import com.masaibar.smartclipboard.utils.ClipboardUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Intent getLaunchIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

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
        final HistoryAdapter adapter =
                new HistoryAdapter(context, datas, new HistoryAdapter.OnClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        DetailTextActivity.start(context, datas.get(position).text);
                    }

                    @Override
                    public void onCopyClick(int position) {
                        new ClipboardUtil(context).copyToClipboard(datas.get(position).text);
                    }
                });

        recyclerView.addItemDecoration(getDecoration(context));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            RecyclerView.ViewHolder target) {

                        //todo 並び替え実装(?)
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int fromPos = viewHolder.getAdapterPosition();
                        new ClipboardDBManager(context).deleteAt(datas.get(fromPos).id);
                        datas.remove(fromPos);
                        adapter.notifyItemRemoved(fromPos);
                    }
                }
        );
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private DividerItemDecoration getDecoration(Context c) {
        DividerItemDecoration decoration =
                new DividerItemDecoration(c, new LinearLayoutManager(c).getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(c, R.drawable.shape_divider));

        return decoration;
    }
}