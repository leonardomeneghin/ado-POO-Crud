package com.example.pooexercise.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.pooexercise.activitys.CadastroAnimal;
import com.example.pooexercise.activitys.PerfilAnimal;
import com.example.pooexercise.model.AnimalModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;


public class AnimalController {
    //Referencia e instancia do database
    public static Boolean operacao;
    private StorageReference storageReference;

    public AnimalController() {
        //Construtor do animal
        //AnimalModel animal = new AnimalModel();


    }


    /*Metodos que precisam realizar operações
    de recuperação de dados
    */
    public void recuperaAnimal(String idAnimal, PerfilAnimal perfilAnimal) {

        SharedPreferences pref = perfilAnimal.getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        String idInstitution = pref.getString("institutionId", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("usuario").child(idInstitution).child("animal").child(idAnimal);

        ValueEventListener animalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AnimalModel animalModel = dataSnapshot.getValue(AnimalModel.class);

                perfilAnimal.animalName.setText(animalModel.getAnimalName());
                perfilAnimal.animalBreed.setText(animalModel.getAnimelBreed());
                perfilAnimal.animalAge.setText(animalModel.getAnimalAge());
                perfilAnimal.animalSize.setText(animalModel.getAnimalSize());
                perfilAnimal.animalMedicine.setText(animalModel.getAnimalMedicine());
                perfilAnimal.animalMedicineTime.setText(animalModel.getAnimalTimeMedicine());
                perfilAnimal.animalObs.setText(animalModel.getAnimalObs());
                String sUrlImage = animalModel.getUrlImageDog();

                //Pegar um context antes, dentro do glide."with" tava bugando(Não esquecer)
                Context context = perfilAnimal.getApplicationContext();

                Glide.with(context).load(sUrlImage).into(perfilAnimal.animalImageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("cancelado", "Erro ao atualizar", databaseError.toException());
            }
        };

        ref.addListenerForSingleValueEvent(animalListener);

    }

    public void cadastrarAnimal(String sNomeDoAnimal, String sRacaDoAnimal, String sIdadeDoAnimal, String sPorteDoAnimal, String sMedicamentoAnimal, String sHorarioMedicamento,
                                String sObservacoesDoAnimal, CadastroAnimal cadastroAnimal, Uri imageUri) {

        //Gerar um UUID random para salvar um novo nó de animal no banco
        String idAnimal = UUID.randomUUID().toString();

        //Referenciar em qual nó vai ser salvo, baseado no id do animal
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("foto").child("animal/" + idAnimal);

        //Salvar imagem no Storage do firebase
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                cadastroAnimal.progressBarAnimal.setVisibility(View.VISIBLE);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //String para url do animal
                        String urlImageDog = uri.toString();

                        SharedPreferences sharedPreferences = cadastroAnimal.getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
                        String idInstitution = sharedPreferences.getString("institutionId", "");

                        //Instancia um modelo de animal para salvar no realtime
                        AnimalModel animalModel = new AnimalModel(idInstitution,idAnimal, sNomeDoAnimal, sRacaDoAnimal, sIdadeDoAnimal, sPorteDoAnimal, sMedicamentoAnimal,
                                                                  sHorarioMedicamento, sObservacoesDoAnimal, urlImageDog);

                        FirebaseDatabase mBase = FirebaseDatabase.getInstance();

                        //Salvar no realtime o animal
                        mBase.getReference("usuario")
                                .child(idInstitution)
                                .child("animal")
                                .child(idAnimal)
                                .setValue(animalModel)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        cadastroAnimal.progressBarAnimal.setVisibility(View.INVISIBLE);
                                        Toast.makeText(cadastroAnimal, "Animal foi cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        cadastroAnimal.progressBarAnimal.setVisibility(View.INVISIBLE);
                                        Toast.makeText(cadastroAnimal, "Não foi possivel realizar o cadastro tente novamente!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });

    }

    /*Metodos que precisam realizar operações
    de editar o animal
    */
    public void editarAnimal(String idAnimal, String sNomeDoAnimal, String sRacaDoAnimal, String sIdadeDoAnimal, String sPorteDoAnimal, String sMedicamentoAnimal, String sHorarioMedicamento,
                             String sObservacoesDoAnimal, PerfilAnimal perfilAnimal) {


        SharedPreferences sharedPreferences = perfilAnimal.getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        String idInstitution = sharedPreferences.getString("institutionId", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("usuario").child(idInstitution).child("animal").child(idAnimal);

        //Instancia um hash map
        HashMap animalMap = new HashMap();
        animalMap.put("animalName", sNomeDoAnimal);
        animalMap.put("animalBreed", sRacaDoAnimal);
        animalMap.put("animalAge", sIdadeDoAnimal);
        animalMap.put("animalSize", sPorteDoAnimal);
        animalMap.put("animalMedicine", sMedicamentoAnimal);
        animalMap.put("animalTimeMedicine", sHorarioMedicamento);
        animalMap.put("animalObs", sObservacoesDoAnimal);


        //Atualizar o animal no real time
        ref.updateChildren(animalMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(perfilAnimal, "Animal foi atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(perfilAnimal, "Não foi possivel atualizar o animal, tente novamente" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void editarFotoAnimal(PerfilAnimal perfilAnimal, String idAnimal, Uri uri) {


        //Referenciar em qual nó vai ser salvo, baseado no id do animal
        Log.d("tag", "teste id: " + idAnimal);

        StorageReference referenceImage = FirebaseStorage.getInstance().getReference("foto").child("animal/" + idAnimal);


        SharedPreferences sharedPreferences = perfilAnimal.getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        String idInstitution = sharedPreferences.getString("institutionId", "");

        //salvar imagem no Storage do firebase
        referenceImage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                perfilAnimal.progressBarPerfil.setVisibility(View.VISIBLE);
                referenceImage.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String urlImageDog = uri.toString();

                                FirebaseDatabase.getInstance().getReference("usuario")
                                        .child(idInstitution)
                                        .child("animal")
                                        .child(idAnimal)
                                        .child("urlImageDog")
                                        .setValue(urlImageDog)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                perfilAnimal.progressBarPerfil.setVisibility(View.INVISIBLE);
                                                Toast.makeText(perfilAnimal, "Foto do animal foi alterada com sucesso!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                perfilAnimal.progressBarPerfil.setVisibility(View.INVISIBLE);
                                                Toast.makeText(perfilAnimal, "Não foi possivel atualizar a foto do animal : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        });

            }
        });


    }

}
