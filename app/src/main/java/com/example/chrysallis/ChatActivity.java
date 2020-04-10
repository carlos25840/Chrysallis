package com.example.chrysallis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.chrysallis.adapters.MensajeAdapter;
import com.example.chrysallis.classes.Evento;
import com.example.chrysallis.classes.Mensaje;
import com.example.chrysallis.classes.Socio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChatActivity extends AppCompatActivity {
    private Evento evento;
    private Socio socio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("evento");
        socio = (Socio) intent.getSerializableExtra("socio");
        setTitle(evento.getNombre());
        ArrayList<Mensaje> listMensajes = new ArrayList<>(evento.getMensajes());

        RecyclerView recyclerViewMensajes = findViewById(R.id.RecyclerMensajes);

        Collections.sort(listMensajes);

        MensajeAdapter adapter = new MensajeAdapter(listMensajes, socio);
        recyclerViewMensajes.setAdapter(adapter);
        recyclerViewMensajes.setLayoutManager(new LinearLayoutManager(this));
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
}
