package com.example.projecttwonew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterListCard extends RecyclerView.Adapter<AdapterListCard.MyViewHolder3> {

    Context context;
    List<Card> cardList;

    private static OnItemClickListener itemClickListener;
    private static OnLongClickListener longClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View v,int position);
    }

    public void setOnItemClickListener (OnItemClickListener itemClickListener){
        AdapterListCard.itemClickListener = itemClickListener;
    }

    public interface OnLongClickListener {
        void OnLongClick(View v,int position);
    }

    public void setOnLongClickListener(OnLongClickListener longClickListener) {
        AdapterListCard.longClickListener = longClickListener;
    }

    public AdapterListCard(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_card,parent,false);
        MyViewHolder3 myViewHolder3 = new MyViewHolder3(v);
        return myViewHolder3;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        holder.iconCard.setImageResource(cardList.get(position).getIconCard());
        holder.nameCard.setText(cardList.get(position).getNameCard());
        holder.noteCard.setText(cardList.get(position).getNoteCard());
        holder.time.setText(cardList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class MyViewHolder3 extends RecyclerView.ViewHolder{

        private ImageView iconCard;
        private TextView nameCard;
        private TextView noteCard;
        private TextView time;
        private LinearLayout linearLayoutCard;

        public MyViewHolder3(@NonNull final View itemView) {
            super(itemView);
            iconCard = (ImageView) itemView.findViewById(R.id.icon_card);
            nameCard = (TextView) itemView.findViewById(R.id.name_card);
            noteCard = itemView.findViewById(R.id.note_card);
            time = itemView.findViewById(R.id.time);
            linearLayoutCard = itemView.findViewById(R.id.liner_layout_card);

            linearLayoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.OnItemClick(itemView,getLayoutPosition());
                    }
                }
            });
            linearLayoutCard.setOnLongClickListener(new View.OnLongClickListener() {
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
