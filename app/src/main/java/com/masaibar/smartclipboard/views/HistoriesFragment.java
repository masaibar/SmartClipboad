package com.masaibar.smartclipboard.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.masaibar.smartclipboard.FavoriteDBManager;
import com.masaibar.smartclipboard.HistoryDBManager;
import com.masaibar.smartclipboard.HistoryAdapter;
import com.masaibar.smartclipboard.R;
import com.masaibar.smartclipboard.entities.HistoryData;
import com.masaibar.smartclipboard.utils.ClipboardUtil;
import com.masaibar.smartclipboard.utils.DebugUtil;

import java.util.List;

public class HistoriesFragment extends Fragment {

    public static HistoriesFragment newInstance() {

        Bundle args = new Bundle();

        HistoriesFragment fragment = new HistoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private HistoryAdapter mAdapter;

    public HistoriesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_histories, container, false);
        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.deleteList(getContext());
        }
    }

    private void setupRecyclerView(final View view) {
        final Context context = getContext();
        final RecyclerView recyclerView =
                (RecyclerView) view.findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final List<HistoryData> datas =
                new HistoryDBManager(getContext()).getAll();
        mAdapter = new HistoryAdapter(context, datas, new HistoryAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                DetailTextActivity.start(context, datas.get(position).text);
            }

            @Override
            public void onItemLongClick(int position) {
                DebugUtil.log("!!!", "long");
            }

            @Override
            public void onCopyClick(int position) {
                new ClipboardUtil(context).copyToClipboard(datas.get(position).text);
            }
        });

        recyclerView.addItemDecoration(new Divider(context).get());
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            RecyclerView.ViewHolder target) {

                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        switch (direction) {
                            case ItemTouchHelper.LEFT:
                                mAdapter.onItemRemove(viewHolder, recyclerView);
                                break;

                            //todo とりあえず暫定対応
                            case ItemTouchHelper.RIGHT:
                                String text = datas.get(viewHolder.getAdapterPosition()).text;
                                new FavoriteDBManager(getContext()).save(text);
                                break;

                                default:
                                    break;
                        }
                    }
                }
        );
        touchHelper.attachToRecyclerView(recyclerView);
    }
}
