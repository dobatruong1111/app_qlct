package com.example.projecttwonew.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttwonew.AdapterOptionsInUser;
import com.example.projecttwonew.Login;
import com.example.projecttwonew.OptionsInUser;
import com.example.projecttwonew.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    View v;
    ArrayList<OptionsInUser> listOption;
    AdapterOptionsInUser adapter;
    TextView nameU;
    RecyclerView recyclerView;

    public UserFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_user,container,false);

        nameU = (TextView) v.findViewById(R.id.name_user);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_in_user);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            nameU.setText(userEmail);
        }

        listOption = new ArrayList<>();
        listOption.add(new OptionsInUser("Cài Đặt",R.drawable.ic_baseline_settings_24));
        listOption.add(new OptionsInUser("Trợ Giúp",R.drawable.ic_baseline_live_help_24));
        listOption.add(new OptionsInUser("Đăng Xuất",R.drawable.ic_baseline_arrow_forward_ios_24));
        adapter = new AdapterOptionsInUser(getContext(),listOption);

        adapter.setOnItemClickListener(new AdapterOptionsInUser.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                if (position == 2) {
                    dialog_logout();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    private void dialog_logout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Thông Báo");
        alert.setIcon(R.drawable.ic_baseline_arrow_forward_ios_24);
        alert.setMessage("Bạn Có Muốn Đăng Xuất Không ?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

}
