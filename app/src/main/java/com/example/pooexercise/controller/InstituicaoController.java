package com.example.pooexercise.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.pooexercise.activitys.CadastrarInstituicao;
import com.example.pooexercise.activitys.PerfilUsuario;
import com.example.pooexercise.model.UserModel;
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

public class InstituicaoController {

    public static Boolean operacao;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public InstituicaoController() {
        mAuth = FirebaseAuth.getInstance();

    }


    public void cadastrarInstituicao(@NonNull String userNameInstitution, String userEmailInstituion, String userPasswordIstitution,
                                     String userCnpjInstitution, String userPhoneInstitution, String userAddressInstitution,
                                     Uri imageUri, CadastrarInstituicao cadastrarInstituicao) {


        //Criar um usuário no auth
        mAuth.createUserWithEmailAndPassword(userEmailInstituion, userPasswordIstitution)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            cadastrarInstituicao.progressBarInstitution.setVisibility(View.VISIBLE);

                            currentUser = mAuth.getCurrentUser();

                            UserProfileChangeRequest userRoleSetUp = new UserProfileChangeRequest.Builder()
                                    .setDisplayName("admin")
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

                            String idInstitution = currentUser.getUid();
                            StorageReference refereImage = FirebaseStorage.getInstance().getReference("foto").child("usuario/" + idInstitution);

                            //Adicionar a foto da instituição no storage
                            refereImage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    refereImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String urlPhotoInstitution = uri.toString();
                                            String userRole = "admin";

                                            UserModel institutionModel = new UserModel(userNameInstitution, userEmailInstituion, userCnpjInstitution,
                                                    userPhoneInstitution, userAddressInstitution, userRole,
                                                    urlPhotoInstitution, idInstitution);

                                            DatabaseReference mbase = FirebaseDatabase.getInstance().getReference("usuario").child(idInstitution);

                                            //Enviar pro realtime o perfil desse usuário que foi cadastrado no auth, criando um nó com o UID do user com o auth
                                            mbase.setValue(institutionModel)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            cadastrarInstituicao.progressBarInstitution.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(cadastrarInstituicao, "A instituição foi cadastrada com sucesso!!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            cadastrarInstituicao.progressBarInstitution.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(cadastrarInstituicao, "Não foi possível realizar o cadastro da sua instituição, tente novamente!" + e.getMessage(), Toast.LENGTH_SHORT).show();
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


    public void recuperarInstituicao(String idInstituicao, PerfilUsuario perfilUsuario) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("usuario").child(idInstituicao);

        ValueEventListener animalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);

                perfilUsuario.nameInstituicao.setText(userModel.getUserNameInstitution());
                perfilUsuario.cnpjInstituicao.setText(userModel.getUserCnpjInstitution());
                perfilUsuario.enderecoInstituicao.setText(userModel.getUserAddressInstitution());
                perfilUsuario.emailInstituicao.setText(userModel.getUserEmailInstituion());
                perfilUsuario.telefoneInstituicao.setText(userModel.getUserPhoneInstitution());
                String sUrlImage = userModel.getUrlImageInstitution();

                //Pegar um context antes, dentro do glide."with" tava bugando(Não esquecer)
                Context context = perfilUsuario.getApplicationContext();

                Glide.with(context).load(sUrlImage).into(perfilUsuario.imageInstituicao);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("cancelado", "Erro ao atualizar", databaseError.toException());
            }
        };

        ref.addListenerForSingleValueEvent(animalListener);


    }
}
