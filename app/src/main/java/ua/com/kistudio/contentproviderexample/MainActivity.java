package ua.com.kistudio.contentproviderexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    CursorLoader cursorLoader;

    public static final String LOG_TAG ="ContentLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btnInsert)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnQuery)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnQueryList)).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getLoaderManager().initLoader(1,null,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInsert:
                ContentValues values = new ContentValues();
                values.put("login","user1");
                values.put("password", "pass1");
                Uri resUri = getContentResolver().
                        insert(MyContentProvider.CONTACT_CONTENT_URI, values);
                Log.d(LOG_TAG,resUri.getPath());
                break;
            case R.id.btnQueryList:
                Cursor cList = getContentResolver().query(MyContentProvider.CONTACT_CONTENT_URI,
                        null,null,null,null);
                SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cList,
                        new String[]{"login","password"},new int[]{android.R.id.text1,android.R.id.text2}, Adapter.NO_SELECTION);
                ((ListView) findViewById(R.id.lvOut)).setAdapter(cursorAdapter);
                break;
            case R.id.btnQuery:
                Cursor c = getContentResolver().query(MyContentProvider.CONTACT_CONTENT_URI,
                        null,null,null,null);
                c.moveToFirst();
                Log.d(LOG_TAG,"--------------------------------------------");
                int loginId = c.getColumnIndex("login");
                int passwordId = c.getColumnIndex("password");

                do{
                    Log.d(LOG_TAG,String.format("%s - login; %s - password ",
                            c.getString(loginId),c.getString(passwordId)));
                } while(c.moveToNext());

                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            cursorLoader = new CursorLoader(this,MyContentProvider.CONTACT_CONTENT_URI,null,null,null,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,data,
                new String[]{"login","password"},new int[]{android.R.id.text1,android.R.id.text2}, Adapter.NO_SELECTION);
        ((ListView) findViewById(R.id.lvOut)).setAdapter(cursorAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
