package edu.icesi.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import edu.icesi.sportapp.model.entity.EventSport;

public class InfoEvent extends AppCompatActivity {



    private TextView eventNametxt;
    private ImageView imgItem;
    private TextView eventDescriptiontxt;
    private TextView eventAddresstxt;
    private TextView eventDatetxt;
    private TextView eventHourtxt;
    private TextView price;
    private TextView emailResponsabile;

    private Button butAttend;
    private Button butMap;
    private Button butBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_event);
        eventNametxt=findViewById(R.id.event_name);
        imgItem=findViewById(R.id.event_photo);
        eventDescriptiontxt=findViewById(R.id.description);
        eventAddresstxt=findViewById(R.id.address);
        eventDatetxt=findViewById(R.id.date);
        eventHourtxt=findViewById(R.id.start_hour);
        price=findViewById(R.id.price);
        emailResponsabile=findViewById(R.id.emailR);


        butAttend=findViewById(R.id.attend);
        butMap=findViewById(R.id.btn_map);
        butBack=findViewById(R.id.btn_back);

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");



        Bundle parametros=this.getIntent().getExtras();
        EventSport eventSport=null;
        if(parametros!=null){
            eventSport= (EventSport) getIntent().getExtras().get("event");


            eventNametxt.setText(eventSport.getName());
            imgItem.setImageResource(eventSport.getPhoto());
            eventDescriptiontxt.setText("Descripción:  "+eventSport.getDescription());
            eventAddresstxt.setText("Dirección:  "+eventSport.getAddress());
            eventDatetxt.setText("Fecha:  "+sdf.format(eventSport.getDate()));
            eventHourtxt.setText("Hora:  "+sdf2.format(eventSport.getDate()));
            price.setText("Precio:  $"+String.valueOf(eventSport.getPrice()).replaceAll("[0]*$", "").replaceAll(".$", ""));
            emailResponsabile.setText("Email responsable:  "+eventSport.getEmailResponsible());


        }

        EventSport finalEventSport = eventSport;
        butMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                i.putExtra("evento",finalEventSport);
                startActivity(i);



            }
        });

    }
}
