package com.example.edutrack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
            return;
        }

        Toolbar topToolbar = findViewById(R.id.topToolbar);
        Toolbar bottomToolbar = findViewById(R.id.bottomToolbar);
        SearchView searchView = findViewById(R.id.searchView);

        CardView cardPathways = findViewById(R.id.cardPathways);
        CardView cardQuiz = findViewById(R.id.cardQuiz);
        CardView cardCourses = findViewById(R.id.cardCourses);
        CardView cardProfile = findViewById(R.id.cardProfile);
        CardView cardChat = findViewById(R.id.cardChat);
        CardView cardLogout = findViewById(R.id.cardLogout);


        setSupportActionBar(topToolbar);

        bottomToolbar.inflateMenu(R.menu.toolbarmenu);

        bottomToolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_pathways) {
                startActivity(new Intent(HomeActivity.this, PathwaysActivity.class));
                return true;
            } else if (id == R.id.menu_quiz) {
                startActivity(new Intent(HomeActivity.this, QuizActivity.class));
                return true;
            } else if (id == R.id.menu_courses) {
                startActivity(new Intent(HomeActivity.this, CoursesActivity.class));
                return true;
            } else if (id == R.id.menu_profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            } else if (id == R.id.menu_chat) {
                startActivity(new Intent(HomeActivity.this, ChatActivity.class));
                return true;
            } else if (id == R.id.menu_setting) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                return true;
            } else if (id == R.id.menu_logout) {
                logoutUser();
                return true;

            } else {
                return false;
            }
        });

        cardPathways.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, PathwaysActivity.class)));

        cardQuiz.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, QuizActivity.class)));

        cardCourses.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, CoursesActivity.class)));

        cardProfile.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));

        cardChat.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ChatActivity.class)));
        cardLogout.setOnClickListener(v -> logoutUser());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(HomeActivity.this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
