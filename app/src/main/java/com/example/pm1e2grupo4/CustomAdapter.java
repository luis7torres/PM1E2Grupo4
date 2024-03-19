package com.example.pm1e2grupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pm1e2grupo4.Config.Contactos;
import com.example.pm1e2grupo4.Config.Personas;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<Contactos> contactos; // Lista para elementos filtrados

    AdapterView.OnItemClickListener itemClickListener;

    // Constructor del adaptador personalizado
    public CustomAdapter(Context context, ArrayList<Contactos> contactos) {
        this.context = context;
        this.contactos = new ArrayList<>(contactos); // Crear una nueva lista basada en contactos
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contactos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_custom_list_view, parent, false);
        }

        // Inicialización de los componentes de la vista
        //TextView idTextView = convertView.findViewById(R.id.textViewId);
        TextView nombreTextView = convertView.findViewById(R.id.textViewNombre);
        TextView telefonoTextView = convertView.findViewById(R.id.textViewTelefono);


        Contactos contacto = contactos.get(position);

        //idTextView.setText(contacto.getId());
        nombreTextView.setText(contacto.getNombre());
        telefonoTextView.setText(contacto.getNumero());


        // Evento onClick
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pasar la información del producto seleccionado a la actividad de BiblioActivity
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra("id", contacto.getId());
                intent.putExtra("nombre",contacto.getNombre());
                intent.putExtra("telefono",contacto.getNumero());
                intent.putExtra("latitud",contacto.getLatitud());
                intent.putExtra("longitud",contacto.getLongitud());
                //intent.putExtra("imagen", contacto.getImagen());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}