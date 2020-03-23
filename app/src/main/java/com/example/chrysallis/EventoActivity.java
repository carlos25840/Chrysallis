package com.example.chrysallis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chrysallis.Api.Api;
import com.example.chrysallis.Api.ApiService.AsistirService;
import com.example.chrysallis.classes.Asistir;
import com.example.chrysallis.classes.Evento;
import com.example.chrysallis.classes.Socio;
import com.example.chrysallis.components.GeocodingLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Evento evento;
    private Socio socio;
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
        socio = (Socio)intent.getSerializableExtra("socio");
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
                showDialogAttendance();
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
        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(address,
                getApplicationContext(), new GeocoderHandler());
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

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            if(locationAddress != null){
                String[] result = locationAddress.split(";");
                double latitude= Double.parseDouble(result[0]);
                double longitude= Double.parseDouble(result[1]);
                // Add a marker in the event and move the camera
                LatLng place = new LatLng(latitude,longitude);
                mMap.addMarker(new MarkerOptions().position(place).title(getString(R.string.event)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            }else{
                LatLng place = new LatLng(41.384724, 2.171768);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            }
        }
    }

    private void showDialogAttendance() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_asistir, null);


        builder.setTitle(Html.fromHtml("<b>"+getString(R.string.attendanceConfirmation)+"</b>"));
        builder.setView(v);

        final EditText editTextPassword = v.findViewById(R.id.editTextNumAttendants);

        builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!editTextPassword.getText().toString().equals("")){
                    int numAsist = Integer.parseInt(editTextPassword.getText().toString());
                    Asistir asistir = new Asistir(socio.getId(),evento.getId(),numAsist);
                    AsistirService asistirService = Api.getApi().create(AsistirService.class);
                    Call<Asistir> asistirCall = asistirService.insertAsistir(asistir);
                    asistirCall.enqueue(new Callback<Asistir>() {
                        @Override
                        public void onResponse(Call<Asistir> call, Response<Asistir> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getApplicationContext(),getString(R.string.attendanceCorfirmed), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"MAAAL", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Asistir> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.getCause() + "-" + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.notNumber), Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.show();
    }
}
