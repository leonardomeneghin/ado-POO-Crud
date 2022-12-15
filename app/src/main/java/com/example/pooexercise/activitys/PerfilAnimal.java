package com.example.pooexercise.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pooexercise.R;
import com.example.pooexercise.controller.AnimalController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vicmikhailau.maskededittext.MaskedEditText;

public class PerfilAnimal extends AppCompatActivity {

    private AnimalController animalController = new AnimalController();//Instancia da controller no listenner
    public ImageView backButton;
    public String name = "";
    public FloatingActionButton floatingEditMode, floatingBackNormalMode, floatingSaveEdit;
    public EditText animalName, animalBreed, animalAge, animalSize, animalMedicine, animalObs;
    public MaskedEditText  animalMedicineTime;
    public ImageView animalImageView;
    private Uri imageUri;
    public ProgressBar progressBarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_animal);

        onInit();

        lerDados();
    }

    private void lerDados() {
        AnimalController animalController = new AnimalController();
        animalController.recuperaAnimal(getIntent().getStringExtra("idAnimal"), PerfilAnimal.this);

    }

    private void onInit() {
        backButton = findViewById(R.id.card_back_button);
        floatingEditMode = findViewById(R.id.floating_edt_perfil_animal_card);
        floatingBackNormalMode = findViewById(R.id.floating_back_edit_animal_card);
        floatingSaveEdit = findViewById(R.id.floating_done_edit_card);
        animalName = findViewById(R.id.edittext_name_animal_card_perfil);
        animalImageView = findViewById(R.id.image_view_card_photo_animal);
        animalBreed = findViewById(R.id.edttext_raca_animal_card_perfil);
        animalAge = findViewById(R.id.edttext_idade_animal_card_perfil);
        animalSize = findViewById(R.id.edttext_porte_animal_card_perfil);
        animalMedicine = findViewById(R.id.edt_medicamento_animal_card_perfil);
        animalMedicineTime = findViewById(R.id.edt_horario_medicamento_animal_card);
        animalObs = findViewById(R.id.edt_observacoes_animal_card);
        progressBarPerfil = findViewById(R.id.progress_bar_perfil_animal_card);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_back_button:
                backHome();
                break;
            case R.id.floating_edt_perfil_animal_card:
                editMode();
                break;
            case R.id.floating_back_edit_animal_card:
                backNormalMode();
                break;
            case R.id.floating_done_edit_card:
                backNormalMode();
                saveEdit(testFields());
                break;
            case R.id.image_view_card_photo_animal:
                abrirGaleria();
                break;
        }
    }

    private void saveEdit(Boolean isSuccess) {
        if (isSuccess) {
            //Variaveis
            String sNomeDoAnimal = animalName.getText().toString();
            String sRacaDoAnimal = animalBreed.getText().toString();
            String sIdadeDoAnimal = animalAge.getText().toString();
            String sPorteDoAnimal = animalSize.getText().toString();
            String sMedicamentoAnimal = animalMedicine.getText().toString();
            String sHorarioMedicamento = animalMedicineTime.getText().toString();
            String sObservacoesDoAnimal = animalObs.getText().toString();


            //Passando os dados para a controler persistir no banco de dados
            animalController.editarAnimal(getIntent().getStringExtra("idAnimal"), sNomeDoAnimal, sRacaDoAnimal, sIdadeDoAnimal, sPorteDoAnimal, sMedicamentoAnimal,
                    sHorarioMedicamento, sObservacoesDoAnimal, PerfilAnimal.this);

        } else {
            // Deu merda, retorna uma exceção
            Toast.makeText(this, "Não foi possível prosseguir, verifique os campos de dados e tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }


    private void backNormalMode() {
        animalName.setEnabled(false);
        animalAge.setEnabled(false);
        animalBreed.setEnabled(false);
        animalSize.setEnabled(false);
        animalMedicine.setEnabled(false);
        animalMedicineTime.setEnabled(false);
        animalObs.setEnabled(false);

        floatingEditMode.setVisibility(View.VISIBLE);
        floatingSaveEdit.setClickable(false);
        floatingSaveEdit.setVisibility(View.INVISIBLE);
        floatingBackNormalMode.setClickable(true);
        floatingBackNormalMode.setVisibility(View.INVISIBLE);
    }

    public boolean testFields() {

        String sNomeDoAnimal = animalName.getText().toString();
        String sRacaDoAnimal = animalBreed.getText().toString();
        String sIdadeDoAnimal = animalAge.getText().toString();
        String sPorteDoAnimal = animalSize.getText().toString();
        String sMedicamentoAnimal = animalMedicine.getText().toString();
        String sHorarioMedicamento = animalMedicineTime.getText().toString();
        String sObservacoesDoAnimal = animalObs.getText().toString();


        if (sNomeDoAnimal.isEmpty() || sRacaDoAnimal.isEmpty() || sIdadeDoAnimal.isEmpty() || sPorteDoAnimal.isEmpty() || sMedicamentoAnimal.isEmpty()|| sHorarioMedicamento.isEmpty() || sObservacoesDoAnimal.isEmpty()) {
            return false;
        }
        return true;
    }

    private void editMode() {

        animalName.setEnabled(true);
        animalAge.setEnabled(true);
        animalSize.setEnabled(true);
        animalBreed.setEnabled(true);
        animalMedicine.setEnabled(true);
        animalMedicineTime.setEnabled(true);
        animalObs.setEnabled(true);

        floatingEditMode.setVisibility(View.INVISIBLE);
        floatingSaveEdit.setClickable(true);
        floatingSaveEdit.setVisibility(View.VISIBLE);
        floatingBackNormalMode.setClickable(true);
        floatingBackNormalMode.setVisibility(View.VISIBLE);
    }


    private void backHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirGaleria() {
        Intent intentGallery = new Intent();
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        intentGallery.setType("image/*");
        startActivityForResult(intentGallery, 2);

    }

    public void editarFoto() {
        animalController.editarFotoAnimal(PerfilAnimal.this, getIntent().getStringExtra("idAnimal"), imageUri);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();
            animalImageView.setImageURI(imageUri);

            AlertDialog.Builder builder = new AlertDialog.Builder(PerfilAnimal.this.animalImageView.getContext());
            builder.setTitle("Atualizar imagem");
            builder.setMessage("Você tem certeza que atualizar sua imagem?");

            builder.setPositiveButton("SIM", (dialogInterface, i) -> {
                editarFoto();
            });
            builder.setNegativeButton("NÃO", (dialogInterface, i) -> {

            });
            builder.show();

        }
    }
}