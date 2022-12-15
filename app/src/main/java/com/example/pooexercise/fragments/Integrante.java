package com.example.pooexercise.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pooexercise.R;
import com.example.pooexercise.activitys.CadastroIntegrante;
import com.example.pooexercise.activitys.MainActivity;
import com.example.pooexercise.controller.AdapterCardIntegrante;
import com.example.pooexercise.model.modelRecyclerViewIntegrante;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Integrante extends Fragment {

    Context context;
    AdapterCardIntegrante adapterCardIntegrante;
    private IntegranteViewModel mViewModel;
    private FloatingActionButton floatingBtnAdicionarIntegrante;
    private Button btnLogout;
    DatabaseReference mBase;

    public static Integrante newInstance() {
        return new Integrante();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.integrante_fragment, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        String idInstitutionCurrentUser = pref.getString("institutionId", "");
        String currentUser = pref.getString("currentUserUid", "");
        String roleUser = pref.getString("userRole", "");

        testUser();

        context = getContext();

        mBase = FirebaseDatabase.getInstance().getReference("usuario").child("integrante");

        floatingBtnAdicionarIntegrante = view.findViewById(R.id.floating_btn_add_integrantes_fragment);
        RecyclerView recyclerviewIntegrante = view.findViewById(R.id.recyclerviewIntegrante);

        recyclerviewIntegrante.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<modelRecyclerViewIntegrante> options =
                new FirebaseRecyclerOptions.Builder<modelRecyclerViewIntegrante>()
                        .setQuery(mBase, modelRecyclerViewIntegrante.class)
                        .build();


        adapterCardIntegrante = new AdapterCardIntegrante(options, context);
        recyclerviewIntegrante.setAdapter(adapterCardIntegrante);
        recyclerviewIntegrante.setItemAnimator(null);


        floatingBtnAdicionarIntegrante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CadastroIntegrante.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void testUser() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("userRole", "");

        if(userRole.equals("normal")){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(getContext(), "Você não tem acesso ao registro de integrantes!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(IntegranteViewModel.class);
        // TODO: Use the ViewModel
    }

    public void loggoutUser(){
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedPref = getContext().getSharedPreferences("idInstitutionCurrentUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }


    @Override
    public void onStart() {
        super.onStart();
        adapterCardIntegrante.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterCardIntegrante.stopListening();
    }
}