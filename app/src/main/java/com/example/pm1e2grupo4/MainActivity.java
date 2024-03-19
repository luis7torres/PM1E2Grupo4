package com.example.pm1e2grupo4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pm1e2grupo4.Config.Contactos;
import com.example.pm1e2grupo4.Config.Personas;
import com.example.pm1e2grupo4.Config.RestApiMethods;
import com.example.pm1e2grupo4.Config.Transacciones;
import com.kyanogen.signatureview.SignatureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements LocationListener {

    Button btn_salvarContacto, btn_contactosSalvados, btn_firmaDigital;
    EditText txt_nombre, txt_telefono, txt_latitud, txt_longitud;
    Boolean actualizacionActiva;
    //SignatureView signatureView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        actualizacionActiva = false;
        //signatureView = (SignatureView) findViewById(R.id.firma_view);
        txt_nombre = (EditText) findViewById(R.id.txtNombre);
        txt_telefono = (EditText) findViewById(R.id.txtTelefono);
        txt_latitud = (EditText) findViewById(R.id.txtLatitud);
        txt_longitud = (EditText) findViewById(R.id.txtLongitud);
        //this.txt_latitud.setText("15.770359896871657");
        //this.txt_longitud.setText("-86.81250922860903");
        btn_firmaDigital = (Button) findViewById(R.id.btnFirmaDigital);
        btn_salvarContacto = (Button) findViewById(R.id.btnSalvarContactos);
        btn_contactosSalvados = (Button) findViewById(R.id.btnContactosSalvados);

        //Evento para el boton de Salvar Contacto
        btn_salvarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreContacto = txt_nombre.getText().toString();  // Obtiene los valores de los campos de texto
                String numero = txt_telefono.getText().toString();
                String lat = txt_latitud.getText().toString();
                String longi = txt_longitud.getText().toString();

                if(validarGPS()==true){
                    Toast.makeText(MainActivity.this, "GPS no esta activado",Toast.LENGTH_SHORT).show();
                }else{
                    // Verifica si algún campo está vacío
                    if(nombreContacto.isEmpty() || numero.isEmpty() || lat.isEmpty() || longi.isEmpty()){
                        // Muestra un mensaje de advertencia si algún campo está vacío
                        Toast.makeText(MainActivity.this, "INGRESE TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // Llama al método para crear un nuevo contacto con los datos proporcionados
                        crearContacto(nombreContacto, numero, lat, longi);
                    }
                }

            }
        });

        //Evento para el boton de Contactos Salvados
        btn_contactosSalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityPlaceHolder.class);
                startActivity(intent);
            }
        });

        // Llama al método para obtener la ubicación del dispositivo
        getLocation();

        //Validar el ingreso de solo letras en el nombre
        expresiones_regulares();
    }

    // Método para obtener la ubicación del dispositivo
    @SuppressLint("MissingPermission")
    public void retrieveLocation() {
        // Obtiene el servicio de ubicación del sistema
        LocationManager manager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        // Verifica si el GPS está habilitado
        boolean isGpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGpsEnabled) {
            Toast.makeText(getApplicationContext(), "GPS NO ESTA ACTIVADO", Toast.LENGTH_LONG).show();
        } else {
            // GPS está activado, procede con la solicitud de actualizaciones de ubicación
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, this);
        }
    }


    // Método para verificar y solicitar permisos de ubicación
    public void getLocation(){
        // Verifica si se tienen los permisos necesarios para acceder a la ubicación fina
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            retrieveLocation(); // Si se tienen los permisos, llama al método para obtener la ubicación
        }else{
            // Si no se tienen los permisos, solicita al usuario que los conceda
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Inicializa el geocoder con la configuración regional predeterminada del dispositivo
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            // Imprime la latitud y longitud en la consola de desarrollo
            System.out.println("Latitude:" + location.getLatitude());
            System.out.println("Longitude:" + location.getLongitude());

            // Actualiza los campos de texto con la latitud y longitud
            txt_latitud.setText(Double.toString(location.getLatitude()));
            txt_longitud.setText(Double.toString(location.getLongitude()));

            // Obtiene la dirección a partir de la latitud y longitud utilizando geocoder
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            // Captura y lanza una RuntimeException si hay un error en la obtención de la dirección
            throw new RuntimeException(e);
        }

    }

    // Métodos de la interfaz LocationListener que gestionan cambios en la ubicación y estado del proveedor de ubicación.
    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        // Gestiona cambios en la ubicación cuando se recibe una lista de ubicaciones.
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        // Gestiona la finalización del flush de ubicación.
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Gestiona cambios en el estado del proveedor de ubicación.
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        // Gestiona la activación del proveedor de ubicación.
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        // Gestiona la desactivación del proveedor de ubicación.
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        // Gestiona cambios en la captura del puntero.
        super.onPointerCaptureChanged(hasCapture);
    }

    public void crearContacto(String nombre, String telefono, String latitud, String longitud) {


        // Crea una cola de solicitudes Volley
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        /*Contactos contactos = new Contactos();

        contactos.setNombre(txt_nombre.getText().toString());
        contactos.setNumero(txt_telefono.getText().toString());
        contactos.setLatitud(txt_latitud.getText().toString());
        contactos.setLongitud(txt_longitud.getText().toString());*/
        // Construye la URL completa para la creación del contacto
        String url = APIConexion.extraerEndpoint() + "CreateContacto.php";
        // Crea un objeto JSON con los datos del nuevo contacto
        JSONObject data = new JSONObject();
        try {
            data.put("nombre", nombre);
            data.put("telefono", telefono);
            data.put("latitud", latitud);
            data.put("longitud", longitud);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Crea una solicitud JsonObjectRequest para enviar los datos al servidor
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtiene el mensaje de respuesta del servidor
                            String message = response.getString("message");
                            // Muestra un mensaje Toast con la respuesta.
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            limpiar(); // Limpia los campos después de crear el contacto
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage()); // Imprime el mensaje de error en la consola de desarrollo
                        // Muestra un mensaje Toast informando sobre el error al crear el contacto
                        Toast.makeText(MainActivity.this, "Error al crear el contacto" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(request); // Agrega la solicitud a la cola para su procesamiento
    }

    //Limpia los campos de nombre y teléfono, y restablece la imagen predeterminada.
    public void limpiar() {
        // Establece el texto de nombre y teléfono a vacío
        txt_nombre.setText(Transacciones.Empty);
        txt_telefono.setText(Transacciones.Empty);

    }

    public boolean validarGPS(){
        boolean validar=false;
        if(txt_latitud.getText().toString().isEmpty() && txt_longitud.getText().toString().isEmpty()){
            validar = true;
        }
        return validar;
    }

    /*Metodo para poder validar expresiones regulares*/
    public void expresiones_regulares(){
        InputFilter soloLetras = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder();
                for (int i = start; i < end; i++) {
                    if (Pattern.matches("[a-zA-Z\\s]", String.valueOf(source.charAt(i)))) {
                        builder.append(source.charAt(i));
                    }
                    // Los caracteres que no cumplen con la condición simplemente no se añaden al constructor
                }
                // Si todos los caracteres son válidos, devolver null no cambia la entrada
                return source.length() == builder.length() ? null : builder.toString();
            }
        };
        txt_nombre.setFilters(new InputFilter[]{soloLetras});
    }

}