package edu.icesi.sportapp.control.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.icesi.sportapp.R;
import edu.icesi.sportapp.model.entity.EventSport;
import edu.icesi.sportapp.model.entity.EventSportRequest;
import edu.icesi.sportapp.model.entity.User;
import edu.icesi.sportapp.model.remote.DatabaseConstants;

public class EventNotificationsAdapter extends BaseAdapter {


    private ArrayList<EventSportRequest> eventSportRequests;

    FirebaseDatabase db;

    public EventNotificationsAdapter(){

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
        View rowView = inflater.inflate(R.layout.row_notification_event, null);
        TextView applicantEventTv = rowView.findViewById(R.id.applicant_event);
        TextView nameNotificationEventTv = rowView.findViewById(R.id.name_notification_event);
        ImageButton checkBtn = rowView.findViewById(R.id.check_btn);
        ImageButton rejectBtn = rowView.findViewById(R.id.reject_btn);

        EventSportRequest event = eventSportRequests.get(i);

        db.getReference().child(DatabaseConstants.USERS)
                .child(event.getUserID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Response
                        User user = dataSnapshot.getValue(User.class);
                        applicantEventTv.setText("Solicitante: " + user.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        db.getReference().child(DatabaseConstants.EVENTS)
                .child(event.getOwnerEventID())
                .child(event.getEventID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Response
                        EventSport eventSport = dataSnapshot.getValue(EventSport.class);
                        nameNotificationEventTv.setText("Evento: " + eventSport.getName());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        checkBtn.setOnClickListener(view1 -> {

        });

        rejectBtn.setOnClickListener(view1 -> {

        });


        return rowView ;
    }

    public void addNotificationEvent(EventSportRequest event){
        eventSportRequests.add(event);
        notifyDataSetChanged();
    }
}
