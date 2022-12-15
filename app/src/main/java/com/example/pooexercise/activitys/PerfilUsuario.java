package com.example.pooexercise.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pooexercise.R;
import com.example.pooexercise.controller.InstituicaoController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PerfilUsuario extends AppCompatActivity {

    public EditText nameInstituicao, cnpjInstituicao, enderecoInstituicao, emailInstituicao, telefoneInstituicao;
    public ImageView backButton;
    public FloatingActionButton floatingEditMode, floatingBackNormal, floatingSaveEdit;
    public ImageView imageInstituicao;
    public Uri imageUri;
    public ProgressBar progressBarIntegrante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        onInit();

        lerDados();


    }

    private void onInit() {
        backButton = findViewById(R.id.card_back_button_instituion);
        nameInstituicao = findViewById(R.id.edittext_name_institution_card_perfil);
        cnpjInstituicao = findViewById(R.id.edttext_cnpj_institution_card_perfil);
        enderecoInstituicao = findViewById(R.id.edttext_endereco_institution_card_perfil);
        emailInstituicao = findViewById(R.id.edttext_email_instituicao_card_perfil);
        telefoneInstituicao = findViewById(R.id.edttext_telefone_instituicao_perfil);
        imageInstituicao = findViewById(R.id.image_view_card_photo_institution);

    }


    private void lerDados() {
        InstituicaoController instituicaoController = new InstituicaoController();

        instituicaoController.recuperarInstituicao(getIntent().getStringExtra("idInstitution"), this);

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_back_button_instituion:
                backButton();
                break;
        }
    }

    private void backButton() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}