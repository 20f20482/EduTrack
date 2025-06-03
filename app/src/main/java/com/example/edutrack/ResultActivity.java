package com.example.edutrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    TextView tvPathwayName482, tvFeedback482;
    ImageView imageResult482;
    Toolbar topToolbar482;
    Button btnGoHome482;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        topToolbar482 = findViewById(R.id.topToolbar);
        tvPathwayName482 = findViewById(R.id.tvPathwayName);
        tvFeedback482 = findViewById(R.id.tvFeedback);
        imageResult482 = findViewById(R.id.imageResult);
        btnGoHome482 = findViewById(R.id.btnGoHome);


        String recommendedPathway = getIntent().getStringExtra("pathway");
        String feedback = getIntent().getStringExtra("feedback");


        tvPathwayName482.setText(recommendedPathway);
        tvFeedback482.setText(feedback);


        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference resultRef = FirebaseDatabase.getInstance()
                .getReference("quizResults").child(userId);

        resultRef.setValue(new QuizResult(recommendedPathway, feedback));


        btnGoHome482.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }


    public static class QuizResult {
        public String pathway;
        public String feedback;

        public QuizResult(String pathway, String feedback) {
            this.pathway = pathway;
            this.feedback = feedback;
        }
    }
}
