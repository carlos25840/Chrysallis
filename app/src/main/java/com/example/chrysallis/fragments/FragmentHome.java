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
import android.widget.TextView;
import android.widget.Toast;

import com.example.chrysallis.Api.Api;
import com.example.chrysallis.Api.ApiService.EventosService;
import com.example.chrysallis.EventoActivity;
import com.example.chrysallis.R;
import com.example.chrysallis.adapters.EventoAdapter;
import com.example.chrysallis.classes.Evento;
import com.example.chrysallis.classes.Socio;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHome extends Fragment {
    private ArrayList<Evento> eventos;
    private RecyclerView recyclerView;
    private Socio socio;
    private TextView msgNotEvents;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //-----------------------------------PRUEBAS----------------------------------------------//
        eventos = new ArrayList<>();
       /* Evento evento1 = new Evento("Colonies al pedraforca", "Colonias de 4 dias por semana santa", R.drawable.ca, new Date(121,07,28), "Barcelona");
        Evento evento2 = new Evento("Prueba", "descripción prueba", R.drawable.en, new Date(120,04,04), "Barcelona");
        Evento evento3 = new Evento("Prueba", "descripción prueba", R.drawable.es, new Date(120,04,04), "Barcelona");
        Evento evento4 = new Evento("Prueba", "descripción prueba", R.drawable.chat, new Date(120,04,04), "Barcelona");
        Evento evento5 = new Evento("Prueba", "descripción prueba", R.drawable.explore, new Date(120,04,04), "Barcelona");

        eventos.add(evento1);
        eventos.add(evento2);
        eventos.add(evento3);
        eventos.add(evento4);
        eventos.add(evento5);*/
        //Inicialización RecyclerView
        recyclerView = view.findViewById(R.id.RecyclerDestacados);
        msgNotEvents = view.findViewById(R.id.msgNotEvents);

        EventosService eventosService = Api.getApi().create(EventosService.class);
        Call<ArrayList<Evento>> eventosCall = eventosService.busquedaEventosComunidad(socio.getId_comunidad());
        eventosCall.enqueue(new Callback<ArrayList<Evento>>() {
            @Override
            public void onResponse(Call<ArrayList<Evento>> call, Response<ArrayList<Evento>> response) {
                switch (response.code()){
                    case 200:
                        eventos = response.body();
                        if(!eventos.isEmpty()){
                            msgNotEvents.setVisibility(View.GONE);
                            EventoAdapter adaptador = new EventoAdapter(eventos);
                            recyclerView.setAdapter(adaptador);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            //Listener para abrir el seleccionado
                            adaptador.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), EventoActivity.class);
                                    intent.putExtra("evento", eventos.get(recyclerView.getChildAdapterPosition(v)));
                                    intent.putExtra("socio", socio);
                                    startActivity(intent);
                                }
                            });
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Evento>> call, Throwable t) {
                Toast.makeText(getContext(),t.getCause() + "-" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });







        //------------------------***************************************-----------------------//
        return view;


    }

    public FragmentHome(Socio socio){
        this.socio= socio;
    }
}
