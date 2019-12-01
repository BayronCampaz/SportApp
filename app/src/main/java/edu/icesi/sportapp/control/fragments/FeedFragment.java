package edu.icesi.sportapp.control.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.icesi.sportapp.R;
import edu.icesi.sportapp.control.adapters.FeedAdapter;
import edu.icesi.sportapp.model.entity.EventSport;
import edu.icesi.sportapp.model.entity.LatLong;
import edu.icesi.sportapp.model.entity.User;
import edu.icesi.sportapp.model.remote.DatabaseConstants;

public class FeedFragment extends Fragment {


    private ListView postList;
    private FeedAdapter adapter;

    FirebaseAuth auth;
    FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_feed, container, false);

        postList = view.findViewById(R.id.events);
        adapter = new FeedAdapter();
        postList.setAdapter(adapter);


        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();


        db.getReference()
                .child("sportEvents")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot child :dataSnapshot.getChildren()){

                            for (DataSnapshot e :child.getChildren()){
                                EventSport event = e.getValue(EventSport.class);

                                switch(event.getSport()) {
                                    case "Fútbol":
                                        event.setPhoto(R.drawable.futbol);
                                        break;
                                    case "Baloncesto":
                                        event.setPhoto(R.drawable.baloncesto);
                                        break;

                                    case "Fútbol Sala":
                                        event.setPhoto(R.drawable.futsal);
                                        break;
                                    case "Voleibol":
                                        event.setPhoto(R.drawable.voleibol);
                                        break;
                                    case "Tenis":
                                        event.setPhoto(R.drawable.tenis);
                                        break;
                                    case "Balonmano":
                                        event.setPhoto(R.drawable.balonmano);
                                        break;
                                    case "Beisbol":
                                        event.setPhoto(R.drawable.beisbol);
                                        break;

                                }

                                adapter.addElement(event);


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
