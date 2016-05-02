package com.mac.chris.contentproviderexample;

import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by chris on 3/23/16.
 */
public class EventCursorAdapter extends CursorAdapter{

    private LayoutInflater inflater;

    public EventCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.rows_events_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textName = (TextView) view.findViewById(R.id.events_name);
        textName.setText(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE)));
    }
}
