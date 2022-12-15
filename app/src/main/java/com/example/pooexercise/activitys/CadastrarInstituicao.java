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
import com.example.pooexercise.controller.InstituicaoController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CadastrarInstituicao extends AppCompatActivity {

    public Button btnFecharCadastroInstituicao;
    public FloatingActionButton btnConfirmarCadastroInstituicao;
    public EditText edtNomeInstituicao,
            edtEmailInstituicao,
            edtSenhaInstituicao,
            edtConfirmaSenhaInstituicao,
            edtCnpjInstituicao,
            edtTelefoneInstituicao,
            edtEnderecoInstituicao;
    public ProgressBar progressBarInstitution;
    public Uri imageUri;
    public ImageView uploadImageInstitution;
    public Boolean operacao = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_instituicao);

        //Iniciar b
        onInitView();
    }

    private void onInitView() {
        btnFecharCadastroInstituicao = findViewById(R.id.btn_fechar_cadastro_instituicao);
        btnConfirmarCadastroInstituicao = findViewById(R.id.floating_btn_cadastrar_instituicao_tela);
        edtNomeInstituicao = findViewById(R.id.edttext_nome_instituicao);
        edtEmailInstituicao = findViewById(R.id.edttext_email_instituicao);
        edtSenhaInstituicao = findViewById(R.id.edttext_senha_instituicao);
        edtConfirmaSenhaInstituicao = findViewById(R.id.edttext_confirma_senha_instituicao);
        edtCnpjInstituicao = findViewById(R.id.edttext_cnpj_instituicao);
        edtTelefoneInstituicao = findViewById(R.id.edttext_telefone_instituicao);
        edtEnderecoInstituicao = findViewById(R.id.edttext_endereco_instituicao);
        uploadImageInstitution = findViewById(R.id.imageView_upload_institution_photo);
        progressBarInstitution = findViewById(R.id.progress_bar_instituicao);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fechar_cadastro_instituicao:
                backLogin();
                break;
            case R.id.floating_btn_cadastrar_instituicao_tela:
                saveData(testFields());
                break;
            case R.id.imageView_upload_institution_photo:
                abrirGaleria();
                break;
        }
    }

    private void backLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private void saveData(boolean isSuccess) {

        if (isSuccess) {
            String sName = edtNomeInstituicao.getText().toString();
            String sEmail = edtEmailInstituicao.getText().toString();
            String sPassword = edtSenhaInstituicao.getText().toString();
            String sCnpj = edtCnpjInstituicao.getText().toString();
            String sPhone = edtTelefoneInstituicao.getText().toString();
            String sAddress = edtEnderecoInstituicao.getText().toString();

            InstituicaoController instituicaoController = new InstituicaoController();

            instituicaoController.cadastrarInstituicao(sName, sEmail, sPassword, sCnpj, sPhone, sAddress, imageUri, CadastrarInstituicao.this);


        } else {

            Toast.makeText(this, "Não foi possível prosseguir, verifique os campos de dados e tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void limparCampos() {
        edtNomeInstituicao.setText("");
        edtEmailInstituicao.setText("");
        edtSenhaInstituicao.setText("");
        edtConfirmaSenhaInstituicao.setText("");
        edtCnpjInstituicao.setText("");
        edtTelefoneInstituicao.setText("");
        edtEnderecoInstituicao.setText("");
    }

    public boolean testFields() {
        String sNomeInstituicao = edtNomeInstituicao.getText().toString();
        String sEmailInstituicao = edtEmailInstituicao.getText().toString();
        String sSenhaInstituicao = edtSenhaInstituicao.getText().toString();
        String sConfirmarSenhaInstituicao = edtConfirmaSenhaInstituicao.getText().toString();
        String sCnpjInstituicao = edtCnpjInstituicao.getText().toString();
        String sTelefoneInstituicao = edtTelefoneInstituicao.getText().toString();
        String sEnderecoInstituicao = edtEnderecoInstituicao.getText().toString();


        if (sNomeInstituicao.isEmpty() || sEmailInstituicao.isEmpty() || sSenhaInstituicao.isEmpty() || sConfirmarSenhaInstituicao.isEmpty() || sCnpjInstituicao.isEmpty() || sTelefoneInstituicao.isEmpty() || sEnderecoInstituicao.isEmpty() || imageUri == null) {
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
            uploadImageInstitution.setImageURI(imageUri);

        }
    }

}