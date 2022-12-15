package com.example.pooexercise.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.pooexercise.R;
import com.example.pooexercise.databinding.ActivityMainBinding;
import com.example.pooexercise.model.IntegranteModel;
import com.example.pooexercise.model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    public ImageView imgPerfil;
    public TextView navEmail, navName;
    public View navHeader;
    public NavigationView navigationView;
    String roleUser, idInstitutionCurrentUser, idIntegrantePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences pref = getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        idInstitutionCurrentUser = pref.getString("institutionId", "");
        roleUser = pref.getString("userRole", "");


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        bottomNavigationView = findViewById(R.id.nav_bottom_bar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_integrantes, R.id.nav_sair)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        onInit();

        updateNav();

    }

    private void onInit() {
        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);

        imgPerfil = navHeader.findViewById(R.id.image_icon_perfil);
        navEmail = navHeader.findViewById(R.id.text_email_perfil_nav);
        navName = navHeader.findViewById(R.id.text_nome_perfil_nav);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_top_bar, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void loggoutUser() {
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedPref = getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void perfilView() {
        if (roleUser.equals("admin")) {
            Intent intent = new Intent(this, PerfilUsuario.class);
            intent.putExtra("idInstitution", idInstitutionCurrentUser);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, PerfilIntegrante.class);
            intent.putExtra("idIntegrante", idIntegrantePerfil);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void onClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_sair:
                loggoutUser();
                break;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_icon_perfil:
                perfilView();
                break;
        }
    }

    public void updateNav() {
        SharedPreferences sharedPref = getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        String institutionIdNav = sharedPref.getString("institutionId", "");
        String rolerUserNav = sharedPref.getString("userRole", "");

        if (rolerUserNav.equals("admin")) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuario").child(institutionIdNav);

            ValueEventListener institutionNavListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);

                    navEmail.setText(userModel.getUserEmailInstituion());
                    navName.setText(userModel.getUserNameInstitution());
                    String urlImage = userModel.getUrlImageInstitution();

                    Glide.with(getApplicationContext()).load(urlImage).into(imgPerfil);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            };

            ref.addListenerForSingleValueEvent(institutionNavListener);
        } else {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuario").child("integrante").child(user.getUid());

            ValueEventListener integranteListenerNav = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    IntegranteModel integranteModel = snapshot.getValue(IntegranteModel.class);

                    navEmail.setText(integranteModel.getEmailIntegrante());
                    navName.setText(integranteModel.getNameIntegrante());
                    String urlImage = integranteModel.getUrlImageIntegrante();
                    idIntegrantePerfil = integranteModel.getIdIntegrante();

                    Glide.with(getApplicationContext()).load(urlImage).into(imgPerfil);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            };

            ref.addListenerForSingleValueEvent(integranteListenerNav);
        }


    }

}