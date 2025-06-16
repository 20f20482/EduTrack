package com.example.edutrack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CoursesActivity extends AppCompatActivity implements CourseAdapter.OnCourseClickListener {

    RecyclerView recyclerViewCourses482;
    ArrayList<Course> courseList482;
    CourseAdapter adapter482;
    Toolbar topToolbar482, bottomToolbar482;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        topToolbar482 = findViewById(R.id.topToolbar482);
        bottomToolbar482 = findViewById(R.id.bottomToolbar482);
        setSupportActionBar(topToolbar482);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerViewCourses482 = findViewById(R.id.recyclerViewCourses482);
        recyclerViewCourses482.setLayoutManager(new LinearLayoutManager(this));

        courseList482 = new ArrayList<>();
        adapter482 = new CourseAdapter(courseList482, this);
        recyclerViewCourses482.setAdapter(adapter482);

        loadCoursesFromFirebase();

    }

    private void loadCoursesFromFirebase() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Courses");

        dbRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList482.clear();

                for (DataSnapshot courseSnap : snapshot.getChildren()) {
                    Course course = courseSnap.getValue(Course.class);
                    if (course != null) {
                        courseList482.add(course);
                    }
                }

                adapter482.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCourseClick(Course Courses) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("title", Courses.getTitle());
        intent.putExtra("description", Courses.getDescription());
        intent.putExtra("link", Courses.getLink());
        startActivity(intent);
    }
}
