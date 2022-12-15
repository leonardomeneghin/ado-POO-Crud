package com.example.pooexercise.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.pooexercise.activitys.CadastroIntegrante;
import com.example.pooexercise.activitys.PerfilIntegrante;
import com.example.pooexercise.model.IntegranteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class IntegranteController {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String idIntegrante;
    private StorageReference storageReference;

    public IntegranteController() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void recuperarIntegrante(String idIntegrante, PerfilIntegrante perfilIntegrante) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("usuario").child("integrante").child(idIntegrante);

        ValueEventListener integranteListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                IntegranteModel integranteModel = dataSnapshot.getValue(IntegranteModel.class);

                perfilIntegrante.nameIntegrante.setText(integranteModel.getNameIntegrante());
                perfilIntegrante.functionIntegrante.setText(integranteModel.getFunctionIntegrante());
                perfilIntegrante.rgIntegrante.setText(integranteModel.getRgIntegrante());
                perfilIntegrante.emailIntegrante.setText(integranteModel.getEmailIntegrante());
                perfilIntegrante.cpfIntegrante.setText(integranteModel.getCpfIntegrante());
                perfilIntegrante.phoneIntegrante.setText(integranteModel.getPhoneIntegrate());
                perfilIntegrante.passwordIntegrante.setText(integranteModel.getPasswordIntegrante());
                String sUrlImage = integranteModel.getUrlImageIntegrante();

                //Pegar um context antes, dentro do glide."with" tava bugando(Não esquecer)
                Context context = perfilIntegrante.getApplicationContext();

                Glide.with(context).load(sUrlImage).into(perfilIntegrante.imageIntegrante);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("cancelado", "Erro ao atualizar", databaseError.toException());
            }
        };

        ref.addListenerForSingleValueEvent(integranteListener);

    }

    public void cadastrarIntegrante(String nameIntegrante, String passwordIntegrante, String emailIntegrante, String functionIntegrante, String cpfIntegrante, String rgIntegrante,
                                    String phoneIntegrate, Uri imageUri, CadastroIntegrante cadastroIntegrante) {


        SharedPreferences sharedPreferences = cadastroIntegrante.getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        String idInstitution = sharedPreferences.getString("institutionId", "");

        mAuth.createUserWithEmailAndPassword(emailIntegrante, passwordIntegrante).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    cadastroIntegrante.progressBarIntegrate.setVisibility(View.VISIBLE);

                    currentUser = mAuth.getCurrentUser();

                    UserProfileChangeRequest userRoleSetUp = new UserProfileChangeRequest.Builder()
                            .setDisplayName("normal")
                            .build();

                    //Adicionar no auth qual o tipo de usuário
                    currentUser.updateProfile(userRoleSetUp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Teste", "role adicionada");
                            }
                        }
                    });

                    String idIntegrante = currentUser.getUid();

                    StorageReference refereImage = FirebaseStorage.getInstance().getReference("foto").child("usuario/" + idIntegrante);

                    refereImage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            refereImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String urlImageIntegrate = uri.toString();
                                    String userRole = "normal";

                                    IntegranteModel integranteModel = new IntegranteModel(nameIntegrante, emailIntegrante, functionIntegrante, cpfIntegrante,
                                            rgIntegrante, phoneIntegrate, urlImageIntegrate, userRole, idInstitution, idIntegrante, passwordIntegrante);

                                    DatabaseReference mbase = FirebaseDatabase.getInstance().getReference("usuario").child("integrante").child(idIntegrante);

                                    mbase.setValue(integranteModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    cadastroIntegrante.progressBarIntegrate.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(cadastroIntegrante, "O integrante foi cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    cadastroIntegrante.progressBarIntegrate.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(cadastroIntegrante, "Não foi possivel cadastrar o integrante!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    });

                }
            }
        });

    }

    /*Metodos que precisam realizar operações
    de editar o integrante
    */
    public void editarIntegrante(String nameIntegrante, String passwordIntegrante, String emailIntegrante, String functionIntegrante, String cpfIntegrante, String rgIntegrante,
                                 String phoneIntegrate, PerfilIntegrante perfilIntegrante) {


        SharedPreferences sharedPreferences = perfilIntegrante.getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        String idInstitution = sharedPreferences.getString("institutionId", "");

        FirebaseAuth mbase = FirebaseAuth.getInstance();


        mbase.signInWithEmailAndPassword(emailIntegrante, passwordIntegrante).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    perfilIntegrante.progressBarIntegrante.setVisibility(View.VISIBLE);
                    FirebaseUser user = mbase.getCurrentUser();
                    idIntegrante = user.getUid();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("usuario").child("integrante").child(idIntegrante);

                    //Instancia um hash map
                    HashMap integranteMap = new HashMap();
                    integranteMap.put("nameIntegrante", nameIntegrante);
                    integranteMap.put("emailIntegrante", emailIntegrante);
                    integranteMap.put("passwordIntegrante", passwordIntegrante);
                    integranteMap.put("functionIntegrante", functionIntegrante);
                    integranteMap.put("cpfIntegrante", cpfIntegrante);
                    integranteMap.put("rgIntegrante", rgIntegrante);
                    integranteMap.put("phoneIntegrate", phoneIntegrate);


                    //Atualizar o integrante no real time
                    ref.updateChildren(integranteMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    perfilIntegrante.progressBarIntegrante.setVisibility(View.INVISIBLE);
                                    Toast.makeText(perfilIntegrante, "Integrante foi atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    perfilIntegrante.progressBarIntegrante.setVisibility(View.INVISIBLE);
                                    Toast.makeText(perfilIntegrante, "Não foi possivel atualizar o perfil do integrante, tente novamente" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }

    public void editarFotoIntegrante(PerfilIntegrante perfilIntegrante, String idIntegrante, Uri uri) {

        StorageReference referenceImage = FirebaseStorage.getInstance().getReference("foto").child("usuario/" + idIntegrante);

        //salvar imagem no Storage do firebase
        referenceImage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                perfilIntegrante.progressBarIntegrante.setVisibility(View.VISIBLE);
                referenceImage.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String urlImageDog = uri.toString();

                                FirebaseDatabase.getInstance().getReference("usuario")
                                        .child("integrante")
                                        .child(idIntegrante)
                                        .child("urlImageIntegrante")
                                        .setValue(urlImageDog)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                perfilIntegrante.progressBarIntegrante.setVisibility(View.INVISIBLE);
                                                Toast.makeText(perfilIntegrante, "Foto do integrante foi alterada com sucesso!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                perfilIntegrante.progressBarIntegrante.setVisibility(View.INVISIBLE);
                                                Toast.makeText(perfilIntegrante, "Não foi possivel atualizar a foto do integrante : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        });

            }
        });


    }


}
