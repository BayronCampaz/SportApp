package edu.icesi.sportapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

import edu.icesi.sportapp.control.fragments.EventCreationFragment;
import edu.icesi.sportapp.control.fragments.EventNotificationsFragment;
import edu.icesi.sportapp.control.fragments.ProfileFragment;
import edu.icesi.sportapp.control.fragments.RequestedEventFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout fragmentsContainer;
    private ImageButton homeBtn;
    private ImageButton requestedEventBtn;
    private ImageButton addEventBtn;
    private ImageButton profileBtn;
    private ImageButton notificationEventBtn;
    private EventCreationFragment eventCreationFragment;
    private ProfileFragment profileFragment;
    private RequestedEventFragment requestedEventFragment;
    private EventNotificationsFragment eventNotificationsFragment;
    FirebaseAuth auth;

    private File photo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentsContainer = findViewById(R.id.fragment_container);
        homeBtn = findViewById(R.id.home_btn);
        requestedEventBtn = findViewById(R.id.requested_event_btn);
        addEventBtn = findViewById(R.id.add_event_btn);
        profileBtn = findViewById(R.id.profile_btn);
        notificationEventBtn = findViewById(R.id.notification_event);

        eventCreationFragment = new EventCreationFragment();
        requestedEventFragment = new RequestedEventFragment();
        profileFragment = new ProfileFragment();
        eventNotificationsFragment = new EventNotificationsFragment();


        homeBtn.setOnClickListener(this);
        requestedEventBtn.setOnClickListener(this);
        addEventBtn.setOnClickListener(this);
        profileBtn.setOnClickListener(this);
        notificationEventBtn.setOnClickListener(this);


        showHomeFragment();
        auth = FirebaseAuth.getInstance();

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        }, 11);
       // adapter.addElement(new EventSport(R.drawable.background,"Torneo futbolin", "Es un torneo especial",8,2000,"Futbol", Calendar.getInstance().getTime()));
        //adapter.addElement(new EventSport(R.drawable.background,"Torneo futbolin", "Es un torneo especial",8,2000,"Futbol", Calendar.getInstance().getTime()));


        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }


    }
    @Override
    public void onClick(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if( view.equals(profileBtn) ){
            setNormalColorToButtons();
            setColorRedToButton(profileBtn);
            ft.replace(R.id.fragment_container, profileFragment);

        }else if( view.equals(homeBtn) ){
            setNormalColorToButtons();
            setColorRedToButton(homeBtn);
            //ft.replace(R.id.fragment_container, editFragment);

        } else if( view.equals(addEventBtn) ){
            setNormalColorToButtons();
            setColorRedToButton(addEventBtn);
            ft.replace(R.id.fragment_container, eventCreationFragment);

        } else if( view.equals(requestedEventBtn) ){
            setNormalColorToButtons();
            setColorRedToButton(requestedEventBtn);
            ft.replace(R.id.fragment_container, requestedEventFragment );

        }else if( view.equals(notificationEventBtn) ){
            setNormalColorToButtons();
            setColorRedToButton(notificationEventBtn);
            ft.replace(R.id.fragment_container, eventNotificationsFragment);

        }
        ft.commit();
    }

    public void setColorRedToButton(ImageButton button){
        button.setColorFilter(getResources().getColor(R.color.customRed));
    }

    public void setNormalColorToButtons(){
        homeBtn.setColorFilter(Color.BLACK);
        addEventBtn.setColorFilter(Color.BLACK);
        profileBtn.setColorFilter(Color.BLACK);
        requestedEventBtn.setColorFilter(Color.BLACK);
        notificationEventBtn.setColorFilter(Color.BLACK);
    }

    public void showHomeFragment(){
        onClick(homeBtn);
    }
}
