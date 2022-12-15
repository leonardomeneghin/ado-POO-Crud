package com.example.pooexercise.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pooexercise.R;
import com.example.pooexercise.controller.IntegranteController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CadastroIntegrante extends AppCompatActivity {

    private Button btnFecharCadastroIntegrante;
    private FloatingActionButton btnConfirmarCadastroIntegrante;
    private EditText edtNomeIntegrante,
            edtEmailIntegrante,
            edtSenhaIntegrante,
            edtConfirmaSenhaIntegrante,
            edtFuncaoIntegrante,
            edtCpfIntegrante,
            edtRgIntegrante,
            edtTelefoneIntegrante;
    public ImageView uploadImageIntegrante;
    public ProgressBar progressBarIntegrate;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_integrante);


        onInitView();
    }

    private void onInitView() {
        btnFecharCadastroIntegrante = findViewById(R.id.btn_fechar_cadastro_integrante);
        btnConfirmarCadastroIntegrante = findViewById(R.id.floating_btn_cadastrar_integrante_tela);
        edtNomeIntegrante = findViewById(R.id.edttext_nome_integrante);
        edtEmailIntegrante = findViewById(R.id.edttext_email_integrante);
        edtSenhaIntegrante = findViewById(R.id.edttext_senha_integrante);
        edtConfirmaSenhaIntegrante = findViewById(R.id.edttext_confirma_senha_integrante);
        edtFuncaoIntegrante = findViewById(R.id.edttext_funcao_integrante);
        edtCpfIntegrante = findViewById(R.id.edttext_cpf_integrante);
        edtRgIntegrante = findViewById(R.id.edttext_rg_integrante);
        edtTelefoneIntegrante = findViewById(R.id.edttext_telefone_integrante);
        progressBarIntegrate = findViewById(R.id.progress_bar_integrante);
        uploadImageIntegrante = findViewById(R.id.imageView_upload_integrante_photo);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fechar_cadastro_integrante:
                finish();
                break;
            case R.id.floating_btn_cadastrar_integrante_tela:
                saveData(testFields());
                break;
            case R.id.imageView_upload_integrante_photo:
                abrirGaleria();
                break;
        }
    }

    private void saveData(boolean isSuccess) {
        if (isSuccess) {
            String sName = edtNomeIntegrante.getText().toString();
            String sPassword = edtSenhaIntegrante.getText().toString();
            String sEmail = edtEmailIntegrante.getText().toString();
            String sFuncao = edtFuncaoIntegrante.getText().toString();
            String sCpf = edtCpfIntegrante.getText().toString();
            String sRg = edtRgIntegrante.getText().toString();
            String sPhone = edtTelefoneIntegrante.getText().toString();

            IntegranteController integranteController = new IntegranteController();

            integranteController.cadastrarIntegrante(sName, sPassword, sEmail, sFuncao, sCpf, sRg, sPhone, imageUri, CadastroIntegrante.this);

        } else {
            Toast.makeText(this, "Não foi possível prosseguir, verifique os campos de dados e tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void limparCampos() {
        edtNomeIntegrante.setText("");
        edtEmailIntegrante.setText("");
        edtSenhaIntegrante.setText("");
        edtConfirmaSenhaIntegrante.setText("");
        edtFuncaoIntegrante.setText("");
        edtCpfIntegrante.setText("");
        edtRgIntegrante.setText("");
        edtTelefoneIntegrante.setText("");
    }

    public boolean testFields() {
        String sNomeIntegrante = edtNomeIntegrante.getText().toString();
        String sEmailIntegrante = edtEmailIntegrante.getText().toString();
        String sSenhaIntegrante = edtSenhaIntegrante.getText().toString();
        String sConfirmaSenhaIntegrante = edtConfirmaSenhaIntegrante.getText().toString();
        String sFuncaoIntegrante = edtFuncaoIntegrante.getText().toString();
        String sCpfIntegrante = edtCpfIntegrante.getText().toString();
        String sRgIntegrante = edtRgIntegrante.getText().toString();
        String sTelefoneIntegrante = edtTelefoneIntegrante.getText().toString();

        if (sNomeIntegrante.isEmpty() || sEmailIntegrante.isEmpty() || sSenhaIntegrante.isEmpty() || sConfirmaSenhaIntegrante.isEmpty() || sFuncaoIntegrante.isEmpty()|| sCpfIntegrante.isEmpty() || sRgIntegrante.isEmpty() || sTelefoneIntegrante.isEmpty() || imageUri == null) {
            return false;
        }

        return true;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void abrirGaleria(){
        Intent intentGallery = new Intent();
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        intentGallery.setType("image/*");
        startActivityForResult(intentGallery, 2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data !=null){

            imageUri = data.getData();
            uploadImageIntegrante.setImageURI(imageUri);

        }
    }
}