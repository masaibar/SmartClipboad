package com.masaibar.smartclipboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.masaibar.smartclipboard.entities.ClipboardData;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public interface OnClickListener {
        void onItemClick(int position);

        void onCopyClick(int position);
    }

    private LayoutInflater mInflater;
    private ArrayList<ClipboardData> mDatas;
    private OnClickListener mListener;

    public HistoryAdapter(Context context, ArrayList<ClipboardData> datas, OnClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mDatas == null || mDatas.size() <= position || mDatas.get(position) == null) {
            return;
        }

        final ClipboardData data = mDatas.get(position);
        holder.mViewRoot.setOnClickListener(new View.OnClickListener() {
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
        View mViewRoot;
        TextView mTextContent;
        TextView mTextDate;
        TextView mTextCount;
        ImageView mImageCopy;

        private ViewHolder(View itemView) {
            super(itemView);
            mViewRoot = itemView.findViewById(R.id.linear_history_item);
            mTextDate = (TextView) itemView.findViewById(R.id.text_date);
            mTextContent = (TextView) itemView.findViewById(R.id.text_content);
            mTextCount = (TextView) itemView.findViewById(R.id.text_count);
            mImageCopy = (ImageView) itemView.findViewById(R.id.image_copy);
        }
    }
}
