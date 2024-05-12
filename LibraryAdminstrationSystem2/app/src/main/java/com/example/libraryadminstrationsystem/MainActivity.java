package com.example.libraryadminstrationsystem;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libraryadminstrationsystem.databinding.ActivityMainBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          binding = ActivityMainBinding.inflate(getLayoutInflater());
          setContentView(binding.getRoot());
          replaceFragment(new FindFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.borrow) {
                replaceFragment(new BorrowFragment());
            } else if (item.getItemId() == R.id.find) {
                replaceFragment(new FindFragment());
            } else if (item.getItemId() == R.id.returnn) {
                replaceFragment(new ReturnFragment());
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
