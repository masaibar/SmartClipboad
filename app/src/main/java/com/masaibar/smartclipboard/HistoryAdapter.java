package com.masaibar.smartclipboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.masaibar.smartclipboard.entities.ClipboardData;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<ClipboardData> mDatas;

    public HistoryAdapter(Context context, ArrayList<ClipboardData> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDatas == null || mDatas.size() <= position || mDatas.get(position) == null) {
            return;
        }

        ClipboardData data = mDatas.get(position);
        holder.mTextDate.setText(String.valueOf(data.time));
        holder.mTextContent.setText(data.text);
        holder.mTextCount.setText(String.valueOf(data.getLength()));
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

        private ViewHolder(View itemView) {
            super(itemView);
            mTextDate = (TextView) itemView.findViewById(R.id.text_date);
            mTextContent = (TextView) itemView.findViewById(R.id.text_content);
            mTextCount = (TextView) itemView.findViewById(R.id.text_count);
        }
    }
}
