package com.masaibar.smartclipboard;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.masaibar.smartclipboard.entities.FavoriteData;
import com.masaibar.smartclipboard.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    public interface OnClickListener {
        void onItemClick(int position);

        void onCopyClick(int position);
    }

    private LayoutInflater mInflater;
    private List<FavoriteData> mDatas;
    private List<FavoriteData> mDatasToDelete;
    private OnClickListener mListener;

    public FavoriteAdapter(Context context, List<FavoriteData> datas, OnClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>(datas);
        mDatasToDelete = new ArrayList<>();
        mListener = listener;
    }

    public void onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        DebugUtil.log("!!!", String.format("%s => %s", fromPosition, toPosition));

        //todo dbの並び替え実装
        //todo idを詰める処理
    }

    public void onItemRemove(RecyclerView.ViewHolder viewHolder, RecyclerView recyclerView) {
        final int position = viewHolder.getAdapterPosition();
        final FavoriteData data = mDatas.get(position);

        Snackbar.make(recyclerView, "removed", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatas.add(position, data);
                        notifyItemInserted(position);
                        mDatasToDelete.remove(data);
                    }
                }).show();
        mDatas.remove(position);
        notifyItemRemoved(position);
        mDatasToDelete.add(data);
    }

    public void deleteList(Context context) {
        FavoriteDBManager dbManager = new FavoriteDBManager(context);
        for (FavoriteData data : mDatasToDelete) {
            dbManager.deleteAt(data.id);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mDatas == null || mDatas.size() <= position || mDatas.get(position) == null) {
            return;
        }

        final FavoriteData data = mDatas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
        holder.mTextDate.setText(String.valueOf(data.time));
        holder.mTextContent.setText(data.text);
        holder.mTextCount.setText(String.valueOf(data.getLength()));
        holder.mImageCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCopyClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.layout_history_item, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextContent;
        TextView mTextDate;
        TextView mTextCount;
        ImageView mImageCopy;

        private ViewHolder(View itemView) {
            super(itemView);
            mTextDate = (TextView) itemView.findViewById(R.id.text_date);
            mTextContent = (TextView) itemView.findViewById(R.id.text_content);
            mTextCount = (TextView) itemView.findViewById(R.id.text_count);
            mImageCopy = (ImageView) itemView.findViewById(R.id.image_copy);
        }
    }
}
