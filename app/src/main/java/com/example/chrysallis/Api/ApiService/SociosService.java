package com.example.chrysallis.Api.ApiService;

import com.example.chrysallis.classes.Socio;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SociosService {
    @GET("api/Socios")
    Call<ArrayList<Socio>> getSocios();

}
