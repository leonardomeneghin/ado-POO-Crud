package com.example.pooexercise.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pooexercise.activitys.Login;
import com.example.pooexercise.activitys.MainActivity;
import com.example.pooexercise.model.IntegranteModel;
import com.example.pooexercise.model.LoginModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginController {

    private final FirebaseAuth mAuth;
    private String idInstitution, userRole;
    private Boolean operacao = null;


    public LoginController() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void singInUser(LoginModel loginModel, Login login) {

        //Salvar no shared preferences para autenticar o usuario e fazer consultas dentro do app (User session)
        SharedPreferences sharedPref = login.getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //Autenticar user no auth
        mAuth.signInWithEmailAndPassword(loginModel.getsEmail(), loginModel.getsPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user.isEmailVerified()) {

                                userRole = user.getDisplayName();

                                if (user.getDisplayName().equals("admin")) {

                                    idInstitution = user.getUid();

                                    editor.putString("institutionId", idInstitution);
                                    editor.putString("currentUserUid", user.getUid());
                                    editor.putString("userRole", userRole);
                                    editor.apply();

                                    loginTest();

                                } else if (user.getDisplayName().equals("normal")) {

                                    FirebaseDatabase mbase = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = mbase.getReference("usuario").child("integrante").child(user.getUid());

                                    ValueEventListener integranteListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            IntegranteModel integranteModel = snapshot.getValue(IntegranteModel.class);

                                            idInstitution = integranteModel.getIdInstitution();

                                            editor.putString("institutionId", idInstitution);
                                            editor.putString("currentUserUid", user.getUid());
                                            editor.putString("userRole", userRole);
                                            editor.apply();

                                            loginTest();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    };
                                    ref.addListenerForSingleValueEvent(integranteListener);

                                }
                            } else if (!user.isEmailVerified()) {
                                user.sendEmailVerification();
                                Toast.makeText(login, "Verifique seu e-mail para ter acesso a conta", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(login, "Erro na autenticação tente novamente", Toast.LENGTH_SHORT).show();

                        }
                    }

                    private void loginTest() {

                        SharedPreferences sharedPreferences = login.getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
                        String userId = sharedPreferences.getString("currentUserUid", "");
                        String idInstitution = sharedPreferences.getString("institutionId", "");
                        String roleUser = sharedPreferences.getString("userRole", "");

                        if (!userId.isEmpty() || !idInstitution.isEmpty() || !roleUser.isEmpty()) {
                            Intent intent = new Intent(login, MainActivity.class);
                            login.startActivity(intent);
                            login.finish();

                            Toast.makeText(login, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }

}

