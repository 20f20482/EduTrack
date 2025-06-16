package com.example.edutrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity {

    Toolbar topToolbar482;
    TextView tvQuestion482;
    RadioGroup optionsGroup482;
    RadioButton option1, option2, option3;
    Button btnNext482;

    ArrayList<String> questions482;
    ArrayList<String[]> options482;
    ArrayList<String[]> mapping482;

    int currentQuestion = 0;

    int scoreA = 0; // Engineering
    int scoreB = 0; // IT
    int scoreC = 0; // Medical
    int scoreD = 0; // Business
    int scoreE = 0; // Education

    DatabaseReference databaseRef;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        topToolbar482 = findViewById(R.id.topToolbar);
        setSupportActionBar(topToolbar482);

        tvQuestion482 = findViewById(R.id.tvQuestion);
        optionsGroup482 = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        btnNext482 = findViewById(R.id.btnNext);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("quizResults");

        questions482 = new ArrayList<>();
        options482 = new ArrayList<>();
        mapping482 = new ArrayList<>();


        questions482.add("Which subject do you like?");
        options482.add(new String[]{"Math ", "Computers", "Sciences"});
        mapping482.add(new String[]{"A", "B", "C"});

        questions482.add("What placr suits you?");
        options482.add(new String[]{"Classroom & Students", "Office & Management", "Clinic & Patients"});
        mapping482.add(new String[]{"E", "D", "C"});

        questions482.add("What activity do you feel comfortable doing?");
        options482.add(new String[]{"Designing", "Teaching", "Patient assistance"});
        mapping482.add(new String[]{"A", "E", "C"});

        questions482.add("Choose what motivates you more:");
        options482.add(new String[]{"Helping people recover", "Starting a business", "Creating new apps"});
        mapping482.add(new String[]{"C", "D", "B"});

        questions482.add("Which skill best describes you?");
        options482.add(new String[]{"Logical problem solving", "Communication and empathy", "Leadership and decision making"});
        mapping482.add(new String[]{"A", "E", "D"});

        loadQuestion();

        btnNext482.setOnClickListener(v -> {
            int selectedId = optionsGroup482.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedIndex = optionsGroup482.indexOfChild(findViewById(selectedId));
            String selectedPath = mapping482.get(currentQuestion)[selectedIndex];

            switch (selectedPath) {
                case "A":
                    scoreA++; break;
                case "B":
                    scoreB++; break;
                case "C":
                    scoreC++; break;
                case "D":
                    scoreD++; break;
                case "E":
                    scoreE++; break;
            }

            currentQuestion++;

            if (currentQuestion < questions482.size()) {
                loadQuestion();
            } else {
                showResult();
            }
        });

    }

    private void loadQuestion() {
        tvQuestion482.setText(questions482.get(currentQuestion));
        optionsGroup482.clearCheck();
        option1.setText(options482.get(currentQuestion)[0]);
        option2.setText(options482.get(currentQuestion)[1]);
        option3.setText(options482.get(currentQuestion)[2]);
    }

    private void showResult() {
        String pathway;
        String feedback;

        int maxScore = Math.max(scoreA, Math.max(scoreB, Math.max(scoreC, Math.max(scoreD, scoreE))));

        if (maxScore == scoreA) {
            pathway = "Engineering";
            feedback = "You have strong problem-solving and analytical skills.";
        } else if (maxScore == scoreB) {
            pathway = "IT";
            feedback = "You're innovative and love working with technology.";
        } else if (maxScore == scoreC) {
            pathway = "Medical";
            feedback = "You care deeply about health and helping others.";
        } else if (maxScore == scoreD) {
            pathway = "Business";
            feedback = "You're a natural leader with business sense.";
        } else {
            pathway = "Education";
            feedback = "You’re passionate about teaching and sharing knowledge.";
        }

        saveResultToFirebase(pathway);

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("pathway", pathway);
        intent.putExtra("feedback", feedback);
        boolean score = false;
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    private void saveResultToFirebase(String pathway) {
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        databaseRef.child(userId).setValue(pathway)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Result saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save result.", Toast.LENGTH_SHORT).show());
    }
}
