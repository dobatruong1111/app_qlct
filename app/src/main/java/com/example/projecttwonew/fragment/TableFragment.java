package com.example.projecttwonew.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentQueryMap;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttwonew.AdapterListTable;
import com.example.projecttwonew.ChiTietBang;
import com.example.projecttwonew.ListTable;
import com.example.projecttwonew.R;
import com.example.projecttwonew.object.Table;
import com.example.projecttwonew.object.Table;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableFragment extends Fragment {

    View v;
    Toolbar toolbar;
    FloatingActionButton fab;

    Button btAdd,btCancel;
    TextInputEditText edtTable;
    TextInputLayout layoutEdt;

    ArrayList<ListTable> tables; // quản lý ds bảng trên giao diện
    RecyclerView rView;
    AdapterListTable adapter;

    private int countWorks = 0;

    DatabaseReference data;
    FirebaseAuth mAuth;

    //ArrayList<Table> list = new ArrayList<>();
    HashMap<String, Table> hashMap = new HashMap<>(); // quản lý ds bảng trên firebase

    onClickListTable clickListTable;

    public interface onClickListTable {
        void sendData(String title);
    }

    public TableFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_table,container,false);

        toolbar = (Toolbar) v.findViewById(R.id.tool_bar_table);
        toolbar.setTitle("Bảng");
        tables = new ArrayList<>();
        rView = (RecyclerView) v.findViewById(R.id.recycler_view);
        adapter = new AdapterListTable(getContext(),tables);
        rView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rView.setAdapter(adapter);

        selectDataUser();

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_add_table();
            }
        });

        adapter.setOnItemClickListener(new AdapterListTable.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                clickListTable.sendData(tables.get(position).getNameTable());
            }
        });
        adapter.setOnLongClickListener(new AdapterListTable.OnLongClickListener() {
            @Override
            public void OnLongClick(View v, int position) {
                dialog_delete(position);
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        clickListTable = (onClickListTable) context;
    }

    public void selectDataUser() {

        FirebaseDatabase.getInstance().getReference("NguoiDung").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("dsBangCongViec").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                tables.add(new ListTable(dataSnapshot.child("nameTable").getValue().toString(),R.drawable.ic_baseline_table_chart_24));
                //Toast.makeText(getActivity(),dataSnapshot.child("nameTable").getValue().toString(), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void dialog_delete(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Thông Báo");
        alert.setIcon(R.drawable.ic_baseline_delete_outline_24);
        alert.setMessage("Bạn có muốn xóa "+tables.get(position).getNameTable()+" không ?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                tables.remove(position);
                adapter.notifyDataSetChanged();

            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }

    private void dialog_add_table(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_table);
        dialog.setCanceledOnTouchOutside(true);

        btAdd = (Button) dialog.findViewById(R.id.bt_addTable);
        btCancel = (Button) dialog.findViewById(R.id.bt_cancel);
        edtTable = (TextInputEditText) dialog.findViewById(R.id.edt_addTable);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("NguoiDung").child(firebaseUser.getUid()).child("countWorks")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        countWorks = dataSnapshot.getValue(Integer.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
//        FirebaseDatabase.getInstance().getReference().child("NguoiDung").child(firebaseUser.getUid()).child("dsBangCongViec")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            hashMap.clear();
//                            for (DataSnapshot dss : dataSnapshot.getChildren()){
//                                Table table = dss.getValue(Table.class);
//                                hashMap.put(dataSnapshot.getKey(),table);
//                            }
//                        }
//                }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });

        FirebaseDatabase.getInstance().getReference("NguoiDung").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("dsBangCongViec").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Table table = dataSnapshot.getValue(Table.class);
                    hashMap.put(dataSnapshot.getKey(),table);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTable.getText().toString().trim().length() == 0) {
                    edtTable.setError("Tên Bảng Trống");
                    edtTable.requestFocus();
                } else if (KTTrung(tables, edtTable.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "Tên Bảng Trùng", Toast.LENGTH_SHORT).show();
                } else {
                    //tables.add(new ListTable(edtTable.getText().toString().trim(),R.drawable.ic_baseline_table_chart_24));
                    //list.add(new Table(edtTable.getText().toString().trim()));
                    //adapter.notifyDataSetChanged();
                    String key = FirebaseDatabase.getInstance().getReference().push().getKey();
                    hashMap.put(key,new Table(edtTable.getText().toString().trim()));
                    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser != null) {
                        String str = firebaseUser.getUid();
                        FirebaseDatabase.getInstance().getReference("NguoiDung").
                                child(str).child("countWorks").setValue(countWorks + 1);

                        FirebaseDatabase.getInstance().getReference("NguoiDung")
                                .child(str).child("dsBangCongViec").setValue(hashMap);
                    }
                    dialog.cancel();
                }
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private boolean KTTrung(ArrayList<ListTable> list,String string) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNameTable().equals(string)) {
                return true;
            }
        }
        return false;
    }
}
