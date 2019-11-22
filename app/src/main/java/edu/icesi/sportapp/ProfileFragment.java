package edu.icesi.sportapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.icesi.sportapp.model.entity.User;
import edu.icesi.sportapp.model.remote.DatabaseConstants;


public class ProfileFragment extends Fragment {

    private TextView nameTv;
    private EditText emailEt;
    private EditText cellphoneEt;
    private Spinner sportSp;
    private Button editInfoBtn;
    private Button changePasswordBtn;

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

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        db.getReference().child(DatabaseConstants.USERS)
                .child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        return view;
    }



}
