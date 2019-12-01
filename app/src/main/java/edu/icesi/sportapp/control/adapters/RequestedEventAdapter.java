package edu.icesi.sportapp.control.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.icesi.sportapp.R;
import edu.icesi.sportapp.model.entity.EventSport;
import edu.icesi.sportapp.model.entity.EventSportRequest;
import edu.icesi.sportapp.model.remote.DatabaseConstants;

public class RequestedEventAdapter extends BaseAdapter {

    private ArrayList<EventSportRequest> eventSportRequests;

    FirebaseDatabase db;

    public RequestedEventAdapter(){

        eventSportRequests = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
    }

    @Override
    public int getCount() {
        return eventSportRequests.size();
    }

    @Override
    public Object getItem(int i) {
        return eventSportRequests.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View rowView = inflater.inflate(R.layout.row_requested_event, null);
        TextView nameRequestedEvent = rowView.findViewById(R.id.name_requested_event);
        TextView statusRequestedEvent = rowView.findViewById(R.id.status_requested_event);

        db.getReference().child(DatabaseConstants.EVENTS)
                .child(eventSportRequests.get(i).getOwnerEventID())
                .child(eventSportRequests.get(i).getEventID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Response
                        EventSport sport = dataSnapshot.getValue(EventSport.class);
                       nameRequestedEvent.setText(sport.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        statusRequestedEvent.setText("Estado: " + eventSportRequests.get(i).getStatus());

        return rowView ;
    }

    public void addRequestedEvent(EventSportRequest event){
        eventSportRequests.add(event);
        notifyDataSetChanged();
    }
}
