package com.example.chrysallis.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chrysallis.R;
import com.example.chrysallis.classes.Socio;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FragmentProfile extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1 ;
    private Socio socio;
    private ImageView imagenPerfil;

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
        nombrePerfil.setText(socio.getNombre());
        ubicacionPerfil.setText(getResources().getString(R.string.community));

        refrescarImagen();


        String idioma = "english";
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

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    if(hasCamera()){
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);

                    }else{
                        Toast.makeText(getContext(), "El dispositivo no tiene cámara", Toast.LENGTH_LONG);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
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
                Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();   //si el usuario acepta los permisos
                cambiarImagen();  //Ejecutamos el programa
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
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
                        refrescarImagen();

                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri uri = data.getData();
                        byte[] imagen = convertImageToByte(uri);
                        socio.setImagenUsuario(imagen);
                        refrescarImagen();
                    }
                    break;
            }
        }
    }

    public byte[] convertImageToByte(Uri uri){
        byte[] data = null;
        try {
            ContentResolver cr = getContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

}
