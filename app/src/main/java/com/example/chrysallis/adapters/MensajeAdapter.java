package com.example.chrysallis.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chrysallis.R;
import com.example.chrysallis.classes.Evento;
import com.example.chrysallis.classes.Mensaje;
import com.example.chrysallis.classes.Socio;

import java.util.ArrayList;


public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajesViewHolder> implements View.OnClickListener{
    private ArrayList<Mensaje> mensajes;
    private View.OnClickListener listener;
    private Socio socio;

    public MensajeAdapter(ArrayList<Mensaje> mensajes, Socio socio) {
        this.mensajes = mensajes;
        this.socio = socio;
    }

    //crea el viewHolder basado en el layout evento_item
    @NonNull
    @Override
    public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mensaje_item, viewGroup, false);
        itemView.setOnClickListener(this);
        MensajesViewHolder mensajesViewHolder = new MensajesViewHolder(itemView);
        return mensajesViewHolder;
    }

    //metodo que recibe una posicion del recyclerview para llenarla con un evento de esa posicion
    @Override
    public void onBindViewHolder(@NonNull MensajesViewHolder viewHolder, int pos) {
        Mensaje mensaje = mensajes.get(pos);

        viewHolder.bindMensaje(mensaje, socio);
    }

    //cuenta la cantidad de Eventos
    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    //Metodo onItemCliclListener
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public static class MensajesViewHolder extends RecyclerView.ViewHolder {

        private TextView nom;
        private TextView textoMensaje;
        private RelativeLayout cardView;
        private TextView textoHora;

        //constructor del viewholder para guardar las views
        public MensajesViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nombreMensaje);
            textoMensaje = itemView.findViewById(R.id.textoMensaje);
            cardView = itemView.findViewById(R.id.cardMensaje);
            textoHora = itemView.findViewById(R.id.textoHora);

        }

        // metodo bindEvento para rellenar los campos del eventoItem
        public void bindMensaje(Mensaje mensaje, Socio socio) {

            String[] mensajeSplitted = mensaje.getMensaje().split(":");
            nom.setText(mensajeSplitted[0]);
            textoMensaje.setText(mensaje.getMensaje().replace(mensajeSplitted[0] + ":", ""));
            if(mensaje.getId_socio() == socio.getId()){
                cardView.setGravity(Gravity.LEFT);
            }
            else{
                cardView.setGravity(Gravity.RIGHT);
            }
            String soloHora = mensaje.getFecha().substring(11,16);
            textoHora.setText(soloHora);
        }
    }
}
