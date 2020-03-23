package com.example.chrysallis.Api.ApiService;

import com.example.chrysallis.classes.Asistir;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AsistirService {
    @POST("api/Asistir")
    Call<Asistir> insertAsistir(@Body Asistir asistir);
}
