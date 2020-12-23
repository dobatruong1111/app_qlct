package com.example.projecttwonew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterListTable extends RecyclerView.Adapter<AdapterListTable.MyViewHolder> {

    Context context;
    List<ListTable> list;

    private static OnItemClickListener itemClickListener;
    private static OnLongClickListener longClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View v,int position);
    }

    public void setOnItemClickListener (OnItemClickListener itemClickListener){
        AdapterListTable.itemClickListener = itemClickListener;
    }

    public interface OnLongClickListener {
        void OnLongClick(View v,int position);
    }

    public void setOnLongClickListener(OnLongClickListener longClickListener) {
        AdapterListTable.longClickListener = longClickListener;
    }

    public AdapterListTable(Context context, List<ListTable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(context).inflate(R.layout.item_table,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameT.setText(list.get(position).getNameTable());
        holder.iconT.setImageResource(list.get(position).getIconTable());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameT;
        private ImageView iconT;
        private LinearLayout linearLayoutTable;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            nameT = (TextView) itemView.findViewById(R.id.name_table);
            iconT = (ImageView) itemView.findViewById(R.id.icon_table);
            linearLayoutTable = itemView.findViewById(R.id.liner_layout_table);

            linearLayoutTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.OnItemClick(itemView,getLayoutPosition());
                    }
                }
            });
            linearLayoutTable.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null) {
                        longClickListener.OnLongClick(itemView,getLayoutPosition());
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
