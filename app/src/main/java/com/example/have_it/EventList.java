package com.example.have_it;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventList extends ArrayAdapter<Event> {
    private ArrayList<Event> events;
    private Context context;

    public EventList( Context context, ArrayList<Event> events) {
        super(context, 0, events);
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.event_list_content, parent,false);
        }
        Event event = events.get(position);
        TextView habitName = view.findViewById(R.id.event_title);
        String date_event=event.getDate()+"/"+event.getEvent();


       habitName.setText(date_event);
        return view;
    }


}