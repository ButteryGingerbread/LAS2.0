package com.example.libraryadminstrationsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityDelete extends AppCompatActivity {
    EditText bookid;
    Button btnDelete;
    DBHelperBooks dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find);
        bookid = findViewById(R.id.bookid);
        btnDelete = findViewById(R.id.btnDelete);
        dbHelper = new DBHelperBooks(this);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookIdTXT = bookid.getText().toString();

                // Check if bookIdTXT is empty
                if (bookIdTXT.isEmpty()) {
                    Toast.makeText(ActivityDelete.this, "Please enter Book ID", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkDeleteBook = dbHelper.deleteBooks(bookIdTXT);
                    if (checkDeleteBook)
                        Toast.makeText(ActivityDelete.this, "Book Deleted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(ActivityDelete.this, "Book Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

