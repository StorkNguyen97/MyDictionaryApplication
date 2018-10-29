package com.atv.anhnd.mydictionaryapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context memContext;

    public static final String DATABASE_NAME = "data.sqlite";
    public static final int DATABASE_VERSION = 1;

    private String DATABASE_LOCATION = "";
    private String DATABASE_FULL_PATH = "";

    public static final String TB_VN_EN = "vietanh";
    public static final String TB_EN_VN = "anhviet";
    private final String TB_BOOKMARK = "bookmark";

    private final String COL_KEY = "tu";
    private final String COL_VALUE = "nghia";

    public SQLiteDatabase memDB;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        memContext = context;

        DATABASE_LOCATION = "data/data/" + memContext.getPackageName() + "/database/";
        DATABASE_FULL_PATH = DATABASE_LOCATION + DATABASE_NAME;


        if (!isExistingDataBase()) {
            try {
                File dbLocation = new File(DATABASE_LOCATION);
                dbLocation.mkdirs();

                extractToDataBaseDirectory(DATABASE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        memDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_FULL_PATH, null);
    }

    boolean isExistingDataBase() {
        File file = new File(DATABASE_FULL_PATH);
        return file.exists();
    }

    public void extractToDataBaseDirectory(String fileName) throws IOException {
        int length;
        InputStream sourceDatabase = this.memContext.getAssets().open(fileName);
        File destinationPath = new File(DATABASE_FULL_PATH);
        OutputStream destination = new FileOutputStream(destinationPath);

        byte[] buffer = new byte[4096];
        while ((length = sourceDatabase.read(buffer)) > 0) {
            destination.write(buffer, 0, length);
        }

        sourceDatabase.close();
        destination.flush();
        destination.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getWord(int dicType) {
        String tableName = getTableName(dicType);
        String q = "SELECT * FROM " + tableName;
        Cursor result = memDB.rawQuery(q, null);

        ArrayList<String> source = new ArrayList<>();

        while (result.moveToNext()) {
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;
    }

    public ArrayList<String> getWord(String tableName, int offset) {
        Log.d("offset", offset + "");
        String q = "SELECT * FROM " + tableName + " LIMIT 50 OFFSET " + offset * 50;
        Cursor result = memDB.rawQuery(q, null);

        ArrayList<String> source = new ArrayList<>();

        while (result.moveToNext()) {
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;
    }

//    public Word getWord(String tu, int dicType) {
//        String tableName = getTableName(dicType);
//        String q = " SELECT * FROM " + tableName + " WHERE upper([tu]) = upper(?) ";
//        Cursor result = memDB.rawQuery(q, new String[]{tu});
//
//        Word word = new Word();
//        while (result.moveToNext()) {
//            word.tu = result.getString(result.getColumnIndex(COL_KEY));
//            word.nghia = result.getString(result.getColumnIndex(COL_VALUE));
//        }
//        return word;
//    }

    public Word getWord(String tu, String tableName) {
        String q = " SELECT * FROM " + tableName + " WHERE upper([tu]) = upper(?) ";
        Cursor result = memDB.rawQuery(q, new String[]{tu});

        Word word = new Word();
        while (result.moveToNext()) {
            word.tu = result.getString(result.getColumnIndex(COL_KEY));
            word.nghia = result.getString(result.getColumnIndex(COL_VALUE));
        }
        return word;
    }

    public void addBookmark(Word word) {
        try {
            String q = " INSERT INTO bookmark([" + COL_KEY + "],[" + COL_VALUE + "]) VALUES (?, ?);";
            memDB.execSQL(q, new Object[]{word.tu, word.nghia});
        } catch (SQLException ex) {

        }
    }

    public void removeBookmark(Word word) {
        try {
            String q = " DELETE FROM bookmark WHERE upper([" + COL_KEY + "]) = upper(?) AND [" + COL_VALUE + "] = ?;";
            memDB.execSQL(q, new Object[]{word.tu, word.nghia});
        } catch (SQLException ex) {

        }
    }

    public ArrayList<String> getAllWordFromBookmark() {
        String q = " SELECT * FROM bookmark ORDER BY [created_at] DESC;";
        Cursor result = memDB.rawQuery(q, null);

        ArrayList<String> source = new ArrayList<>();

        while (result.moveToNext()) {
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;
    }

    public boolean isWordMark(Word word) {
        String q = " SELECT * FROM bookmark WHERE upper([tu]) = upper(?) AND [nghia] = ?";
        Cursor result = memDB.rawQuery(q, new String[]{word.tu, word.nghia});
        return result.getCount() > 0;
    }

    public Word getWordFromBookmark(String tu) {
        String q = " SELECT * FROM bookmark WHERE tu = ?";
        Cursor result = memDB.rawQuery(q, new String[]{tu});
        Word word = new Word();
        while (result.moveToNext()) {
            word.tu = result.getString(result.getColumnIndex(COL_KEY));
            word.nghia = result.getString(result.getColumnIndex(COL_VALUE));
        }
        return word;
    }

    public String getTableName(int dicType) {
        String tableName = "";
        if (dicType == R.id.action_en_vn) {
            tableName = TB_EN_VN;
        } else {
            tableName = TB_VN_EN;
        }
        return tableName;
    }
}
