package edu.icesi.sportapp.control.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import edu.icesi.sportapp.LoginActivity;
import edu.icesi.sportapp.R;
import edu.icesi.sportapp.model.entity.EventSport;
import edu.icesi.sportapp.model.entity.User;
import edu.icesi.sportapp.model.remote.DatabaseConstants;


public class ProfileFragment extends Fragment {

    private TextView nameTv;
    private EditText emailEt;
    private EditText cellphoneEt;
    private Spinner sportSp;
    private Button editInfoBtn;
    private Button changePasswordBtn;
    private Button acceptChangesBtn;
    private  Button signout;

    private ArrayAdapter<EventSport> eventSportArrayAdapter;

    FirebaseAuth auth;
    FirebaseDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTv = view.findViewById(R.id.perfil_name);
        emailEt = view.findViewById(R.id.perfil_mail_et);
        cellphoneEt = view.findViewById(R.id.perfil_cellphone_et);
        sportSp = view.findViewById(R.id.perfil_sports_spinner);
        editInfoBtn = view.findViewById(R.id.edit_info);
        changePasswordBtn = view.findViewById(R.id.change_password);
        acceptChangesBtn = view.findViewById(R.id.accept_changes_btn);
        signout = view.findViewById(R.id.cerrar_sesión);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(container.getContext(),
                R.array.sports_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSp.setAdapter(adapter);

        final String[] password = {""};

        db.getReference().child(DatabaseConstants.USERS)
                .child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        nameTv.setText(user.getName());

                        password[0] = user.getPassword();

                        emailEt.setText(user.getEmail());
                        emailEt.setEnabled(false);

                        cellphoneEt.setText(user.getCellphone());
                        cellphoneEt.setEnabled(false);

                        sportSp.setTransitionName(user.getFavoriteSport());
                        sportSp.setEnabled(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        changePasswordBtn.setOnClickListener(view1 -> {

        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
               Intent i = new Intent(getContext(), LoginActivity.class);
               startActivity(i);
              // finish();
            }
        });

        editInfoBtn.setOnClickListener(view1 -> {
            emailEt.setEnabled(true);
            emailEt.setTextColor(Color.WHITE);
            cellphoneEt.setEnabled(true);
            cellphoneEt.setTextColor(Color.WHITE);
            sportSp.setEnabled(true);
            acceptChangesBtn.setVisibility(View.VISIBLE);
            editInfoBtn.setVisibility(View.GONE);
        });

        acceptChangesBtn.setOnClickListener(view1 -> {
            emailEt.setEnabled(false);
            emailEt.setTextColor(getResources().getColor(R.color.grayHint));
            cellphoneEt.setEnabled(false);
            cellphoneEt.setTextColor(getResources().getColor(R.color.grayHint));
            sportSp.setEnabled(false);


            User newUser = new User(auth.getCurrentUser().getUid(),
                    nameTv.getText().toString(),
                    emailEt.getText().toString(),
                    password[0],
                    cellphoneEt.getText().toString(),
                    sportSp.getSelectedItem().toString());

            db.getReference().child(DatabaseConstants.USERS).child(newUser.getUid()).setValue(newUser);
            editInfoBtn.setVisibility(View.VISIBLE);
            acceptChangesBtn.setVisibility(View.GONE);
            Toast toast = Toast.makeText(container.getContext(),"Se ha cambiado los datos correctamente", Toast.LENGTH_SHORT);
            toast.show();
        });


        return view;
    }



}
