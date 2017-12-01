package com.example.pranav.karaoke;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ibra on 11/28/2017.
 */

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder> {
    private String[] item;
    public  VerticalAdapter(String[] item){
        this.item = item;
    }
    @Override
    public VerticalAdapter.VerticalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VerticalAdapter.VerticalViewHolder holder, int position) {
        holder.text.setText(item[position]);

    }

    @Override
    public int getItemCount() {
        return item.length;
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public VerticalViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.usernameItem);

        }
    }
}
