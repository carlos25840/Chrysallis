package com.example.chrysallis.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chrysallis.EventoActivity;
import com.example.chrysallis.MainActivity;
import com.example.chrysallis.R;
import com.example.chrysallis.adapters.EventoAdapter;
import com.example.chrysallis.classes.Evento;

import java.sql.Date;
import java.util.ArrayList;


public class FragmentHome extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //-----------------------------------PRUEBAS----------------------------------------------//
        ArrayList<Evento> eventos = new ArrayList<>();
        Evento evento1 = new Evento("Colonies al pedraforca", "Colonias de 4 dias por semana santa", R.drawable.ca, new Date(121,07,28), "Barcelona");
        Evento evento2 = new Evento("Prueba", "descripción prueba", R.drawable.en, new Date(120,04,04), "Barcelona");
        Evento evento3 = new Evento("Prueba", "descripción prueba", R.drawable.es, new Date(120,04,04), "Barcelona");
        Evento evento4 = new Evento("Prueba", "descripción prueba", R.drawable.chat, new Date(120,04,04), "Barcelona");
        Evento evento5 = new Evento("Prueba", "descripción prueba", R.drawable.explore, new Date(120,04,04), "Barcelona");

        eventos.add(evento1);
        eventos.add(evento2);
        eventos.add(evento3);
        eventos.add(evento4);
        eventos.add(evento5);
        //Inicialización RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerDestacados);

        EventoAdapter adaptador = new EventoAdapter(eventos);

        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Listener para abrir el seleccionado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventoActivity.class);
                intent.putExtra("evento", eventos.get(recyclerView.getChildAdapterPosition(v)));
                startActivity(intent);
            }
        });


        //------------------------***************************************-----------------------//
        return view;


    }
}
