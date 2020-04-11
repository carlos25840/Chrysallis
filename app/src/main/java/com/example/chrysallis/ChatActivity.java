package com.example.chrysallis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chrysallis.Api.Api;
import com.example.chrysallis.Api.ApiService.EventosService;
import com.example.chrysallis.Api.ApiService.MensajesService;
import com.example.chrysallis.adapters.EventoChatAdapter;
import com.example.chrysallis.adapters.MensajeAdapter;
import com.example.chrysallis.classes.Evento;
import com.example.chrysallis.classes.Mensaje;
import com.example.chrysallis.classes.Socio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private Evento evento;
    private Socio socio;
    private ArrayList<Mensaje> mensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageButton enviar = findViewById(R.id.btnEnviar);
        EditText txtMensaje = findViewById(R.id.txtMensaje);
        Intent intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("evento");
        socio = (Socio) intent.getSerializableExtra("socio");
        setTitle(evento.getNombre());


        cargarMensajes();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String formattedDate = df.format(currentTime);

                Mensaje mensaje = new Mensaje(socio.getId(),evento.getId(), txtMensaje.getText().toString(), formattedDate);
                txtMensaje.setText("");
                MensajesService mensajesService = Api.getApi().create(MensajesService.class);
                Call<Mensaje> mensajeCall = mensajesService.insertMensaje(mensaje);
                mensajeCall.enqueue(new Callback<Mensaje>() {
                    @Override
                    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                        if(response.isSuccessful()){

                            cargarMensajes();
                        }else{
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Mensaje> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getCause() + "-" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    public void cargarMensajes(){
        MensajesService mensajesService = Api.getApi().create(MensajesService.class);
        Call<ArrayList<Mensaje>> mensajesCall = mensajesService.getMensajesByEvent(evento.getId());
        mensajesCall.enqueue(new Callback<ArrayList<Mensaje>>() {
            @Override
            public void onResponse(Call<ArrayList<Mensaje>> call, Response<ArrayList<Mensaje>> response) {
                switch (response.code()){
                    case 200:
                        mensajes = response.body();
                        if(!mensajes.isEmpty()){
                            RecyclerView recyclerViewMensajes = findViewById(R.id.RecyclerMensajes);

                            Collections.sort(mensajes);

                            MensajeAdapter adapter = new MensajeAdapter(mensajes, socio);
                            recyclerViewMensajes.setAdapter(adapter);
                            recyclerViewMensajes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerViewMensajes.scrollToPosition(mensajes.size() - 1);

                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Mensaje>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getCause() + "-" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
