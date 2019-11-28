package edu.icesi.sportapp.control.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.icesi.sportapp.R;
import edu.icesi.sportapp.control.adapters.EventNotificationsAdapter;


public class EventNotificationsFragment extends Fragment {

    private ListView listEventNotifications;
    private EventNotificationsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_notifications, container, false);
        listEventNotifications = view.findViewById(R.id.list_event_notifications);

        adapter = new EventNotificationsAdapter();
        listEventNotifications.setAdapter(adapter);
        return view;
    }


}
