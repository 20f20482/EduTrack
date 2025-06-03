package com.example.edutrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

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
    ArrayList<Integer> correctAnswers482;
    int currentQuestion = 0;
    int score = 0;

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
        databaseRef = FirebaseDatabase.getInstance().getReference("quiz_results");


        questions482 = new ArrayList<>();
        options482 = new ArrayList<>();
        correctAnswers482 = new ArrayList<>();


        questions482.add("Have languages and history been easier and more suspenseful for you than mathematics and science?");
        options482.add(new String[]{"Yes", "No", "I don't know"});
        correctAnswers482.add(0);

        questions482.add("Which subject do you prefer?");
        options482.add(new String[]{"Math", "Sciences", "Languages & Literature"});
        correctAnswers482.add(1);

        questions482.add("Do you prefer books more than any other way to collect information?");
        options482.add(new String[]{"Yes", "No", "I don't know"});
        correctAnswers482.add(2);

        questions482.add("Which environment suits you?");
        options482.add(new String[]{"Lab", "Classroom", "Office"});
        correctAnswers482.add(3);

        loadQuestion();

        btnNext482.setOnClickListener(v -> {
            int selectedId = optionsGroup482.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedAnswer = optionsGroup482.indexOfChild(findViewById(selectedId));
            if (selectedAnswer == correctAnswers482.get(currentQuestion)) {
                score++;
            }

            currentQuestion++;

            if (currentQuestion < questions482.size()) {
                loadQuestion();
            } else {
                saveResultToFirebase();
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
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

    private void saveResultToFirebase() {
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        databaseRef.child(userId).setValue(score)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Result saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save result.", Toast.LENGTH_SHORT).show());
    }
}
