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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        EditText editTextPassword = findViewById(R.id.editTextPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String password = editTextPassword.getText().toString();
                String passwordEncrypted = encryptThisString(password);
                passwordEncrypted = passwordEncrypted.toUpperCase();
                SociosService sociosService = Api.getApi().create(SociosService.class);
                Call<Socio> callSocioLogin = sociosService.SocioLogin(editTextPhone.getText().toString(),passwordEncrypted);
                callSocioLogin.enqueue(new Callback<Socio>() {
                    @Override
                    public void onResponse(Call<Socio> call, Response<Socio> response) {
                        switch (response.code()){
                            case 200:
                                Socio socio = response.body();
                                if(socio != null){
                                    Intent intent = new Intent(MainActivity.this, DestacadosActivity.class);
                                    intent.putExtra("socio", socio);
                                    startActivity(intent);
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

    //Método que encipta el password con SHA-512
    public static String encryptThisString(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
