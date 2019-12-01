package edu.icesi.sportapp.control.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.icesi.sportapp.R;
import edu.icesi.sportapp.control.adapters.EventNotificationsAdapter;
import edu.icesi.sportapp.model.entity.EventSport;
import edu.icesi.sportapp.model.entity.EventSportRequest;
import edu.icesi.sportapp.model.remote.DatabaseConstants;


public class EventNotificationsFragment extends Fragment {

    private ListView listEventNotifications;
    private EventNotificationsAdapter adapter;
    FirebaseDatabase db;
    FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_notifications, container, false);
        listEventNotifications = view.findViewById(R.id.list_event_notifications);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        adapter = new EventNotificationsAdapter();
        listEventNotifications.setAdapter(adapter);

        db.getReference()
                .child(DatabaseConstants.EVENTS)
                .child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot child :dataSnapshot.getChildren()){
                                EventSport event = child.getValue(EventSport.class);
                                for(EventSportRequest eventSR: event.getRequests()){
                                    adapter.addNotificationEvent(eventSR);
                                }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });


        return view;
    }


}
