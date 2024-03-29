package edu.icesi.sportapp.control.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.icesi.sportapp.InfoEvent;
import edu.icesi.sportapp.MainActivity;
import edu.icesi.sportapp.MapsActivity;
import edu.icesi.sportapp.R;
import edu.icesi.sportapp.control.fragments.FeedFragment;
import edu.icesi.sportapp.model.entity.EventSport;
import edu.icesi.sportapp.model.entity.EventSportRequest;
import edu.icesi.sportapp.model.remote.DatabaseConstants;

public class FeedAdapter extends BaseAdapter {

    private ArrayList <EventSport> objects;

    FirebaseDatabase db;
    FirebaseAuth auth;

    public FeedAdapter() {
        objects= new ArrayList<EventSport>();
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.eventitem, null, false);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");


        final EventSport data = objects.get(position);


        ImageView eventPhoto = v.findViewById(R.id.event_photo);
        TextView eventName  = v.findViewById(R.id.event_name);

        TextView eventAddress = v.findViewById(R.id.address);
        TextView startHour = v.findViewById(R.id.start_hour);
        TextView price= v.findViewById(R.id.price);
        TextView numberPeople=v.findViewById(R.id.number_people);
        TextView date=v.findViewById(R.id.date);

        TextView emailRes=v.findViewById(R.id.emailR);

        Button btnAttend= v.findViewById(R.id.attend);
        Button btnInfo=v.findViewById(R.id.more_info);

        eventPhoto.setImageResource(data.getPhoto());
        eventName.setText("Nombre:  "+data.getName());
        eventAddress.setText("Dirección:  "+data.getAddress());
        startHour.setText("Hora de inicio:  "+sdf2.format(data.getDate()));

        price.setText("Precio:  $"+String.valueOf(data.getPrice()).replaceAll("[0]*$", "").replaceAll(".$", ""));
        numberPeople.setText("Número de personas:  "+data.getNumberPeople());
        date.setText("Fecha:  "+sdf.format(data.getDate()));
        emailRes.setText("Email responsable:  "+data.getEmailResponsible());

        EventSport event = objects.get(position);

        if(event.getOwnerID().equals(auth.getCurrentUser().getUid())){
            emailRes.setText("Este es un evento tuyo");
            btnAttend.setVisibility(View.GONE);
        }

        btnAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uid =  db.getReference().child(DatabaseConstants.REQUESTS).child(auth.getCurrentUser().getUid()).push().getKey();
                EventSportRequest request = new EventSportRequest(uid,
                        auth.getCurrentUser().getUid(),
                        event.getUid(),
                        event.getOwnerID(),
                        EventSportRequest.PENDING);

                db.getReference().child(DatabaseConstants.REQUESTS).child(auth.getCurrentUser().getUid())
                        .child(uid).setValue(request);

                ArrayList<EventSportRequest> requests = event.getRequests();
                if(requests == null){
                    requests = new ArrayList<EventSportRequest>();
                }
                requests.add(request);
                event.setRequests(requests);
                Log.e(">>>>>>>>", event.getAddress());
                db.getReference().child(DatabaseConstants.EVENTS).child(event.getOwnerID()).child(event.getUid()).setValue(event);

                Toast toast = Toast.makeText(parent.getContext(), "Enviaste tu solicitud al evento", Toast.LENGTH_SHORT);
                toast.show();



            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), InfoEvent.class);
                i.putExtra("event",data);
                view.getContext().startActivity(i);
            }
        });

        return v;

    }

    public void addElement(EventSport post){
        objects.add(post);
        notifyDataSetChanged();

    }

}
