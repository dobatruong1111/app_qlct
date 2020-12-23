package com.example.projecttwonew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.projecttwonew.fragment.CardFragment;
import com.example.projecttwonew.fragment.HomeFragment;
import com.example.projecttwonew.fragment.TableFragment;
import com.example.projecttwonew.fragment.ThongBaoFragment;
import com.example.projecttwonew.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TrangChu extends AppCompatActivity implements TableFragment.onClickListTable {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout_container);
        bottomNavigationView.setOnNavigationItemSelectedListener(btN);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_container,
                new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener btN = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_tb:
                    fragment = new ThongBaoFragment();
                    break;
                case R.id.nav_table:
                    fragment = new TableFragment();
                    break;
                case R.id.nav_card:
                    fragment = new CardFragment();
                    break;
                case R.id.nav_user:
                    fragment = new UserFragment();
                    break;
            }
            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_container,
                    fragment).commit();
            return true;
        }
    };

    @Override
    public void sendData(String title) {
        Intent intent = new Intent(this,ChiTietBang.class);
        intent.putExtra("title",title);
        startActivity(intent);
    }
}