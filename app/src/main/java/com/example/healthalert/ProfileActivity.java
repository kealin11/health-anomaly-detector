package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    LinearLayout btnDashboard, btnProfile, btnMessages;
    TextView tvName, tvEmail, tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // --- User Info ---
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);

        tvName.setText("Tom");
        tvEmail.setText("tom@icloud.com");
        tvPhone.setText("+27 123 456 789");

        // --- Bottom Navbar ---
        btnDashboard = findViewById(R.id.btn_dashboard);
        btnProfile = findViewById(R.id.btn_profile);
        btnMessages = findViewById(R.id.btn_messages);

        btnDashboard.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
            finish();
        });

        // Profile is current page
        btnProfile.setOnClickListener(v -> {});

        btnMessages.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, MessagesActivity.class));
            finish();
        });
    }
}
