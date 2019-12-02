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
import edu.icesi.sportapp.control.adapters.RequestedEventAdapter;
import edu.icesi.sportapp.model.entity.EventSportRequest;
import edu.icesi.sportapp.model.remote.DatabaseConstants;


public class RequestedEventFragment extends Fragment {

    private ListView listRequestedEvents;
    RequestedEventAdapter adapter;

    FirebaseDatabase db;
    FirebaseAuth auth;

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

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        db.getReference()
                .child(DatabaseConstants.REQUESTS)
                .child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                                EventSportRequest event = child.getValue(EventSportRequest.class);
                                adapter.addRequestedEvent(event);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });

        listRequestedEvents.setAdapter(adapter);

        return view;
    }
}
