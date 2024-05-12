package com.example.libraryadminstrationsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAdd extends AppCompatActivity {
    EditText title, author, amount;
    Button btnAdd;
    DBHelperBooks dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        amount = findViewById(R.id.amount);
        btnAdd = findViewById(R.id.btnAdd);
        dbHelper = new DBHelperBooks(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleTXT = title.getText().toString();
                String authorTXT = author.getText().toString();
                String amountTXTStr = amount.getText().toString();

                if (titleTXT.isEmpty() || authorTXT.isEmpty() || amountTXTStr.isEmpty()) {
                    Toast.makeText(ActivityAdd.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    int amountTXT = Integer.parseInt(amountTXTStr);
                    boolean checkAddBooks = dbHelper.addBooks(titleTXT, authorTXT, amountTXT);
                    if (checkAddBooks)
                        Toast.makeText(ActivityAdd.this, "New Book Added", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(ActivityAdd.this, "Book Not Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
}}
