package com.example.edutrack;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PathwayDetailActivity extends AppCompatActivity {


    Toolbar topToolbar482;
    TextView tvPathwayTitle482, tvDescription482, tvSkills482, tvJobs482, tvSubjects482;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathway_detail);


        topToolbar482 = findViewById(R.id.topToolbar);
        tvPathwayTitle482 = findViewById(R.id.tvPathwayTitle);
        tvDescription482 = findViewById(R.id.tvDescription);
        tvSkills482 = findViewById(R.id.tvSkills);
        tvJobs482 = findViewById(R.id.tvJobs);
        tvSubjects482 = findViewById(R.id.tvSubjects);


        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String skills = getIntent().getStringExtra("skills");
        String jobs = getIntent().getStringExtra("jobs");
        String subjects = getIntent().getStringExtra("subjects");


        tvPathwayTitle482.setText(title);
        tvDescription482.setText(description);
        tvSkills482.setText(skills);
        tvJobs482.setText(jobs);
        tvSubjects482.setText(subjects);


        topToolbar482.setTitle(title);

    }
}
