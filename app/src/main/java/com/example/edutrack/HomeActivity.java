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

    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
            return ;
        }

        Toolbar topToolbar482 = findViewById(R.id.topToolbar482);
        Toolbar bottomToolbar482 = findViewById(R.id.bottomToolbar482);
        SearchView searchView482 = findViewById(R.id.searchView482);

        CardView cardPathways = findViewById(R.id.cardPathways482);
        CardView cardQuiz482 = findViewById(R.id.cardQuiz482);
        CardView cardCourses482 = findViewById(R.id.cardCourses482);
        CardView cardProfile482 = findViewById(R.id.cardProfile482);
        CardView cardChat482 = findViewById(R.id.cardChat482);
        CardView cardJob482= findViewById(R.id.cardJob482);



        setSupportActionBar(topToolbar482);

        bottomToolbar482.inflateMenu(R.menu.toolbarmenu);

        bottomToolbar482.setOnMenuItemClickListener(item -> {
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

        cardQuiz482.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, QuizActivity.class)));

        cardCourses482.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, CoursesActivity.class)));

        cardProfile482.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));

        cardChat482.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ChatActivity.class)));

        cardJob482.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, JobsActivity.class)));



        searchView482.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(HomeActivity.this, "Searching here: " + query, Toast.LENGTH_SHORT).show();
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
