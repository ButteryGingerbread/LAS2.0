package com.example.libraryadminstrationsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityView extends AppCompatActivity {
    Button btnView;
    DBHelperBooks dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find);
        btnView = findViewById(R.id.btnView);
        dbHelper = new DBHelperBooks(this);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = dbHelper.getBooks();

                if (res.getCount() == 0) {
                    Toast.makeText(ActivityView.this, "No Books Found", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder buffer = new StringBuilder();
                    while (res.moveToNext()) {
                        buffer.append("BookID: ").append(res.getString(0)).append("\n");
                        buffer.append("Title: ").append(res.getString(1)).append("\n");
                        buffer.append("Author: ").append(res.getString(2)).append("\n\n");
                        buffer.append("Amount: ").append(res.getString(3)).append("\n\n");
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityView.this);
                    builder.setCancelable(true);
                    builder.setTitle("Books");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
            }
        });

    }
}
