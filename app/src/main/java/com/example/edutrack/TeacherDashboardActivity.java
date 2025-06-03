package com.example.edutrack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class TeacherDashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextView tvWelcomeTeacher;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");


        tvWelcomeTeacher = findViewById(R.id.tvWelcomeTeacher);
        CardView cardViewStudents482 = findViewById(R.id.cardViewStudents);
        CardView cardManageCourses482 = findViewById(R.id.cardManageCourses);
        CardView cardChat482 = findViewById(R.id.cardChat);
        CardView cardLogout482 = findViewById(R.id.cardLogout);


        if (currentUser != null) {
            String uid = currentUser.getUid();

            usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        tvWelcomeTeacher.setText("Welcome, " + (name != null ? name : "Teacher"));
                    } else {
                        tvWelcomeTeacher.setText("Welcome, Teacher");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    tvWelcomeTeacher.setText("Welcome");
                    Toast.makeText(TeacherDashboardActivity.this, "Error loading name", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


        cardViewStudents482.setOnClickListener(v ->
                startActivity(new Intent(this, ViewStudentsActivity.class))
        );

        cardManageCourses482.setOnClickListener(v ->
                startActivity(new Intent(this, UploadCourseActivity.class))
        );

        cardChat482.setOnClickListener(v ->
                startActivity(new Intent(this, ChatActivity.class))
        );

        cardLogout482.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
