package edu.icesi.sportapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout fragmentsContainer;
    private ImageButton homeBtn;
    private ImageButton eventBtn;
    private ImageButton addEventBtn;
    private ImageButton profileBtn;
    EventCreationFragment eventCreationFragment;
    ProfileFragment profileFragment;
    FirebaseAuth auth;

    private File photo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentsContainer = findViewById(R.id.fragment_container);
        homeBtn = findViewById(R.id.home_btn);
        eventBtn = findViewById(R.id.event_btn);
        addEventBtn = findViewById(R.id.add_event_btn);
        profileBtn = findViewById(R.id.profile_btn);

        eventCreationFragment = new EventCreationFragment();
        profileFragment = new ProfileFragment();

        homeBtn.setOnClickListener(this);
        eventBtn.setOnClickListener(this);
        addEventBtn.setOnClickListener(this);
        profileBtn.setOnClickListener(this);


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
            ft.replace(R.id.fragment_container, profileFragment);
        }else if( view.equals(homeBtn) ){
            //ft.replace(R.id.fragment_container, editFragment);
        } else if( view.equals(addEventBtn) ){
            ft.replace(R.id.fragment_container, eventCreationFragment);
        }
        ft.commit();
    }

    public void showHomeFragment(){
        onClick(homeBtn);
    }
}
