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
import com.masaibar.smartclipboard.FavoriteAdapter;
import com.masaibar.smartclipboard.R;
import com.masaibar.smartclipboard.entities.FavoriteData;
import com.masaibar.smartclipboard.utils.ClipboardUtil;

import java.util.List;

public class FavoritesFragment extends Fragment {

    public static FavoritesFragment newInstance() {

        Bundle args = new Bundle();

        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FavoriteAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public FavoritesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_favorite);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        updateRecyclerView();
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
                    @Override
                    public boolean onMove(
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            RecyclerView.ViewHolder target) {

                        mAdapter.onItemMoved(
                                viewHolder.getAdapterPosition(), target.getAdapterPosition());

                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        //todo 横スワイプはしない(削除はなんとかする)
                    }
                }
        );
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void updateRecyclerView() {
        final Context context = getContext();
        final List<FavoriteData> datas =
                new FavoriteDBManager(getContext()).getAll();
        mAdapter = new FavoriteAdapter(context, datas, new FavoriteAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                DetailTextActivity.start(context, datas.get(position).text);
            }

            @Override
            public void onCopyClick(int position) {
                new ClipboardUtil(context).copyToClipboard(datas.get(position).text);
            }
        });

        mRecyclerView.addItemDecoration(new Divider(context).get());
        mRecyclerView.setAdapter(mAdapter);
    }
}
