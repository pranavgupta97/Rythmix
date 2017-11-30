package com.example.pranav.karaoke;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ibra on 11/28/2017.
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {
    private String[] item;
    public  HorizontalAdapter(String[] item){
        this.item = item;
    }
    @Override
    public HorizontalAdapter.HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalAdapter.HorizontalViewHolder holder, int position) {
        holder.text.setText(item[position]);

    }

    @Override
    public int getItemCount() {
        return item.length;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public HorizontalViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.usernameItem);

        }
    }
}
