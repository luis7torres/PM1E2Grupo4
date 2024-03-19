package com.example.pm1e2grupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pm1e2grupo4.Config.Contactos;

import java.util.List;

public class ViewActivity extends AppCompatActivity {

    Button editar, eliminar, contactos, mapa;
    EditText nombre, telefono, lat, lon;
    Context context;

    //private List<Contactos> Listacontactos; // Lista para elementos filtrados
    //Contactos contactos1 = new Contactos();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //Obtener datos del intent que inició esta actividad
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String nombreContacto = intent.getStringExtra("nombre");
        String numero = intent.getStringExtra("telefono");
        String latitud = intent.getStringExtra("latitud");
        String longitud = intent.getStringExtra("longitud");


        // Obtener referencias a los elementos de la interfaz de usuario

        contactos = (Button) findViewById(R.id.btnContactos);
        editar = (Button) findViewById(R.id.btnEditar);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        mapa = (Button) findViewById(R.id.btnVerMapa);
        nombre = (EditText) findViewById(R.id.txtNombre2);
        telefono = (EditText) findViewById(R.id.txtTelefono2);
        lat = (EditText) findViewById(R.id.txtLatitud2);
        lon = (EditText) findViewById(R.id.txtLongitud2);

        // Establecer los textos en los EditText con los datos del contacto
        nombre.setText(nombreContacto);
        telefono.setText(numero);
        lat.setText(latitud);
        lon.setText(longitud);


        //Evento para el boton Editar
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), EditActivity.class);

                // Pasar los datos del contacto actual a la actividad de edición
                intent1.putExtra("id", id);
                intent1.putExtra("nombre", nombreContacto);
                intent1.putExtra("telefono", numero);
                intent1.putExtra("latitud", latitud);
                intent1.putExtra("longitud", longitud);

                startActivity(intent1);

            }
        });

        //Evento para el boton Eliminar
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent1 = new Intent(getApplicationContext(), EliminarActivity.class);
                startActivity(intent1);*/
                eliminarContacto(id); // Llamar al método para eliminar el contacto
            }
        });

        //Evento para el boton Ver Mapa
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Crear un intent para iniciar la actividad de ubicación en el mapa
                Intent intent = new Intent(getApplicationContext(), UbicacionActivity.class);
                // Pasar los datos necesarios para la ubicación al intent
                //intent.putExtra("nombre", nombreContacto);
                intent.putExtra("latitud", latitud);
                intent.putExtra("longitud", longitud);
                // Iniciar la actividad de ubicación en el mapa
                startActivity(intent);

               /* System.out.println(nombreContacto);
                System.out.println(latitud);
                System.out.println(longitud);*/


            }
        });

        //Evento para el boton de contactos
        contactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), ActivityPlaceHolder.class);
                startActivity(intent1);
            }
        });
    }

    //Metodo para eliminar un contacto especifico mediante una solicitud HTTP DELETE a la API.
    public void eliminarContacto(int idContacto) {
        // Construir la URL para la solicitud DELETE a la API
        String url = APIConexion.extraerEndpoint() + "DeleteContacto.php?id=" + idContacto;
        // Crear una solicitud StringRequest con método DELETE
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Al recibir una respuesta exitosa, iniciar la actividad de la lista de contactos
                        Intent intent = new Intent(getApplicationContext(), ActivityPlaceHolder.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();  // Manejar errores en la respuesta
                    }
                });

        // Obtener la cola de solicitudes Volley y agregar la solicitud
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


}