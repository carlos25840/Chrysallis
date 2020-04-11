package com.example.chrysallis;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chrysallis.Api.Api;
import com.example.chrysallis.Api.ApiService.AsistirService;
import com.example.chrysallis.Api.ApiService.ComunidadesService;
import com.example.chrysallis.Api.ApiService.EventosService;
import com.example.chrysallis.adapters.EventoAdapter;
import com.example.chrysallis.classes.Asistir;
import com.example.chrysallis.classes.Comunidad;
import com.example.chrysallis.classes.Evento;
import com.example.chrysallis.classes.Socio;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApuntadoActivity extends AppCompatActivity {

    private ArrayList<Evento> eventos;
    private RecyclerView recyclerView;
    private EventoAdapter adaptador;




    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_apuntado);


        Intent intent = getIntent();
        Socio socio = (Socio)intent.getSerializableExtra("socio");
        int id = socio.getId();
        recyclerView = findViewById(R.id.recyclerEventosApuntado);



        EventosService eventosService = Api.getApi().create(EventosService.class);
        Call<ArrayList<Evento>> eventosCall;

        eventosCall = eventosService.getEventosApuntado(id);
        eventosCall.enqueue(new Callback<ArrayList<Evento>>() {
            @Override
            public void onResponse(Call<ArrayList<Evento>> call, Response<ArrayList<Evento>> response) {
                switch (response.code()) {
                    case 200:
                        eventos = response.body();

                        rellenarRecyclerView();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Evento>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getCause() + "-" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        rellenarRecyclerView();




    }

    public void rellenarRecyclerView(){
        if (!eventos.isEmpty()) {

            adaptador = new EventoAdapter(eventos);
            recyclerView.setAdapter(adaptador);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            //Listener para abrir el seleccionado
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ApuntadoActivity.this, EventoActivity.class);
                    intent.putExtra("evento", eventos.get(recyclerView.getChildAdapterPosition(v)));
                    startActivity(intent);
                }
            });
        } else {

            recyclerView.removeAllViewsInLayout();
        }
    }


}

