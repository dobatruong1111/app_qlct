package com.example.projecttwonew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projecttwonew.object.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    TextInputEditText edtPassNew,edtCFPassNew,edtEmailSignUp;
    Button btSignUp;
    ProgressBar pb;
    FirebaseAuth mAuth;
    DatabaseReference data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnhXa();

        mAuth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                if (edtEmailSignUp.getText().toString().trim().length() == 0) {
                    pb.setVisibility(View.GONE);
                    edtPassNew.setError("Không Được Để Trống");
                } else if (edtPassNew.getText().toString().trim().length() < 6) {
                    pb.setVisibility(View.GONE);
                    edtPassNew.setError("Tối Thiểu 6 Ký Tự");
                } else if (edtCFPassNew.getText().toString().trim().length() == 0) {
                    pb.setVisibility(View.GONE);
                    edtCFPassNew.setError("Không Được Để Trống");
                } else if (!edtPassNew.getText().toString().trim().equals(edtCFPassNew.getText().toString().trim())) {
                    pb.setVisibility(View.GONE);
                    edtCFPassNew.setError("Không Trùng Với Mật Khẩu Mới");
                } else {
                    final String email = edtEmailSignUp.getText().toString().trim();
                    final String pass = edtPassNew.getText().toString().trim();
                    mAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        User user = new User(email,pass);
                                        if (firebaseUser != null) {
                                            String str = firebaseUser.getUid();
                                            data.child("NguoiDung").child(str).setValue(user);
                                        }
                                        Toast.makeText(SignUp.this, "Đã Đăng Ký", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    } else {
                                        Toast.makeText(SignUp.this, "Đăng Ký Thất Bại", Toast.LENGTH_SHORT).show();
                                        pb.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pb.setVisibility(View.GONE);
    }

    private void AnhXa() {
        edtPassNew = (TextInputEditText) findViewById(R.id.edt_pass_new);
        edtCFPassNew = (TextInputEditText) findViewById(R.id.edt_cfPass_new);
        edtEmailSignUp = (TextInputEditText) findViewById(R.id.edt_email_signUp);
        btSignUp = (Button) findViewById(R.id.bt_create);
        pb = (ProgressBar) findViewById(R.id.progress_bar_signUp);
    }
}