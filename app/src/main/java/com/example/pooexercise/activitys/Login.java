package com.example.pooexercise.activitys;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pooexercise.R;
import com.example.pooexercise.controller.LoginController;
import com.example.pooexercise.model.LoginModel;
public class Login extends AppCompatActivity {

    private EditText edtLogin, edtSenha;
    private TextView txtTermos;
    private Button btnEnter, btnRegister;
    Boolean operacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        onInitView();

    }

    private void onInitView() {
        edtLogin = findViewById(R.id.edittext_user_email);
        edtSenha = findViewById(R.id.edittext_user_password);
        btnEnter =  findViewById(R.id.btn_enter);
        btnRegister = findViewById(R.id.btn_register_institution);
        txtTermos = findViewById(R.id.termos_contrato_uso);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enter:
                autenticacao(testFields());
                break;
            case R.id.btn_register_institution:
                registerInstitution();
                break;
            case R.id.termos_contrato_uso:
                abrirTermos();
                break;
        }
    }

    private void abrirTermos() {
        Intent intent = new Intent(Login.this, TermosUso.class);
        startActivity(intent);
    }

    private void registerInstitution() {
        Intent intent = new Intent(Login.this, CadastrarInstituicao.class);
        startActivity(intent);
        finish();
    }

    public void autenticacao(Boolean testeFields){

        if (testeFields) {
            String sEmail    = edtLogin.getText().toString();
            String sPassword = edtSenha.getText().toString();

            LoginModel loginModel = new LoginModel(sEmail, sPassword);

            LoginController loginController = new LoginController();

            loginController.singInUser(loginModel, this);
        } else {

            Toast.makeText(this, "Não foi possível prosseguir, verifique os campos de dados e tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean testFields() {
        String sEmail    = edtLogin.getText().toString();
        String sPassword = edtSenha.getText().toString();


        if (sEmail.isEmpty() || sPassword.isEmpty()) {
            return false;
        }

        return true;
    }
}