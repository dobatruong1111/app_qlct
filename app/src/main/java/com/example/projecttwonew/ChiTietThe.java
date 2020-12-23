package com.example.projecttwonew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChiTietThe extends AppCompatActivity {

    Toolbar toolbar;
    ListView listWork;
    EditText edtAddWork;
    ImageView enterAddWork;
    LinearLayout ll;
    ArrayAdapter<NameWork> adapter;
    ArrayList<NameWork> dsWorks;

    EditText edtMieuTaThe;
    TextView txtMTT;
    ImageView iconAddMTT;
    TextView txtNgayHetHan,txtAddUser,txtAddFile,txtListWorks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_the);
        AnhXa();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dataCard");
        if (bundle != null) {
            String nameCard = bundle.getString("nameCard");
            String time = bundle.getString("time");
            toolbar.setTitle(nameCard);
            txtNgayHetHan.append(" "+time);
        } else {
            toolbar.setTitle("Chi Tiết Thẻ");
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        dsWorks = new ArrayList<>();

        adapter = new ArrayAdapter<NameWork>(this,
                android.R.layout.simple_list_item_multiple_choice,dsWorks);
        listWork.setAdapter(adapter);
        for (int i = 0; i < dsWorks.size(); i++) {
            listWork.setItemChecked(i,dsWorks.get(i).isActive());
        }

        listWork.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listWork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                boolean currentCheck = checkedTextView.isChecked();
                NameWork nameWork = (NameWork) listWork.getItemAtPosition(position);
                nameWork.setActive(!currentCheck);
            }
        });

        enterAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddWork.getText().toString().trim().length() == 0) {
                    edtAddWork.setError("Hãy Điền Tên Công Việc");
                    edtAddWork.requestFocus();
                } else if (KTT(dsWorks,edtAddWork.getText().toString().trim())) {
                    edtAddWork.setError("Tên Công Việc Trùng");
                    edtAddWork.requestFocus();
                } else {
                    dsWorks.add(0,new NameWork(edtAddWork.getText().toString().trim()));
                    adapter.notifyDataSetChanged();
                    edtAddWork.setText("");
                }
            }
        });

        iconAddMTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMTT.append(" "+edtMieuTaThe.getText().toString().trim());
                edtMieuTaThe.setText("");
            }
        });

        txtListWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = listWork.getVisibility();
                int b = ll.getVisibility();
                if (a == 8 && b == 8) {
                    listWork.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.VISIBLE);
                } else if (a == 0 && b == 0) {
                    listWork.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_chitietthe,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean KTT(List<NameWork> list,String str){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNameW().equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar_in_chitietCard);
        listWork = findViewById(R.id.list_works);
        edtAddWork = (EditText) findViewById(R.id.edt_addWork);
        enterAddWork = (ImageView) findViewById(R.id.icon_enter_AddWork);
        ll = (LinearLayout) findViewById(R.id.addNameWorks);

        edtMieuTaThe = (EditText) findViewById(R.id.edt_csMieuTaThe);
        iconAddMTT = (ImageView) findViewById(R.id.icon_addMieuTaThe);
        txtMTT = (TextView) findViewById(R.id.mieu_ta_the);
        txtAddUser = (TextView) findViewById(R.id.txt_addUser);
        txtNgayHetHan = (TextView) findViewById(R.id.txt_NgayHetHan);
        txtAddFile = (TextView) findViewById(R.id.txt_file);
        txtListWorks = (TextView) findViewById(R.id.txt_dsCongViec);
    }
}