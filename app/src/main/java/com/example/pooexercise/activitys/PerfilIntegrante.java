package com.example.pooexercise.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pooexercise.R;
import com.example.pooexercise.controller.IntegranteController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vicmikhailau.maskededittext.MaskedEditText;

public class PerfilIntegrante extends AppCompatActivity {

    public EditText nameIntegrante, functionIntegrante, rgIntegrante, emailIntegrante, passwordIntegrante, phoneIntegrante;
    public ImageView backButton;
    public FloatingActionButton floatingEditMode, floatingBackNormal, floatingSaveEdit;
    public MaskedEditText cpfIntegrante;
    public ImageView imageIntegrante;
    public Uri imageUri;
    public ProgressBar progressBarIntegrante;
    IntegranteController integranteController = new IntegranteController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_integrante);

        onInit();

        lerDados();
    }
    private void onInit() {
        backButton = findViewById(R.id.card_back_button_integrante);
        floatingEditMode = findViewById(R.id.floating_edt_perfil_integrante_card);
        floatingBackNormal = findViewById(R.id.floating_back_edit_integrante_card);
        floatingSaveEdit = findViewById(R.id.floating_done_edit_integrante_card);
        nameIntegrante = findViewById(R.id.edittext_name_integrante_card_perfil);
        functionIntegrante = findViewById(R.id.edttext_funcao_integrante_card_perfil);
        rgIntegrante = findViewById(R.id.edttext_rg_integrante_card_perfil);
        emailIntegrante = findViewById(R.id.edttext_email_integrante_card_perfil);
        passwordIntegrante = findViewById(R.id.edttext_password_integrante_card_perfil);
        cpfIntegrante = findViewById(R.id.edttext_cpf_integrante_card);
        progressBarIntegrante = findViewById(R.id.progress_bar_perfil_integrante_card);
        imageIntegrante = findViewById(R.id.image_view_card_photo_integrante);
        phoneIntegrante = findViewById(R.id.edttext_phone_integrante_card);
    }

    private void lerDados() {
        IntegranteController integranteController = new IntegranteController();

        integranteController.recuperarIntegrante(getIntent().getStringExtra("idIntegrante"), PerfilIntegrante.this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_back_button_integrante:
                backHome();
                break;
            case R.id.floating_edt_perfil_integrante_card:
                editMode();
                break;
            case R.id.floating_back_edit_integrante_card:
                backNormalMode();
                break;
            case R.id.floating_done_edit_integrante_card:
                backNormalMode();
                saveEdit(testFields());
                break;
            case R.id.image_view_card_photo_integrante:
                abrirGaleria();
                break;
        }
    }

    private void backHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void backNormalMode() {
        nameIntegrante.setEnabled(false);
        functionIntegrante.setEnabled(false);
        rgIntegrante.setEnabled(false);
        emailIntegrante.setEnabled(false);
        passwordIntegrante.setEnabled(false);
        cpfIntegrante.setEnabled(false);
        phoneIntegrante.setEnabled(false);

        floatingEditMode.setVisibility(View.VISIBLE);
        floatingSaveEdit.setClickable(false);
        floatingSaveEdit.setVisibility(View.INVISIBLE);
        floatingBackNormal.setClickable(true);
        floatingBackNormal.setVisibility(View.INVISIBLE);
    }

    private void editMode() {

        nameIntegrante.setEnabled(true);
        functionIntegrante.setEnabled(true);
        rgIntegrante.setEnabled(true);
        emailIntegrante.setEnabled(true);
        passwordIntegrante.setEnabled(true);
        cpfIntegrante.setEnabled(true);
        phoneIntegrante.setEnabled(true);


        floatingEditMode.setVisibility(View.INVISIBLE);
        floatingSaveEdit.setClickable(true);
        floatingSaveEdit.setVisibility(View.VISIBLE);
        floatingBackNormal.setClickable(true);
        floatingBackNormal.setVisibility(View.VISIBLE);
    }

    private void saveEdit(Boolean isSuccess) {
        if (isSuccess) {
            String snameIntegrante = nameIntegrante.getText().toString();
            String sFunctionIntegrante = functionIntegrante.getText().toString();
            String sRgIntegrante = rgIntegrante.getText().toString();
            String sEmailIntegrante = emailIntegrante.getText().toString();
            String sPasswordIntegrante = passwordIntegrante.getText().toString();
            String sCpfIntegrante = cpfIntegrante.getText().toString();
            String sPhoneNumber = phoneIntegrante.getText().toString();


            //Passando os dados para a controler persistir no banco de dados
            integranteController.editarIntegrante(snameIntegrante, sPasswordIntegrante, sEmailIntegrante, sFunctionIntegrante, sCpfIntegrante, sRgIntegrante, sPhoneNumber, PerfilIntegrante.this);

        } else {

            Toast.makeText(this, "Não foi possível prosseguir, verifique os campos de dados e tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean testFields() {

        String snameIntegrante = nameIntegrante.getText().toString();
        String sFunctionIntegrante = functionIntegrante.getText().toString();
        String sRgIntegrante = rgIntegrante.getText().toString();
        String sEmailIntegrante = emailIntegrante.getText().toString();
        String sSasswordIntegrante = passwordIntegrante.getText().toString();
        String sCpfIntegrante = cpfIntegrante.getText().toString();
        String sPhoneIntegrante = phoneIntegrante.getText().toString();


        if (snameIntegrante.isEmpty() || sSasswordIntegrante.isEmpty() || sFunctionIntegrante.isEmpty() || sRgIntegrante.isEmpty() || sEmailIntegrante.isEmpty() || sPhoneIntegrante.isEmpty() || sCpfIntegrante.isEmpty()) {
            return false;
        }
        return true;
    }

    public void abrirGaleria() {
        Intent intentGallery = new Intent();
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        intentGallery.setType("image/*");
        startActivityForResult(intentGallery, 2);

    }

    public void editarFoto() {
       integranteController.editarFotoIntegrante(this, getIntent().getStringExtra("idIntegrante"),imageUri);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();
            imageIntegrante.setImageURI(imageUri);

            AlertDialog.Builder builder = new AlertDialog.Builder(PerfilIntegrante.this.imageIntegrante.getContext());
            builder.setTitle("Atualizar imagem");
            builder.setMessage("Você tem certeza que deseja atualizar sua imagem?");

            builder.setPositiveButton("SIM", (dialogInterface, i) -> {
                editarFoto();
            });
            builder.setNegativeButton("NÃO", (dialogInterface, i) -> {

            });
            builder.show();

        }
    }
}