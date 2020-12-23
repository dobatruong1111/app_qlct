package com.example.projecttwonew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projecttwonew.fragment.UserFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextInputLayout LayoutEmail,LayoutPass;
    TextInputEditText edtEmail,edtPass;
    ProgressBar pb;
    Button signUp,login;
    FirebaseAuth mAuth;
    CheckBox checkRememberPass;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,SignUp.class));
            }
        });

        sharedPreferences = getSharedPreferences("datalogin",MODE_PRIVATE);
        edtEmail.setText(sharedPreferences.getString("tk",""));
        edtPass.setText(sharedPreferences.getString("mk",""));
        checkRememberPass.setChecked(sharedPreferences.getBoolean("check",false));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                if (edtEmail.getText().toString().trim().length() == 0) {
                    pb.setVisibility(View.GONE);
                    edtEmail.setError("Không Được Để Trống");
                }
                else if (edtPass.getText().toString().trim().length() == 0) {
                    pb.setVisibility(View.GONE);
                    edtPass.setError("Không Được Để Trống");
                } else {
                    UserLogin();
                }
            }
        });
    }

    private void UserLogin() {
        final String email = edtEmail.getText().toString().trim();
        final String pass = edtPass.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Login.this,TrangChu.class);
                            startActivity(intent);
                            if (checkRememberPass.isChecked()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("tk",email);
                                editor.putString("mk",pass);
                                editor.putBoolean("check",true);
                                editor.commit();
                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("tk");
                                editor.remove("mk");
                                editor.remove("check");
                                editor.commit();
                            }
                        } else {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Tài Khoản Hoặc Mật Khẩu Không Đúng !", Toast.LENGTH_SHORT).show();
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
        LayoutEmail = (TextInputLayout) findViewById(R.id.text_input_layout_email);
        edtEmail = (TextInputEditText) findViewById(R.id.edt_email);
        LayoutPass = (TextInputLayout) findViewById(R.id.text_input_layout_pass);
        edtPass = (TextInputEditText) findViewById(R.id.edt_pass);
        login = (Button) findViewById(R.id.bt_login);
        signUp = (Button) findViewById(R.id.bt_signUp);
        pb = (ProgressBar) findViewById(R.id.progress_bar_login);
        checkRememberPass = (CheckBox) findViewById(R.id.check_remenber_pass);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }
}