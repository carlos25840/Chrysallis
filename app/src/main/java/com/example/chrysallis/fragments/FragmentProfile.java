package com.example.chrysallis.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chrysallis.Api.Api;
import com.example.chrysallis.Api.ApiService.SociosService;
import com.example.chrysallis.DestacadosActivity;
import com.example.chrysallis.MainActivity;
import com.example.chrysallis.R;
import com.example.chrysallis.classes.Socio;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FragmentProfile extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1 ;
    private Socio socio;
    private ImageView imagenPerfil;
    private String idioma;
    private String lang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    //Cuando se crea la activity
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imagenPerfil = getView().findViewById(R.id.ImgPerfil);
        mostrarPerfil();
    }

    public FragmentProfile(Socio socio) {
        this.socio = socio;
    }

    public void mostrarPerfil(){
        TextView nombrePerfil = getView().findViewById(R.id.nombrePerfil);
        TextView ubicacionPerfil = getView().findViewById(R.id.ubicacionPerfil);
        TextView idiomaPerfil = getView().findViewById(R.id.languagePerfil);
        ImageButton editPassword = getView().findViewById(R.id.buttonEditPassword);
        ImageButton editLanguage =  getView().findViewById(R.id.buttonEditLanguage);
        ImageButton powerOff = getView().findViewById(R.id.powerOff);
        nombrePerfil.setText(socio.getNombre());
        ubicacionPerfil.setText(getResources().getString(R.string.community));

        refrescarImagen();


        idioma = "english";
        if(socio.getIdiomaDefecto() != null){
            idioma = socio.getIdiomaDefecto().toLowerCase();
        }
        switch (idioma){
            case "spanish":
                idioma = getResources().getString(R.string.Spanish);
                break;
            case "euskera":
                idioma = getResources().getString(R.string.Euskera);
                break;
            case "galician":
                idioma = getResources().getString(R.string.Galician);
                break;
            case "catalan":
                idioma = getResources().getString(R.string.Catalan);
                break;
            case "english":
            default:
                idioma = getResources().getString(R.string.English);
                break;
        }
        idiomaPerfil.setText(idioma);

        imagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted()){
                    cambiarImagen();
                }
            }
        });

        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPassword();
            }
        });

        editLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLanguage();
            }
        });

        powerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                powerOffButton();
            }
        });
    }


    public void refrescarImagen(){

        if(socio.getImagenUsuario() != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(socio.getImagenUsuario(), 0, socio.getImagenUsuario().length);
            imagenPerfil.setImageBitmap(Bitmap.createScaledBitmap(bmp, imagenPerfil.getWidth(),
                    imagenPerfil.getHeight(), false));
        }else{
            imagenPerfil.setImageResource(R.drawable.imagen_profile);
        }

    }

    public void cambiarImagen(){

        final CharSequence[] options = { getResources().getString(R.string.TakePhoto), getResources().getString(R.string.ChooseGallery),getResources().getString(R.string.cancel) };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.ChooseProfilePicture));

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals(getResources().getString(R.string.TakePhoto))) {
                    if(hasCamera()){
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);

                    }else{
                        Toast.makeText(getContext(), getResources().getString(R.string.NoCamera), Toast.LENGTH_LONG);
                    }
                } else if (options[item].equals(getResources().getString(R.string.ChooseGallery))) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals(getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private boolean hasCamera() {
        if (getContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT)){
            return true;
        } else {
            return false;
        }
    }


    public  boolean isPermissionGranted() {
        boolean permiso;
        //si la version es mayor de 6.0
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(Manifest.permission.CAMERA) //Si tiene permiso
                    == PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) //Si tiene permiso
                    == PackageManager.PERMISSION_GRANTED ) {
                Log.v("TAG","Permission is granted");
                permiso = true;
            } else {                                    //si no tiene permiso lo pide
                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
                permiso = false;
            }
        }
        else { //si la version es menor de android 6.0
            Log.v("TAG","Permission is granted");
            permiso = true;
        }
        return permiso;
    }


    //Método para comprobar que el usuario a dado permisos y ejecutar el programa
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if(requestCode==1)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), getResources().getString(R.string.PermisoDenegado), Toast.LENGTH_SHORT).show();   //si el usuario acepta los permisos
                cambiarImagen();  //Ejecutamos el programa
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.PermisoDenegado), Toast.LENGTH_SHORT).show();
                //si el usuario no nos da permisos no hacemos nada
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //ImageView foto = getView().findViewById(R.id.ImgPerfil);
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imagen = stream.toByteArray();
                        bmp.recycle();
                        socio.setImagenUsuario(imagen);

                        saveUser(getString(R.string.languageChanced), getString(R.string.languageNotChanced));
                        refrescarImagen();
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri uri = data.getData();
                        byte[] imagen = convertImageToByte(uri);
                        socio.setImagenUsuario(imagen);

                        saveUser(getString(R.string.languageChanced), getString(R.string.languageNotChanced));
                        refrescarImagen();
                    }
                    break;
            }
        }
    }

    public byte[] convertImageToByte(Uri uri){
        byte[] encoded = null;
        try {
            ContentResolver cr = getContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            encoded = Base64.encode(data, 0);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return encoded;
    }

    private void showDialogPassword() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_password, null);


        builder.setTitle(Html.fromHtml("<b>"+getString(R.string.changePassword)+"</b>"));
        builder.setView(v);

        final EditText editTextPassword = v.findViewById(R.id.editTextPassword);
        final EditText editTextConfirmation = v.findViewById(R.id.editTextConfirmation);

        builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(editTextPassword.getText().toString().equals(editTextConfirmation.getText().toString())){
                    String password = MainActivity.encryptThisString(editTextPassword.getText().toString());
                    socio.setPassword(password);
                    saveUser(getString(R.string.passwordChanged),getString(R.string.passwordNotChanged));
                }else{
                    Toast.makeText(getActivity(),R.string.passwordsNotMatch, Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    public void showDialogLanguage() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_Dialog_Alert);
        builder.setTitle(R.string.changeLanguage);

        //list of items
        final String[] items = getResources().getStringArray(R.array.languages);
        int pos = 0;

        do{
            if( !items[pos].equals(idioma)){
                pos++;
            }
        }while(pos < items.length && !items[pos].equals(idioma));
        // set single choice items
        builder.setSingleChoiceItems(items, pos,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                socio.setIdiomaDefecto("English");
                                idioma = getResources().getString(R.string.English);
                                lang = "en";
                                break;
                            case 1:
                                socio.setIdiomaDefecto("Spanish");
                                idioma = getResources().getString(R.string.Spanish);
                                lang = "es";
                                break;
                            case 2:
                                socio.setIdiomaDefecto("Catalan");
                                idioma = getResources().getString(R.string.Catalan);
                                lang = "ca";
                                break;
                            case 3:
                                socio.setIdiomaDefecto("Euskera");
                                idioma = getResources().getString(R.string.Euskera);
                                lang = "eu";
                                break;
                            case 4:
                                socio.setIdiomaDefecto("Galician");
                                idioma = getResources().getString(R.string.Galician);
                                lang = "gl";
                                break;
                        }
                    }
                });

        builder.setPositiveButton(R.string.accept,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveUser(getString(R.string.languageChanced), getString(R.string.languageNotChanced));
                        refrescarIdioma();
                        setLocale(lang);
                    }
                });

        builder.setNegativeButton(R.string.cancel,null);
        builder.show();
    }

    public void saveUser(String success, String fail){
        SociosService sociosService = Api.getApi().create(SociosService.class);
        Call<Socio> socioCall = sociosService.putSocio(socio.getId(),socio);
        socioCall.enqueue(new Callback<Socio>() {
            @Override
            public void onResponse(Call<Socio> call, Response<Socio> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(),success,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), fail,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Socio> call, Throwable t) {
                Toast.makeText(getActivity(),t.getCause() + "-" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void refrescarIdioma(){
        TextView idiomaPerfil = getView().findViewById(R.id.languagePerfil);
        idiomaPerfil.setText(idioma);
    }

    /*Método que nos cambia el idioma del juego en función del seleccionado*/
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        DestacadosActivity.refrescar(getFragmentManager());
        saveLocale(lang);


        /*new MaterialAlertDialogBuilder(getActivity())
                .setTitle(R.string.restartApp)
                .setMessage("Message")
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();*/

    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getActivity().getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void powerOffButton(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

}
