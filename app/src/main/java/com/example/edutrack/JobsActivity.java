package com.example.edutrack;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class JobsActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);


        Toolbar topToolbar = findViewById(R.id.topToolbar482);
        setSupportActionBar(topToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Show the jobs in Oman");
        }


        WebView jobsWebView = findViewById(R.id.jobsWeb482);
        WebSettings webSettings = jobsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        jobsWebView.setWebViewClient(new WebViewClient());
        jobsWebView.loadUrl("https://www.mol.gov.om/job");



    }



}
