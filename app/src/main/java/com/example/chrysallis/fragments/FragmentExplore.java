package com.example.chrysallis.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chrysallis.Api.Api;
import com.example.chrysallis.Api.ApiService.ComunidadesService;
import com.example.chrysallis.R;
import com.example.chrysallis.adapters.ComunidadesSpinnerAdapter;
import com.example.chrysallis.classes.Comunidad;
import com.example.chrysallis.components.DatePickerFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentExplore extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View item = inflater.inflate(R.layout.fragment_explore, container, false);
        TextInputEditText txtDate = item.findViewById(R.id.editTextFecha);
        ComunidadesService comunidadesService = Api.getApi().create(ComunidadesService.class);
        Call<ArrayList<Comunidad>> comunidadesCall = comunidadesService.getComunidades();
        comunidadesCall.enqueue(new Callback<ArrayList<Comunidad>>() {
            @Override
            public void onResponse(Call<ArrayList<Comunidad>> call, Response<ArrayList<Comunidad>> response) {
                switch (response.code()){
                    case 200:
                        ArrayList<Comunidad> comunidades = response.body();
                        ComunidadesSpinnerAdapter spinnerAdapter = new ComunidadesSpinnerAdapter(getActivity(),comunidades);
                        Spinner spnComunidades = item.findViewById(R.id.spnComunity);
                        spnComunidades.setAdapter(spinnerAdapter);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Comunidad>> call, Throwable t) {

            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText txtDate = item.findViewById(R.id.editTextFecha);
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because January is zero
                        final String selectedDate = day + "/" + (month+1) + "/" + year;
                        txtDate.setText(selectedDate);
                    }
                });

                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });
        return item;
    }

    private void showDatePickerDialog() {

    }

}
