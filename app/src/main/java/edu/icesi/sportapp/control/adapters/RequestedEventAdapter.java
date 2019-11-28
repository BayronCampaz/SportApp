package edu.icesi.sportapp.control.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.icesi.sportapp.R;
import edu.icesi.sportapp.model.entity.EventSportRequest;

public class RequestedEventAdapter extends BaseAdapter {

    private ArrayList<EventSportRequest> eventSportRequests;

    public RequestedEventAdapter(){
        eventSportRequests = new ArrayList<>();
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

        //TODO Acá toca obtener los eventos haciendo uso del atributo eventID del objeto eventSportRequests
        nameRequestedEvent.setText("Evento: " );
        nameRequestedEvent.setText("Estado: " + eventSportRequests.get(i).getStatus());

        return rowView ;
    }

    public void addRequestedEvent(EventSportRequest event){
        eventSportRequests.add(event);
    }
}
