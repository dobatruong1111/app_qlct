package com.example.projecttwonew.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.projecttwonew.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThongBaoFragment extends Fragment {

    Toolbar toolbar;
    View v;
    TextView txtTb;
    DatabaseReference dataThongBao;
    ImageView icon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_thongbao,container,false);

        icon = v.findViewById(R.id.icon_thongbao);
        toolbar = (Toolbar) v.findViewById(R.id.tool_bar_thong_bao);
        toolbar.setTitle("Thông Báo");

        txtTb = v.findViewById(R.id.txt_ThongBao);
        dataThongBao = FirebaseDatabase.getInstance().getReference("ThongBaoUser");
        dataThongBao.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtTb.setText(dataSnapshot.getValue().toString());
                txtTb.setVisibility(View.VISIBLE);
                icon.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return v;
    }

}
