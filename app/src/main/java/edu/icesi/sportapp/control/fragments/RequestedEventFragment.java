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
import edu.icesi.sportapp.control.adapters.RequestedEventAdapter;


public class RequestedEventFragment extends Fragment {

    private ListView listRequestedEvents;
    RequestedEventAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requested_event, container, false);
        listRequestedEvents = view.findViewById(R.id.list_requested_events);

        adapter = new RequestedEventAdapter();
        listRequestedEvents.setAdapter(adapter);

        return view;
    }
}
