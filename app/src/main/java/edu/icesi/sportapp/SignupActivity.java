package edu.icesi.sportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import edu.icesi.sportapp.model.entity.User;

public class SignupActivity extends AppCompatActivity {

    private EditText nameEt;
    private EditText emailEt;
    private EditText passwordEt;
    private EditText repasswordEt;
    private EditText cellphoneEt;
    private Spinner favoriteSportSp;
    private Button registerBtn;


    FirebaseAuth auth;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEt = findViewById(R.id.register_name_et);
        emailEt = findViewById(R.id.register_mail_et);
        passwordEt = findViewById(R.id.register_password_et);
        repasswordEt = findViewById(R.id.register_repassword_et);
        cellphoneEt = findViewById(R.id.register_cellphone_et);
        favoriteSportSp = findViewById(R.id.sports_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sports_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favoriteSportSp.setAdapter(adapter);

        registerBtn = findViewById(R.id.register_btn);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        registerBtn.setOnClickListener(view -> {
            if (emailEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignupActivity.this, "El campo de email esta vacio", Toast.LENGTH_LONG).show();
                return;
            }

            if (!passwordEt.getText().toString()
                    .equals(repasswordEt.getText().toString())) {
                Toast.makeText(SignupActivity.this, "Las contraseñas NO coinciden", Toast.LENGTH_LONG).show();
                return;
            }

            if (passwordEt.getText().toString().trim().length() < 6) {
                Toast.makeText(SignupActivity.this, "Las contraseñas debe tener mínimo 6 carácteres", Toast.LENGTH_LONG).show();
                return;
            }

            auth.createUserWithEmailAndPassword(
                    emailEt.getText().toString().trim(),
                    passwordEt.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        User user = new User(
                                auth.getCurrentUser().getUid(),
                                nameEt.getText().toString().trim(),
                                emailEt.getText().toString().trim(),
                                passwordEt.getText().toString().trim(),
                                cellphoneEt.getText().toString(),
                                favoriteSportSp.getTransitionName()
                        );

                        db.getReference().child("users").child(user.getUid()).setValue(user);
                        Intent i = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(SignupActivity.this, "" + task.getException(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        });
    }
}
