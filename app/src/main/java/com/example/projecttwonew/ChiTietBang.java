package com.example.projecttwonew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecttwonew.fragment.TableFragment;
import com.example.projecttwonew.object.Cards;
import com.example.projecttwonew.object.Table;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ChiTietBang extends AppCompatActivity {

    public Toolbar toolbar;

    RecyclerView recyclerView;
    AdapterListCard adapterListCard;
    ArrayList<Card> list;

    FloatingActionButton fab;

    TextInputEditText edtNameCard,edtNoteCard,edtTime;
    Button btAddCard,btCcCard;

    HashMap<String, Cards> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bang);
        AnhXa();

        Intent intent = getIntent();
        String tt = intent.getStringExtra("title");
        toolbar.setTitle(tt);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();
        //list.add(new Card(R.drawable.icon_work,"Card one","Đang Làm","1/1/2021"));
        //list.add(new Card(R.drawable.icon_work,"Card two","Đang Làm","6/1/2021"));

        adapterListCard = new AdapterListCard(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterListCard);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_addCard();
            }
        });

        adapterListCard.setOnItemClickListener(new AdapterListCard.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Intent intent = new Intent(ChiTietBang.this,ChiTietThe.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameCard",list.get(position).getNameCard());
                bundle.putString("time",list.get(position).getTime());
                intent.putExtra("dataCard",bundle);
                startActivity(intent);
            }
        });
        adapterListCard.setOnLongClickListener(new AdapterListCard.OnLongClickListener() {
            @Override
            public void OnLongClick(View v, int position) {
                dialog_delete(position);
            }
        });
    }

    private void dialog_delete(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông Báo");
        alert.setIcon(R.drawable.ic_baseline_delete_outline_24);
        alert.setMessage("Bạn có muốn xóa "+list.get(position).getNameCard()+" không ?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(position);
                adapterListCard.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
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

    private void dialog_addCard() {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_card);

        edtNameCard = (TextInputEditText) dialog.findViewById(R.id.edt_addNameCard);
        edtNoteCard = (TextInputEditText) dialog.findViewById(R.id.edt_addNoteCard);
        edtTime = (TextInputEditText) dialog.findViewById(R.id.edt_addTime);
        btAddCard = (Button) dialog.findViewById(R.id.bt_addCard);
        btCcCard = (Button) dialog.findViewById(R.id.bt_cancel);

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        btAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNameCard.getText().toString().trim().length() == 0) {
                    edtNameCard.setError("Tên Thẻ Trống");
                    edtNameCard.requestFocus();
                } else if (edtTime.getText().toString().trim().length() == 0) {
                    edtTime.setError("Không Có Thời Gian Hoàn Thành");
                    edtTime.requestFocus();
                } else if (KTTrung(list,edtNameCard.getText().toString().trim())){
                    Toast.makeText(ChiTietBang.this, "Trùng Tên Thẻ", Toast.LENGTH_SHORT).show();
                } else {
                    list.add(new Card(R.drawable.ic_baseline_work_outline_24,edtNameCard.getText().toString().trim(),
                            edtNoteCard.getText().toString().trim(),edtTime.getText().toString().trim()));
                    adapterListCard.notifyDataSetChanged();

                    hashMap.put(FirebaseDatabase.getInstance().getReference().push().getKey(),new Cards(edtNameCard.getText().toString().trim(),
                            edtNoteCard.getText().toString().trim(),edtTime.getText().toString()));

                    dialog.cancel();
                }
            }
        });
        btCcCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private boolean KTTrung(ArrayList<Card> list,String str) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNameCard().equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void ChonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,date);
        datePickerDialog.show();
    }

    private void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar_ctTable);
        fab = (FloatingActionButton) findViewById(R.id.fab_in_ctTable);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_listCard);
    }

}