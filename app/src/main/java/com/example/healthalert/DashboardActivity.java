package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;


public class DashboardActivity extends AppCompatActivity {

    LinearLayout btnDashboard, btnProfile, btnMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // --- Mock data for dashboard ---
        TextView heartRateText = findViewById(R.id.textHeartRate);
        TextView tvOxygen = findViewById(R.id.tvOxygen);
        TextView tvTemp = findViewById(R.id.tvTemperature);
        TextView bloodPressureText = findViewById(R.id.textbloodPressure);

        heartRateText.setText("72 bpm");
        tvOxygen.setText("Oxygen: 91%");
        tvTemp.setText("Temperature: 38.6 °C");
        bloodPressureText.setText("Blood Pressure: 120 / 80 mmHg");


        // --- Bottom Navbar ---
        btnDashboard = findViewById(R.id.btn_dashboard);
        btnProfile = findViewById(R.id.btn_profile);
        btnMessages = findViewById(R.id.btn_messages);

        // Dashboard is current page
        btnDashboard.setOnClickListener(v -> {});

        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
            finish(); // Prevent stacking
        });

        btnMessages.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, MessagesActivity.class));
            finish();
        });
    }
}
