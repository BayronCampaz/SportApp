package edu.icesi.sportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout fragmentsContainer;
    private ImageButton homeBtn;
    private ImageButton eventBtn;
    private ImageButton addEventBtn;
    private ImageButton profileBtn;
    EventCreationFragment eventCreationFragment;
    ProfileFragment profileFragment;
    FirebaseAuth auth;

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
