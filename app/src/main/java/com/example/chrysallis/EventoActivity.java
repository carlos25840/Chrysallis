package com.example.chrysallis;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.chrysallis.classes.Evento;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class EventoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Evento evento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TextView txtEvent = findViewById(R.id.txtEvent);
        TextView txtCom = findViewById(R.id.txtComunEvent);
        TextView txtDate = findViewById(R.id.txtDateEvent);
        TextView txtTime = findViewById(R.id.txtTimeEvent);
        TextView txtDescription = findViewById(R.id.txtDesEvent);
        TextView txtLocation = findViewById(R.id.txtLocationEvent);
        Button button = findViewById(R.id.buttonJoin);
        Intent intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("evento");
        txtEvent.setText(evento.getNombre());
        txtCom.setText(evento.getComunidades().getNombre());
        String date = evento.getFecha().substring(0,10);
        txtDate.setText(date);
        String time = evento.getHora().substring(0,8);
        txtTime.setText(time);
        txtDescription.setText(evento.getDescripcion());
        txtLocation.setText(evento.getUbicacion());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String address = evento.getUbicacion();
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses != null) {
            double latitude= addresses.get(0).getLatitude();
            double longitude= addresses.get(0).getLongitude();
            // Add a marker in Sydney and move the camera
            LatLng place = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(place).title(getString(R.string.event)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        }else{
            LatLng place = new LatLng(41.384724, 2.171768);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        }
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
