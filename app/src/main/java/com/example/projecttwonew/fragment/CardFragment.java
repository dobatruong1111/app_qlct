package com.example.projecttwonew.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projecttwonew.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CardFragment extends Fragment {

    View v;
    Toolbar toolbar;
    TextView txtMC;
    DatabaseReference mData;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_card,container,false);

        toolbar = (Toolbar) v.findViewById(R.id.tool_bar_card);
        toolbar.setTitle("Thẻ Của Tôi");
        txtMC = v.findViewById(R.id.txt_MyCard);
        FirebaseDatabase.getInstance().getReference("MyCard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtMC.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return v;
    }
}