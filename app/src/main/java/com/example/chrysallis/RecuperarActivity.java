package com.example.chrysallis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chrysallis.Api.Api;
import com.example.chrysallis.Api.ApiService.SociosService;
import com.example.chrysallis.classes.Socio;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarActivity extends AppCompatActivity {
    private Socio socio;

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
                Call<Socio> callSocioLogin = sociosService.SocioRecuperar(editTextCorreo.getText().toString(), editTextPhone.getText().toString());
                callSocioLogin.enqueue(new Callback<Socio>() {
                    @Override
                    public void onResponse(Call<Socio> call, Response<Socio> response) {
                        switch (response.code()){
                            case 200:
                                socio = response.body();
                                if(socio != null){
                                    enviarMail();
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
        EditText editTextCorreo = findViewById(R.id.editTextEmail);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Mail m=new Mail("eventschrysallis@gmail.com","chrysallis2005");

        String[] toArr = {editTextCorreo.getText().toString().trim(), "carlos25840@gmail.com"};
        m.setTo(toArr);
        m.setFrom("Chrysallis");
        m.setSubject("Chrysallis password");


        String password = getPassword(8);
        m.setBody("Tu nueva clave de Crysallis Events es: " + password);

        try {
            boolean i= m.send();
            if(i==true){
                String passwordEnc = MainActivity.encryptThisString(password);
                socio.setPassword(passwordEnc);
                saveUser(getString(R.string.passwordChanged),getString(R.string.passwordNotChanged));
                Toast.makeText(getApplicationContext(),"Email was sent successfully ",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Email was not sent successfully ",Toast.LENGTH_LONG).show();

            }

        } catch (Exception e2) {
            Toast.makeText(getApplicationContext(), e2.toString(), Toast.LENGTH_LONG).show();

        }

    }

    public String getPassword(int length){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        Random rand = new Random();

        for(int i = 0; i < length; i++){
            char c = chars[rand.nextInt(chars.length)];
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    public void saveUser(String success, String fail){
        SociosService sociosService = Api.getApi().create(SociosService.class);
        Call<Socio> socioCall = sociosService.putSocio(socio.getId(),socio);
        socioCall.enqueue(new Callback<Socio>() {
            @Override
            public void onResponse(Call<Socio> call, Response<Socio> response) {
                if(response.isSuccessful()){
                    Toast.makeText(RecuperarActivity.this, success,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RecuperarActivity.this, fail,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Socio> call, Throwable t) {
                Toast.makeText(RecuperarActivity.this,t.getCause() + "-" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



}
