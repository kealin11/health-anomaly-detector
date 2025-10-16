package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText etUsername, etEmail, etPassword, etAge, etMedicalAid;
    FloatingActionButton fabCreateAccount;   // floating yellow button
    TextView tvAlreadyHave;                  // "Already have an account? Log in" link
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_sign_up);

        // Bind UI elements
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etAge = findViewById(R.id.etAge);
        etMedicalAid = findViewById(R.id.etMedicalAid);
        fabCreateAccount = findViewById(R.id.fabCreateAccount);
        tvAlreadyHave = findViewById(R.id.tvAlreadyHave); // make sure this TextView exists in XML

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // ✅ Handle Create Account button click
        fabCreateAccount.setOnClickListener(v -> {
            String username = safeText(etUsername);
            String email = safeText(etEmail);
            String password = safeText(etPassword);
            String ageStr = safeText(etAge);
            String medicalAid = safeText(etMedicalAid);

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username, email and password are required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create user with Firebase Auth
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user == null) {
                                Toast.makeText(this, "User creation succeeded but user is null", Toast.LENGTH_LONG).show();
                                return;
                            }
                            String uid = user.getUid();

                            // Prepare additional profile data
                            Map<String, Object> profile = new HashMap<>();
                            profile.put("username", username);
                            profile.put("email", email);
                            profile.put("medicalAidNumber", medicalAid);
                            profile.put("age", parseIntOrNull(ageStr));
                            profile.put("createdAt", System.currentTimeMillis());

                            // Save to Firestore under users/{uid}
                            db.collection("users").document(uid)
                                    .set(profile)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();

                                        // ✅ Redirect back to Login screen
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Failed to save profile: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    });

                        } else {
                            String errorMsg = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            if (errorMsg.contains("already in use")) {
                                // ✅ If account already exists, go back to login
                                Toast.makeText(this, "Account already exists. Please log in.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(this, "Sign Up failed: " + errorMsg, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });

        // ✅ Handle "Already have an account? Log in" link
        if (tvAlreadyHave != null) {
            tvAlreadyHave.setOnClickListener(v -> {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private String safeText(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString().trim();
    }

    private Integer parseIntOrNull(String s) {
        try {
            return s.isEmpty() ? null : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
