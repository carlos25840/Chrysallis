package com.example.chrysallis.Api.ApiService;

import com.example.chrysallis.classes.Asistir;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AsistirService {
    @POST("api/Asistir")
    Call<Asistir> insertAsistir(@Body Asistir asistir);
    @DELETE("api/Asistir/{id_socio}/{id_evento}")
    Call<Asistir> deleteAsistir(@Path("id_socio") int id_socio, @Path("id_evento") int id_evento);
    @PUT("api/Asistir/{id_socio}/{id_evento}")
    Call<Asistir> putAsistir(@Path("id_socio") int id_socio, @Path("id_evento") int id_evento,@Body Asistir asistir);
}
