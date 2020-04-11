package com.example.chrysallis.Api.ApiService;

import com.example.chrysallis.classes.Asistir;
import com.example.chrysallis.classes.Evento;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AsistirService {
    @POST("api/Asistir")
    Call<Asistir> insertAsistir(@Body Asistir asistir);

    @GET ("api/Asistir/searchid")
    Call<Asistir> getEventosApuntado(@Body int id);
}
