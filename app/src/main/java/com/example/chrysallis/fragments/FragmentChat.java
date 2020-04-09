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
import android.widget.Toast;

import com.example.chrysallis.Api.Api;
import com.example.chrysallis.Api.ApiService.EventosService;
import com.example.chrysallis.ChatActivity;
import com.example.chrysallis.R;
import com.example.chrysallis.adapters.EventoChatAdapter;
import com.example.chrysallis.classes.Evento;
import com.example.chrysallis.classes.Socio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentChat extends Fragment {

    private ArrayList<Evento> eventos;
    private RecyclerView recyclerView;
    private Socio socio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        eventos = new ArrayList<>();
        recyclerView = view.findViewById(R.id.RecyclerChats);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(currentTime);
        EventosService eventosService = Api.getApi().create(EventosService.class);
        Call<ArrayList<Evento>> eventosCall = eventosService.busquedaEventosComunidad(socio.getId_comunidad(),formattedDate);
        eventosCall.enqueue(new Callback<ArrayList<Evento>>() {
            @Override
            public void onResponse(Call<ArrayList<Evento>> call, Response<ArrayList<Evento>> response) {
                switch (response.code()){
                    case 200:
                        eventos = response.body();
                        if(!eventos.isEmpty()){
                            EventoChatAdapter adaptador = new EventoChatAdapter(eventos);
                            recyclerView.setAdapter(adaptador);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            //Listener para abrir el seleccionado
                            adaptador.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                                    intent.putExtra("evento", eventos.get(recyclerView.getChildAdapterPosition(v)));
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

        return view;

    }

    public FragmentChat(Socio socio){
        this.socio= socio;
    }
}
