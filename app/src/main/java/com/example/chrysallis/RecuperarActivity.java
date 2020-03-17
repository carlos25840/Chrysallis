package com.example.chrysallis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chrysallis.Api.Api;
import com.example.chrysallis.Api.ApiService.SociosService;
import com.example.chrysallis.classes.Socio;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        EditText editTextPhone = findViewById(R.id.editTextPhoneRecuperar);
        EditText editTextCorreo = findViewById(R.id.editTextEmail);

        Button butonRecuperar = findViewById(R.id.buttonRecuperar);

        butonRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SociosService sociosService = Api.getApi().create(SociosService.class);
                Call<Socio> callSocioLogin = sociosService.SocioRecuperar(editTextPhone.getText().toString(), editTextCorreo.getText().toString());
                callSocioLogin.enqueue(new Callback<Socio>() {
                    @Override
                    public void onResponse(Call<Socio> call, Response<Socio> response) {
                        switch (response.code()){
                            case 200:
                                Socio socio = response.body();
                                if(socio != null){
                                    enviarMail();
                                    Toast.makeText(getApplicationContext(),"Su contraseña ha sido enviada", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),R.string.loginIncorrect, Toast.LENGTH_LONG).show();
                                }
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Socio> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getCause() + "-" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    public void enviarMail(){
        new SendMailTask(RecuperarActivity.this).execute("carlos25840@gmail.com",
                "jcleon20055", "mariajosebg95@gmail.com ", "prueba", "Tu nueva clave es: 052hdst");
    }
}
