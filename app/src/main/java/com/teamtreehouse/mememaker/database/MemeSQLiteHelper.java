package com.teamtreehouse.mememaker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Evan Anger on 8/17/14.
 */
public class MemeSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "memes.db";
    private static final int DB_VERSION = 1;

    //Meme Table functionality
    public static final String MEMES_TABLE = "MEMES";
    public static final String COLUMN_MEME_ASSET = "ASSET";
    public static final String COLUMN_MEME_NAME = "NAME";
    private static String CREATE_MEMES = "CREATE TABLE" + MEMES_TABLE +  "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_MEME_ASSET + " TEXT," + COLUMN_MEME_NAME +" TEXT)";

    //Meme Table Annotations functionality
    //Meme Table Annotations functionality
    public static final String ANNOTATIONSTABLE = "ANNOTATIONS";
    public static final String COLUMNANNOTATIONCOLOR = "COLOR";
    public static final String COLUMNANNOTATIONX = "X";
    public static final String COLUMNANNOTATIONY = "Y";
    public static final String COLUMNANNOTATIONTITLE = "TITLE";
    public static final String COLUMNFOREIGNKEYMEME = "MEME_ID";


    private static final String CREATEANNOTATIONS = "CREATE TABLE " + ANNOTATIONSTABLE + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMNANNOTATIONX + " INTEGER, " +
            COLUMNANNOTATIONY + " INTEGER, " +
            COLUMNANNOTATIONTITLE + " TEXT, " +
            COLUMNANNOTATIONCOLOR + " TEXT, " +
            COLUMNFOREIGNKEYMEME + " INTEGER, " +
            "FOREIGN KEY(" + COLUMNFOREIGNKEYMEME + ") REFERENCES MEMES(_ID))";

    public MemeSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create database schema for first time
        db.execSQL(CREATE_MEMES);
        db.execSQL(CREATEANNOTATIONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
