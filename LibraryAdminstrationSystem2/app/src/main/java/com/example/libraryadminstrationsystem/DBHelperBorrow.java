package com.example.libraryadminstrationsystem;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import androidx.annotation.Nullable;

public class DBHelperBorrow extends SQLiteOpenHelper {
    public static final String DBName = "borrow.db";

    public DBHelperBorrow(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE borrow (" +
                "BorrowID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "StudentID TEXT, " +
                "BookID TEXT, " +
                "BorrowDate TEXT, " +
                "ReturnDate TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS borrow");
        onCreate(db);
    }

    public boolean insertData(String studentID, String bookID, DBHelperBooks dbHelper2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("StudentID", studentID);
        contentValues.put("BookID", bookID);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        contentValues.put("BorrowDate", currentDate);

        Calendar returnCalendar = Calendar.getInstance();
        returnCalendar.add(Calendar.DAY_OF_YEAR, 5);
        String returnDate = sdf.format(returnCalendar.getTime());
        contentValues.put("ReturnDate", returnDate);

        long result = db.insert("borrow", null, contentValues);

        if (result != -1) {
            boolean updated = dbHelper2.decrementBookAmount(bookID);
            return updated;
        }

        return false;
    }


    public Cursor getBorrowedBooksByStudentID(String studentID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"BorrowID", "BookID", "BorrowDate", "ReturnDate"};
        String selection = "StudentID = ?";
        String[] selectionArgs = {studentID};
        return db.query("borrow", projection, selection, selectionArgs, null, null, null);
    }

    public boolean returnBook(int borrowID, DBHelperBooks dbHelper2, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "BorrowID = ?";
        String[] whereArgs = {String.valueOf(borrowID)};
        int deletedRows = db.delete("borrow", whereClause, whereArgs);
        if (deletedRows > 0) {
            Cursor cursor = db.rawQuery("SELECT BookID FROM borrow WHERE BorrowID = ?", new String[]{String.valueOf(borrowID)});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String bookID = cursor.getString(cursor.getColumnIndex("BookID"));
                dbHelper2.incrementBookAmount(bookID);
            }
            cursor.close();
            Toast.makeText(context, "Book returned successfully.", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Failed to return book.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }}
