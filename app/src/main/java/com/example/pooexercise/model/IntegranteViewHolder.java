package com.example.pooexercise.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pooexercise.R;

public class IntegranteViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageIntegrante, deleteIntegrate;
    public TextView integranteName, integranteFunction;
    public CardView cardViewIntegrante;


    public IntegranteViewHolder(View itemView){
        super(itemView);

        imageIntegrante = itemView.findViewById(R.id.image_integrante_card);
        deleteIntegrate = itemView.findViewById(R.id.image_button_excluir_card_integrante);
        integranteName = itemView.findViewById(R.id.text_nome_integrante_card);
        integranteFunction = itemView.findViewById(R.id.text_function_integrante_card);
        cardViewIntegrante = itemView.findViewById(R.id.card_integrante);


    }


}
