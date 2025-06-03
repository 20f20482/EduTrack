package com.example.edutrack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class AdminDashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference("users");

        TextView tvAdmin482 = findViewById(R.id.tvWelcomeAdmin);
        Button btnVwUs482 = findViewById(R.id.btnViewUsers);
        Button btnUplCuAdmin482 = findViewById(R.id.btnUploadCorAdmin);
        Button btnLoOuAd482 = findViewById(R.id.btnLogoutAdmin);

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            tvAdmin482.setText("Welcome, Admin\n" + currentUser.getEmail());
        } else {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        btnVwUs482.setOnClickListener(v ->
                startActivity(new Intent(this, ViewStudentsActivity.class))
        );

        btnUplCuAdmin482.setOnClickListener(v ->
                startActivity(new Intent(this, UploadCourseActivity.class))
        );

        btnLoOuAd482.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
