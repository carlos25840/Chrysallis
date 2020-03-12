package com.example.chrysallis.Api.ApiService;

import com.example.chrysallis.classes.Comunidad;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ComunidadesService {
    @GET("api/Comunidades")
    Call<ArrayList<Comunidad>> getComunidades();
}
