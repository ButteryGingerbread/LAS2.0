package com.example.libraryadminstrationsystem;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperBooks extends SQLiteOpenHelper {

    public DBHelperBooks(Context context) {
        super(context, "books.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Books (BookID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Author TEXT, Amount INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("DROP TABLE IF EXISTS Books");
        onCreate(DB);
    }

    public boolean addBooks(String Title, String Author, int Amount) {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT MAX(BookID) FROM Books", null);
        int currentMaxID = 0;
        if (cursor.moveToFirst()) {
            currentMaxID = cursor.getInt(0);
        }
        cursor.close();

        int nextBookID = currentMaxID + 1;

        ContentValues contentValues = new ContentValues();
        contentValues.put("BookID", nextBookID);
        contentValues.put("Title", Title);
        contentValues.put("Author", Author);
        contentValues.put("Amount", Amount);
        long result = DB.insert("Books", null, contentValues);
        return result != -1;
    }

    public Boolean updateBooks(String BookID, String Title, String Author)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", Title);
        contentValues.put("Author", Author);
        Cursor cursor = DB.rawQuery("Select * from books where BookID = ?", new String[]{BookID});
        if (cursor.getCount() > 0) {
            long result = DB.update("books", contentValues, "BookID=?", new String[]{BookID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Boolean deleteBooks (String BookID)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from books where BookID = ?", new String[]{BookID});
        if (cursor.getCount() > 0) {
            long result = DB.delete("books", "BookID=?", new String[]{BookID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getBooks ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from books", null);
        return cursor;
    }

    public boolean decrementBookAmount(String bookID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Amount FROM Books WHERE BookID = ?", new String[]{bookID});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int currentAmount = cursor.getInt(cursor.getColumnIndex("Amount"));
            if (currentAmount > 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("Amount", currentAmount - 1);
                int rowsAffected = db.update("Books", contentValues, "BookID = ?", new String[]{bookID});
                return rowsAffected > 0;
            }
        }
        cursor.close();
        return false;
    }

    public boolean incrementBookAmount(String bookID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Amount FROM Books WHERE BookID = ?", new String[]{bookID});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int currentAmount = cursor.getInt(cursor.getColumnIndex("Amount"));
            if (currentAmount > 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("Amount", currentAmount + 1);
                int rowsAffected = db.update("Books", contentValues, "BookID = ?", new String[]{bookID});
                return rowsAffected > 0;
            }
        }
        cursor.close();
        return false;
    }

}