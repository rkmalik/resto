package com.example.rkmalik.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rkmalik.data.Category;
import com.example.rkmalik.data.FoodItem;
import com.example.rkmalik.data.Restaurant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicks on 11/03/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    //Database path
    private static String DB_PATH;
    // Database Version
    private static final int DB_VERSION = 1;
    // Database Name
    private static final String DB_NAME = "restoDB";

    public SQLiteDatabase database = null;
    public final Context context;

    public SQLiteDatabase getDatabase()
    {
        return database;
    }

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        DB_PATH = String.format("//data//data//%s//databases//", context.getPackageName());

        createDatabase();

    }

    public SQLiteDatabase openDatabase()
    {
        String path = DB_PATH + DB_NAME;
        if(database==null)
        {
            createDatabase();
            database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    public void createDatabase()
    {
        boolean isDbExist = checkDatabase();
        if(!isDbExist)
        {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkDatabase()
    {
        SQLiteDatabase checkDb = null;
        String path = DB_PATH+DB_NAME;
        checkDb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        if(null != checkDb)
            checkDb.close();
        return checkDb!=null;
    }

    private void copyDatabase() throws IOException
    {
        InputStream inputStream = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH+DB_NAME;
        OutputStream localDbStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while((bytesRead = inputStream.read(buffer)) > 0)
            localDbStream.write(buffer, 0, bytesRead);

        localDbStream.close();
        inputStream.close();
    }

    @Override
    public synchronized void close()
    {
        if(database!=null)
            database.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
