package com.example.libraryadminstrationsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityUpdate extends AppCompatActivity {
    EditText bookid, title, author;
    Button btnUpdate;
    DBHelperBooks dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find);
        bookid = findViewById(R.id.bookid);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        btnUpdate = findViewById(R.id.btnUpdate);
        dbHelper = new DBHelperBooks(this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookIdTXT = bookid.getText().toString();
                String titleTXT = title.getText().toString();
                String authorTXT = author.getText().toString();

                // Check if any field is empty
                if (bookIdTXT.isEmpty() || titleTXT.isEmpty() || authorTXT.isEmpty()) {
                    Toast.makeText(ActivityUpdate.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUpdateBook = dbHelper.updateBooks(bookIdTXT, titleTXT, authorTXT);
                    if (checkUpdateBook)
                        Toast.makeText(ActivityUpdate.this, "Book Updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(ActivityUpdate.this, "Book Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
