package com.mac.chris.contentproviderexample;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {
    
    long id;
    private final static int REQUEST_PERMISSION_RESULT = 0;
    private ListView eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        eventsList = (ListView) findViewById(R.id.list_events);

        Intent i = getIntent();
        id = i.getLongExtra("ID",0);

        loadEvents();
    }

    private void loadEvents() {
        Uri uri = CalendarContract.Events.CONTENT_URI;

        String[] projections = {
                CalendarContract.Events.TITLE,
                CalendarContract.Events._ID
        };

        String selection = CalendarContract.Events.CALENDAR_ID + "=?";

        String[] selectionArgs = { id+""
        };

        String sortOrder = CalendarContract.Events.TITLE + " ASC";

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Cursor cursor = getContentResolver().query(uri, projections, selection, selectionArgs, sortOrder);

        EventCursorAdapter adapter = new EventCursorAdapter(this, cursor, 0);
        eventsList.setAdapter(adapter);
    }

    private void removeBoringEvents(long id) {

        Uri deleteUri = CalendarContract.Events.CONTENT_URI;

       // deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
       // int rows = getContentResolver().delete(deleteUri, null, null);
       // Log.i("removeBoringEvents() ", "Rows deleted: " + rows);

    }
}
