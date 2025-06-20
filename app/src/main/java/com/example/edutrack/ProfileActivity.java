package com.example.edutrack;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    Toolbar topToolbar482;
    TextView tvUsername482, tvEmail482, tvFavoritePathway482, tvTestResults482, tvTestScore482;

    FirebaseAuth mAuth;
    DatabaseReference userRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        topToolbar482 = findViewById(R.id.topToolbar);
        tvUsername482 = findViewById(R.id.tvUsername);
        tvEmail482 = findViewById(R.id.tvEmail);
        tvFavoritePathway482 = findViewById(R.id.tvFavoritePathway);
        tvTestResults482 = findViewById(R.id.tvTestResults);
        tvTestScore482 = findViewById(R.id.tvTestScore);

        setSupportActionBar(topToolbar482);
        Objects.requireNonNull(getSupportActionBar()).setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish();

        }

        assert currentUser != null;
        String uid = currentUser.getUid();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String username = snapshot.child("username").getValue(String.class);
                    String email = currentUser.getEmail();
                    String Pathway = snapshot.child("pathway").getValue(String.class);
                    String testResults = snapshot.child("quizResults").getValue(String.class);
                    String testScore = snapshot.child("score").getValue(String.class);
                    

                    tvUsername482.setText(username != null ? username : "N/A");
                    tvEmail482.setText(email != null ? email : "N/A");
                    tvFavoritePathway482.setText(Pathway != null ? Pathway : "N/A");
                    tvTestResults482.setText(testResults != null ? testResults : "No test results yet");
                    tvTestScore482.setText(testScore != null ? testScore : "");
                } else {
                    Toast.makeText(ProfileActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
