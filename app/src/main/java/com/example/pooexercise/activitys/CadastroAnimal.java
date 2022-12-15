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
import com.example.pooexercise.controller.AnimalController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vicmikhailau.maskededittext.MaskedEditText;

public class CadastroAnimal extends AppCompatActivity implements View.OnClickListener {
    private AnimalController animalController = new AnimalController();//Instancia da controller no listenner
    private Button btnFecharCadastroAnimal;
    private FloatingActionButton btnConfirmarCadastroAnimal;
    private ImageView uploadAnimalPhoto;
    private Uri imageUri;
    private EditText edtNomeAnimal,
                     edtRacaAnimal,
                     edtIdadeAnimal,
                     edtPorteAnimal,
                     edtMedicamentoAnimal,
                     edtObservacoesAnimal;
    public ProgressBar progressBarAnimal;
    private MaskedEditText edtHorarioMedicamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal);

        onInitView();

    }

    private void onInitView() {
        btnFecharCadastroAnimal = findViewById(R.id.btn_fechar_cadastro_animal);
        btnConfirmarCadastroAnimal = findViewById(R.id.floating_btn_cadastrar_animal_tela);
        edtNomeAnimal = findViewById(R.id.edttext_animal_name);
        edtRacaAnimal = findViewById(R.id.edttext_raca_animal);
        edtIdadeAnimal = findViewById(R.id.edttext_idade_animal);
        edtPorteAnimal = findViewById(R.id.edttext_porte_animal);
        edtMedicamentoAnimal = findViewById(R.id.edttext_medicamento_animal);
        edtHorarioMedicamento = findViewById(R.id.edttext_horario_medicamento);
        edtObservacoesAnimal = findViewById(R.id.edttext_observacoes_animal);
        uploadAnimalPhoto =  findViewById(R.id.imageView_upload_photo);
        progressBarAnimal = findViewById(R.id.progress_bar_animal);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fechar_cadastro_animal:
                finish();
                break;
            case R.id.floating_btn_cadastrar_animal_tela:
                saveData(testFields());
                break;
            case R.id.imageView_upload_photo:
                abrirGaleria();
                break;
        }
    }


    private void saveData(boolean isSuccess) {
        if (isSuccess){
            String sNomeDoAnimal = edtNomeAnimal.getText().toString();
            String sRacaDoAnimal = edtRacaAnimal.  getText().toString();
            String sIdadeDoAnimal = edtIdadeAnimal.getText().toString();
            String sPorteDoAnimal = edtPorteAnimal.getText().toString();
            String sMedicamentoAnimal = edtMedicamentoAnimal.getText().toString();
            String sHorarioMedicamento = edtHorarioMedicamento.getText().toString();
            String sObservacoesDoAnimal = edtObservacoesAnimal.getText().toString();

            animalController.cadastrarAnimal(sNomeDoAnimal, sRacaDoAnimal, sIdadeDoAnimal, sPorteDoAnimal, sMedicamentoAnimal,
                                             sHorarioMedicamento, sObservacoesDoAnimal, CadastroAnimal.this, imageUri);

            hideKeyboardFrom(this, edtObservacoesAnimal);
            limparCampos();
        } else {
            Toast.makeText(this, "Não foi possível prosseguir, verifique os campos de dados e tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }


    private void limparCampos() {
        edtNomeAnimal.setText("");
        edtRacaAnimal.setText("");
        edtIdadeAnimal.setText("");
        edtPorteAnimal.setText("");
        edtMedicamentoAnimal.setText("");
        edtHorarioMedicamento.setText("");
        edtObservacoesAnimal.setText("");

    }

    public boolean testFields() {

        String sNomeDoAnimal = edtNomeAnimal.getText().toString();
        String sRacaDoAnimal = edtRacaAnimal.getText().toString();
        String sIdadeDoAnimal = edtIdadeAnimal.getText().toString();
        String sPorteDoAnimal = edtPorteAnimal.getText().toString();
        String sMedicamentoAnimal = edtMedicamentoAnimal.getText().toString();
        String sHorarioMedicamento = edtHorarioMedicamento.getText().toString();
        String sObservacoesDoAnimal = edtObservacoesAnimal.getText().toString();


        if (sNomeDoAnimal.isEmpty() || sRacaDoAnimal.isEmpty() || sIdadeDoAnimal.isEmpty() || sPorteDoAnimal.isEmpty() || sMedicamentoAnimal.isEmpty()|| sHorarioMedicamento.isEmpty() || sObservacoesDoAnimal.isEmpty()) {
            return false;
        }
        return true;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(CadastroAnimal.INPUT_METHOD_SERVICE);
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
            uploadAnimalPhoto.setImageURI(imageUri);

        }
    }
}