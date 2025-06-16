package com.example.edutrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CourseDetailActivity extends AppCompatActivity {

    Toolbar topToolbar482, bottomToolbar482;
    TextView tvCourseTitle482, tvCourseDescription482, tvCourseLink482;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);


        topToolbar482 = findViewById(R.id.topToolbar);
        bottomToolbar482 = findViewById(R.id.bottomToolbar);
        tvCourseTitle482 = findViewById(R.id.tvCourseTitle);
        tvCourseDescription482 = findViewById(R.id.tvCourseDescription);
        tvCourseLink482 = findViewById(R.id.tvCourseLink);


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String link = intent.getStringExtra("link");


        tvCourseTitle482.setText(title);
        tvCourseDescription482.setText(description);


        tvCourseLink482.setOnClickListener(view -> {
            if (link != null && !link.isEmpty()) {
                Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(openLinkIntent);
            }
        });


        topToolbar482.setTitle(title);


        topToolbar482.setNavigationIcon(R.drawable.ic_back);
        topToolbar482.setNavigationOnClickListener(v -> finish());

    }
}
