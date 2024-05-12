package com.example.libraryadminstrationsystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityBorrow extends AppCompatActivity {

    EditText etStudentID, etBookID;
    Button btnConfirm;
    DBHelperBorrow dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_borrow);

        etStudentID = findViewById(R.id.etStudentID);
        etBookID = findViewById(R.id.etBookID);
        dbHelper = new DBHelperBorrow(this);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonConfirmClicked();
            }
        });
    }

    public void onButtonConfirmClicked() {
        String student = etStudentID.getText().toString();
        String book = etBookID.getText().toString();

        if (TextUtils.isEmpty(student) || TextUtils.isEmpty(book)) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        } else {
            boolean registeredSuccess = dbHelper.insertData(student, book, new DBHelperBooks(this));
            if (registeredSuccess) {
                Toast.makeText(this, "Borrow Confirmed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Borrow Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
