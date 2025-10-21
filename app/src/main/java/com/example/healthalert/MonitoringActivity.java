package com.example.healthalert;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthalert.models.patient;

import java.util.ArrayList;
import java.util.List;

public class MonitoringActivity extends AppCompatActivity {

    RecyclerView recyclerView = findViewById(R.id.recyclerPatients);
    patientAdapter adapter;
    List<patient> patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 🔹 Dummy patient data
        patientList = new ArrayList<patient>();
        patientList.add(new patient("John Doe", 120, 92, 38.7, "120 / 80" ));
        patientList.add(new patient("Jane Smith", 75, 98, 36.9, "130 / 60"));
        patientList.add(new patient("Michael Lee", 54, 89, 37.5, "110 / 82"));

        adapter = new patientAdapter(patientList);
        recyclerView.setAdapter(adapter);
    }
}
