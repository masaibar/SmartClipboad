package com.masaibar.smartclipboard.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.masaibar.smartclipboard.ClipboardDBManager;
import com.masaibar.smartclipboard.HistoryAdapter;
import com.masaibar.smartclipboard.R;
import com.masaibar.smartclipboard.entities.ClipboardData;
import com.masaibar.smartclipboard.utils.ClipboardUtil;

import java.util.ArrayList;

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
        View view = inflater.inflate(R.layout.fragment_histories, container, false);
        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        final Context context = getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final ArrayList<ClipboardData> datas =
                new ClipboardDBManager(getContext()).getAll();
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
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
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
