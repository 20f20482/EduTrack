package com.example.edutrack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UploadCourseActivity extends AppCompatActivity {

    private TextInputEditText editTitle, editDescription, editLink;
    private DatabaseReference courseRef;

    private String courseId = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_course);

        editTitle = findViewById(R.id.editTextTitle);
        editDescription = findViewById(R.id.editTextDescription);
        editLink = findViewById(R.id.editTextLink);
        Button buttonUpload = findViewById(R.id.buttonUpload);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        courseRef = FirebaseDatabase.getInstance().getReference("Courses");


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("courseId")) {
            courseId = intent.getStringExtra("courseId");
            editTitle.setText(intent.getStringExtra("title"));
            editDescription.setText(intent.getStringExtra("description"));
            editLink.setText(intent.getStringExtra("link"));

            buttonUpload.setText("Update Course");
            buttonDelete.setVisibility(View.VISIBLE);
        }

        buttonUpload.setOnClickListener(view -> uploadOrUpdateCourse());
        buttonDelete.setOnClickListener(view -> showDeleteConfirmationDialog());

    }

    private void uploadOrUpdateCourse() {
        String title = Objects.requireNonNull(editTitle.getText()).toString().trim();
        String description = Objects.requireNonNull(editDescription.getText()).toString().trim();
        String link = Objects.requireNonNull(editLink.getText()).toString().trim();

        if (TextUtils.isEmpty(title)) {
            editTitle.setError("Enter course title");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            editDescription.setError("Enter course description");
            return;
        }

        if (TextUtils.isEmpty(link)) {
            editLink.setError("Enter course link");
            return;
        }

        if (courseId == null) {

            String id = courseRef.push().getKey();
            Course course = new Course(id, title, description, link);
            assert id != null;
            courseRef.child(id).setValue(course)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Course uploaded", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {

            Course course = new Course(courseId, title, description, link);
            courseRef.child(courseId).setValue(course)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Course")
                .setMessage("Are you sure you want to delete this course?")
                .setPositiveButton("Yes", (dialog, which) -> deleteCourse())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteCourse() {
        if (courseId != null) {
            courseRef.child(courseId).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Course deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}
