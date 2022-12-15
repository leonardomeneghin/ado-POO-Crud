package com.example.pooexercise.controller;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pooexercise.R;
import com.example.pooexercise.activitys.PerfilAnimal;
import com.example.pooexercise.model.modelRecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdapterCardDog extends FirebaseRecyclerAdapter<modelRecyclerView, AdapterCardDog.myviewhodler> {

    Context context;

    public AdapterCardDog(@NonNull FirebaseRecyclerOptions<modelRecyclerView> options, Context context) {
        super(options);

        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewhodler holder, int position, @NonNull modelRecyclerView model) {
        holder.sDogName.setText("Nome : " + model.getAnimalName());
        holder.sDogAge.setText(model.getAnimalAge() + " anos");
        Glide.with(holder.imageDog.getContext()).load(model.getUrlImageDog()).into(holder.imageDog);

        holder.cardViewDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PerfilAnimal.class);
                intent.putExtra("idAnimal", model.getIdAnimal());
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.deleteDog.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.deleteDog.getContext());
            builder.setTitle("Deletar Cachorro");
            builder.setMessage("Você tem certeza que deseja deletar o animal?");

            builder.setPositiveButton("SIM", (dialogInterface, i) -> {

                SharedPreferences sharedPreferences = context.getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
                String idInstitution = sharedPreferences.getString("institutionId", "");

                DatabaseReference mbase = FirebaseDatabase.getInstance().getReference("usuario").child(idInstitution).child("animal").child(model.getIdAnimal());

                mbase.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                StorageReference storageReference = FirebaseStorage.getInstance().getReference("foto");
                                StorageReference referenceImage = storageReference.child("animal/" + model.getIdAnimal());
                                referenceImage.delete();

                                notifyDataSetChanged();
                                Toast.makeText(context, "Animal foi deletado com suceso!", Toast.LENGTH_SHORT).show();
                            }
                        });

            }).setNegativeButton("NÃO", (dialogInterface, i) -> {

            });

            builder.show();


        });

    }


    @NonNull
    @Override
    public myviewhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cachorro, parent, false);
        return new myviewhodler(view);
    }

    public static class myviewhodler extends RecyclerView.ViewHolder {

        ImageView imageDog, deleteDog;
        TextView sDogName, sDogAge;
        CardView cardViewDog;


        public myviewhodler(@NonNull View itemView) {
            super(itemView);

            cardViewDog = itemView.findViewById(R.id.card_view_dog);
            imageDog = itemView.findViewById(R.id.card_image_dog_photo);
            sDogName = itemView.findViewById(R.id.card_text_nome_do_cachorro_card);
            sDogAge = itemView.findViewById(R.id.card_text_idade_do_cachorro_card);
            deleteDog = itemView.findViewById(R.id.card_image_button_excluir_card_cachorro);

        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }
}
