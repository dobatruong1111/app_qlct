package com.example.projecttwonew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterOptionsInUser extends RecyclerView.Adapter<AdapterOptionsInUser.MyViewHolder2> {

    Context context;
    List<OptionsInUser> list;
    private static OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View v,int position);
    }

    public void setOnItemClickListener (OnItemClickListener itemClickListener){
        AdapterOptionsInUser.itemClickListener = itemClickListener;
    }

    public AdapterOptionsInUser(Context context, List<OptionsInUser> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_option_in_user,parent,false);
        MyViewHolder2 myViewHolder2 = new MyViewHolder2(v);
        return myViewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        holder.nameOp.setText(list.get(position).getName());
        holder.iconOp.setImageResource(list.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        private TextView nameOp;
        private ImageView iconOp;

        public MyViewHolder2(@NonNull final View itemView) {
            super(itemView);
            nameOp = (TextView) itemView.findViewById(R.id.name_option);
            iconOp = (ImageView) itemView.findViewById(R.id.icon_option);

            nameOp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.OnItemClick(itemView,getLayoutPosition());
                    }
                }
            });
        }
    }
}
