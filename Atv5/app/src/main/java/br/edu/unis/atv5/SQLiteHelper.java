package br.edu.unis.atv5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "unis.db";
    private static final int DB_VERSION = 1;
    private static SQLiteHelper INSTANCE = null;
    private static final String DB_TABLE = "users";

    private SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static SQLiteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SQLiteHelper(context);
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "
                + DB_TABLE
                + "(id INTEGER primary key, user TEXT, password TEXT)"
        );
    }

    public long createUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("user", user);
            values.put("password", password);
            id = db.insertOrThrow(DB_TABLE, null, values);
            db.setTransactionSuccessful();
            Log.i("USER_CREATED","ID: " + id);
        } catch (SQLException e) {
            Log.e("ERROR_NEW_USER", "createUser: " + e.getMessage(), e);
        } finally {
            if (db.isOpen()) db.endTransaction();
        }
        return id;
    }

    public boolean deleteUser(long id ,String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("user", user);
            values.put("password", password);
            String where = "id=" + id;
            db.delete(DB_TABLE, where, null);
            db.setTransactionSuccessful();
            Log.i("USER_DELETED","ID: " + id);
        } catch (SQLException e) {
            Log.e("ERROR_USER_DELETE", "deleteUser: " + e.getMessage(), e);
        } finally {
            if (db.isOpen()) db.endTransaction();
        }
        return true;
    }

    public boolean authenticateUser(String user, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { "user" };
        String where = "user=? AND password=?";
        String[] args = { user, password };
        int count = 0;
        try {
            Cursor cursor = db.query(DB_TABLE, columns, where, args,null,null,null);
            count = cursor.getCount();
            cursor.close();
        } catch (SQLException e) {
            Log.e("ERROR_AUTHENTICATE", "authenticate user: " + e.getMessage(), e);
        } finally {
            if (db.isOpen()) db.close();
        }
        return count > 0;
    }

    public List<User> all() {
        final int ID = 0;
        final int USER = 1;
        final int PASSWORD = 2;
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM users",null);
            cursor.moveToFirst();
            do {
                long id = cursor.getLong(ID);
                String user = cursor.getString(USER);
                String password = cursor.getString(PASSWORD);
                users.add(new User(id, user, password));
            } while(cursor.moveToNext());
            cursor.close();
        } catch (SQLException e) {
            Log.e("ERROR_LIST_USER", e.getMessage(), e);
        } finally {
            if (db.isOpen()) db.close();
        }
        return users;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
