package com.example.libraryadminstrationsystem;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ActivityReturn extends AppCompatActivity {
    EditText etStudentID;
    ListView listViewBooks;
    ArrayAdapter<String> adapter;
    ArrayList<String> borrowedBooks;
    DBHelperBorrow dbHelper;
    DBHelperBooks dbHelper2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_return);

        etStudentID = findViewById(R.id.etStudentID);
        listViewBooks = findViewById(R.id.listViewBooks);
        dbHelper = new DBHelperBorrow(this);
        dbHelper2 = new DBHelperBooks(this);

        borrowedBooks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, borrowedBooks);
        listViewBooks.setAdapter(adapter);

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> searchBorrowedBooks());

        listViewBooks.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            int borrowID = extractBorrowID(selectedItem);
            if (borrowID != -1) {
                returnBookAndRemoveFromList(borrowID, position);
            }
        });
    }

    private void searchBorrowedBooks() {
        if (etStudentID == null) {
            Toast.makeText(ActivityReturn.this, "EditText is null", Toast.LENGTH_SHORT).show();
            return;
        }

        String studentID = etStudentID.getText().toString().trim();

        Cursor cursor = dbHelper.getBorrowedBooksByStudentID(studentID);
        borrowedBooks.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int borrowID = cursor.getInt(cursor.getColumnIndex("BorrowID"));
                @SuppressLint("Range") String bookID = cursor.getString(cursor.getColumnIndex("BookID"));
                @SuppressLint("Range") String borrowDate = cursor.getString(cursor.getColumnIndex("BorrowDate"));
                @SuppressLint("Range") String returnDate = cursor.getString(cursor.getColumnIndex("ReturnDate"));
                borrowedBooks.add("Borrow ID: " + borrowID + "\nBook ID: " + bookID + "\nBorrow Date: " + borrowDate + "\nReturn Date: " + returnDate);
            } while (cursor.moveToNext());

            cursor.close();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(ActivityReturn.this, "No borrowed books found for this student ID.", Toast.LENGTH_SHORT).show();
        }
    }

    private int extractBorrowID(String selectedItem) {
        String[] parts = selectedItem.split("\n");
        for (String part : parts) {
            if (part.startsWith("Borrow ID: ")) {
                String borrowIDString = part.substring("Borrow ID: ".length());
                try {
                    return Integer.parseInt(borrowIDString);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        }
        return -1;
    }

    private void returnBookAndRemoveFromList(int borrowID, int position) {
        boolean bookReturned = dbHelper.returnBook(borrowID, dbHelper2, this);
        if (bookReturned) {
            borrowedBooks.remove(position);
            adapter.notifyDataSetChanged();
        }
    }
}
