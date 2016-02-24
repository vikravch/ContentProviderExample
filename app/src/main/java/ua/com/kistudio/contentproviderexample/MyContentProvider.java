package ua.com.kistudio.contentproviderexample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.content.ContentUris;
import android.database.sqlite.SQLiteOpenHelper;

public class MyContentProvider extends ContentProvider {

    public static final String AUTORITIES = "my.kistudio.content.example";
    public static final String CONTENT = "content";
    public static final Uri CONTACT_CONTENT_URI = Uri.parse("content://"+AUTORITIES+"/"+CONTENT);


    public static final String TABLE_NAME = "users";
    DBHelper dbHelper;
    SQLiteDatabase db;

    public MyContentProvider() {

    }


    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(),1);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(TABLE_NAME, null, values);
        Uri resURI = ContentUris.withAppendedId(uri,rowID);
        return resURI;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        db = dbHelper.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class DBHelper extends SQLiteOpenHelper {

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " (_id integer primary key autoincrement, login text, password text);";

        public DBHelper(Context context, int version) {
            super(context, "myDB", null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}

