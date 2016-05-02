package com.mac.chris.contentproviderexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_PERMISSION_RESULT = 0;
    private ListView calendarsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarsList = (ListView) findViewById(R.id.list_calendars);

        calendarsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // id = Calendar ID
                Intent i = new Intent(getApplicationContext(), EventsActivity.class);
                i.putExtra("ID", id);
                startActivity(i);
            }
        });
        checkPermission();
    }

    private void loadCalendars() {
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

        String[] projections = {
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars._ID
        };

        String selection = "";

        String[] selectionArgs = {
        };

        String sortOrder = CalendarContract.Calendars.NAME + " ASC";

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Cursor cursor = getContentResolver().query(uri, projections, selection, selectionArgs, sortOrder);

        CalendarCursorAdapter adapter = new CalendarCursorAdapter(this, cursor, 0);
        calendarsList.setAdapter(adapter);
    }

    private void checkPermission() {

        List<String> permissionsToRequest = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_DENIED) {
            permissionsToRequest.add(Manifest.permission.READ_CALENDAR);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_DENIED) {
            permissionsToRequest.add(Manifest.permission.WRITE_CALENDAR);;
        }

        String[] permissionsList = new String[permissionsToRequest.size()];
        permissionsList = permissionsToRequest.toArray(permissionsList);

        if (permissionsToRequest.size() != 0) {
            ActivityCompat.requestPermissions(this, permissionsList, REQUEST_PERMISSION_RESULT);
        } else {
            loadCalendars();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_RESULT) {
            for (int i = 0; i < permissions.length; i++) {
                if (Manifest.permission.READ_CALENDAR.equals(permissions[i])) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        throw new RuntimeException();
                    }
                } else if (Manifest.permission.WRITE_CALENDAR.equals(permissions[i])) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        throw new RuntimeException();
                    }
                }
            }
        }
        loadCalendars();
    }
}
