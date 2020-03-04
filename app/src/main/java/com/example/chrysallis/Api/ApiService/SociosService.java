package com.example.chrysallis.Api.ApiService;

import com.example.chrysallis.classes.Socio;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SociosService {
    @GET("api/Socios")
    Call<ArrayList<Socio>> getSocios();

    @GET("api/Socios/{telefono}/{password}")
    Call<Socio> SocioLogin(@Path("telefono")String telefono,@Path("password") String password);
}
