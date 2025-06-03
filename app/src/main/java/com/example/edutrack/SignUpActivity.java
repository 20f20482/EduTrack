package com.example.edutrack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText regEmail, regPassword, regConfirm;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String selectedUserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regConfirm = findViewById(R.id.reg_confirm);
        Spinner regUserTypeSpinner = findViewById(R.id.reg_user_type);
        Button regButton = findViewById(R.id.reg_button);
        Button btnBackToMain = findViewById(R.id.btn_back_to_main);

        //  Firebase Auth & Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        //  Spinner
        String[] userTypes = {"Student", "Teacher", "Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regUserTypeSpinner.setAdapter(adapter);

        regUserTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUserType = userTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedUserType = "";
            }
        });

        // register button
        regButton.setOnClickListener(v -> {

            String email = regEmail.getText().toString().trim();
            String password = regPassword.getText().toString().trim();
            String confirmPassword = regConfirm.getText().toString().trim();

            // data verification
            if (TextUtils.isEmpty(email)) {
                regEmail.setError("Email is required");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                regEmail.setError("Please enter a valid email");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                regPassword.setError("Password is required");
                return;
            }
            if (password.length() < 6) {
                regPassword.setError("Password must be at least 6 characters");
                return;
            }
            if (!password.equals(confirmPassword)) {
                regConfirm.setError("Passwords do not match");
                return;
            }
            if (TextUtils.isEmpty(selectedUserType)) {
                Toast.makeText(SignUpActivity.this, "Please select an account type", Toast.LENGTH_SHORT).show();
                return;
            }

            //create account
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpActivity.this, task -> {
                        if (task.isSuccessful()) {

                            String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                            Map<String, Object> userData = new HashMap<>();
                            userData.put("email", email);
                            userData.put("userType", selectedUserType);

                            mDatabase.child(userId).setValue(userData)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();


                                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(SignUpActivity.this, "Failed to save user data: " + Objects.requireNonNull(dbTask.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                        } else {
                            Toast.makeText(SignUpActivity.this, "Registration failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        });


        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
        });


    }
}
