package edu.icesi.sportapp.control.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.icesi.sportapp.R;
import edu.icesi.sportapp.model.entity.EventSport;

public class FeedAdapter extends BaseAdapter {

    private ArrayList <EventSport> objects;

    public FeedAdapter() {
        objects= new ArrayList<EventSport>();
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

        final EventSport data = objects.get(position);


        ImageView eventPhoto = v.findViewById(R.id.event_photo);
        TextView eventName  = v.findViewById(R.id.event_name);
        //TextView eventAddress = v.findViewById(R.id.address);
        //TextView startHour = v.findViewById(R.id.start_hour);
        TextView responsible = v.findViewById(R.id.responsible);
        Button btnAttend= v.findViewById(R.id.attend);
        Button btnInfo=v.findViewById(R.id.more_info);

        eventPhoto.setImageResource(data.getPhoto());
        eventName.setText("Nombre: "+data.getName());
        //eventAddress.setText("Dirección: "+data);
        //responsible.setText("Responable: "+data.get);


        btnAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        return v;

    }

    public void addElement(EventSport post){
        objects.add(post);
        notifyDataSetChanged();

    }

}
