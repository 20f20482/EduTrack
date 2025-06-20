package com.example.edutrack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PathwaysActivity extends AppCompatActivity {

    Toolbar topToolbar482, bottomToolbar482;
    LinearLayout pathwaysContainer482;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathways);


        topToolbar482 = findViewById(R.id.topToolbar);
        bottomToolbar482 = findViewById(R.id.bottomToolbar);
        pathwaysContainer482 = findViewById(R.id.pathwaysContainer);


        addPathway("Engineering Pathway",
                "Explore careers in mechanical, electrical, and civil engineering.",
                R.drawable.ic_engineering,
                "Application of science and math to solve technical problems.",
                "Mathematics, Design, Analysis, CAD",
                "Civil Engineer, Mechanical Engineer, Electrical Engineer",
                "Statics, Thermodynamics, Circuits, Mechanics");

        addPathway("Computer Science Pathway",
                "Discover software development, AI, and data science careers.",
                R.drawable.ic_it,
                "Study of computation, programming, and data systems.",
                "Java, Algorithms, Python, Databases",
                "Software Developer, Data Scientist, AI Engineer",
                "Data Structures, Operating Systems, Web Development");

        addPathway("Medicine Pathway",
                "Pursue careers in healthcare, surgery, and research.",
                R.drawable.ic_medical,
                "Medical science focused on diagnosis and treatment of diseases.",
                "Critical Thinking,Empathy , Anatomy",
                "Doctor, Surgeon, Medical Researcher",
                "Physiology, Pharmacology, Pathology,Biochemistry ");

        addPathway("Business Pathway",
                "Develop skills in management, marketing, and finance.",
                R.drawable.ic_business1,
                "Focus on business operations, management, and strategy.",
                "Financial Analysis,Leadership , Communication",
                "Marketing Specialist,ManagerHR Officer , Business ",
                "Accounting,Management , Economics,Marketing ");

        addPathway("Education Pathway",
                "A Future as an Education Specialist and Teacher.",
                R.drawable.ic_education,
                "Discovering teaching and learning methods.",
                "Planning,Assessment , Communication",
                "Educational Consultant, Principal, Teacher",
                "Curriculum Design,Psychology ,Pedagogy ");

    }

    private void addPathway(String title, String subtitle, int imageRes,
                            String description, String skills, String jobs, String subjects) {

        @SuppressLint("InflateParams") View cardView = LayoutInflater.from(this).inflate(R.layout.item_pathway_card, null);


        ImageView icon = cardView.findViewById(R.id.icon);
        TextView titleView = cardView.findViewById(R.id.title);
        TextView subtitleView = cardView.findViewById(R.id.subtitle);

        icon.setImageResource(imageRes);
        titleView.setText(title);
        subtitleView.setText(subtitle);


        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(PathwaysActivity.this, PathwayDetailActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            intent.putExtra("skills", skills);
            intent.putExtra("jobs", jobs);
            intent.putExtra("subjects", subjects);
            startActivity(intent);
        });


        pathwaysContainer482.addView(cardView);
    }
}
