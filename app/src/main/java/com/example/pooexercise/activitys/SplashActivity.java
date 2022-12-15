package com.example.pooexercise.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pooexercise.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        testUser();
    }

    public void testUser() {

        SharedPreferences sharedPreferences = getSharedPreferences("idInstitutionCurrentUser", MODE_PRIVATE);
        String userInstitution = sharedPreferences.getString("institutionId", "");
        String currentUserUid = sharedPreferences.getString("currentUserUid", "");
        String userRole = sharedPreferences.getString("userRole", "");


        if (userInstitution.isEmpty() || currentUserUid.isEmpty() || userRole.isEmpty()) {
            Log.d("test", "entrei no if :" + userInstitution);
            Intent intent = new Intent(SplashActivity.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            Log.d("test", "entrei no else :");
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}