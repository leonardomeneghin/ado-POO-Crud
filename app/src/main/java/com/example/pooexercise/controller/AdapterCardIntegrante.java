package com.example.pooexercise.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.pooexercise.R;
import com.example.pooexercise.activitys.PerfilIntegrante;
import com.example.pooexercise.model.IntegranteViewHolder;
import com.example.pooexercise.model.modelRecyclerViewIntegrante;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdapterCardIntegrante extends FirebaseRecyclerAdapter<modelRecyclerViewIntegrante, IntegranteViewHolder> {

    Context context;


    public AdapterCardIntegrante(FirebaseRecyclerOptions<modelRecyclerViewIntegrante> options, Context context) {
        super(options);

        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull IntegranteViewHolder holder, int position, @NonNull modelRecyclerViewIntegrante model) {

        holder.integranteName.setText("Nome : " + model.getNameIntegrante());
        holder.integranteFunction.setText("Função : " + model.getFunctionIntegrante());
        Glide.with(holder.imageIntegrante.getContext()).load(model.getUrlImageIntegrante()).into(holder.imageIntegrante);

        holder.cardViewIntegrante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PerfilIntegrante.class);
                intent.putExtra("idIntegrante", model.getIdIntegrante());
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.deleteIntegrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.deleteIntegrate.getContext());
                builder.setTitle("Deletar Integrante");
                builder.setMessage("Você tem certeza que deseja deletar o integrante?");

                builder.setPositiveButton("SIM", ((dialogInterface, i) -> {

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    DatabaseReference mbase = FirebaseDatabase.getInstance().getReference("usuario").child("integrante").child(model.getIdIntegrante());


                    mbase.removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("foto");
                                    StorageReference referenceImage = storageReference.child("usuario/" + model.getIdIntegrante());
                                    referenceImage.delete();

                                    notifyDataSetChanged();
                                    Toast.makeText(context, "O integrante foi deletado com sucesso!!", Toast.LENGTH_SHORT).show();
                                }
                            });

                })).setNegativeButton("NÃO", ((dialogInterface, i) -> {

                }));

                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public IntegranteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_integrante, parent, false);

        return new IntegranteViewHolder(view);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }

}
