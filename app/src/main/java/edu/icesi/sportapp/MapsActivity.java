package edu.icesi.sportapp;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.icesi.sportapp.model.entity.EventSport;
import edu.icesi.sportapp.model.entity.LatLong;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener,
        GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    private Marker locationUser;
    private Marker selectedMarker;
    Boolean actualPosition=true;
    JSONObject jso;
    Double longitudOrigin,latitudOrigin;
    private boolean llegar=false;

    private Double latiDestino;
    private Double longitudDestino;
    private TextView msg;
    private Button back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        msg=findViewById(R.id.site_tv);
        back=findViewById(R.id.butback);


        Bundle parametros=this.getIntent().getExtras();
        EventSport eventSport=null;
        if(parametros!=null) {

            llegar=true;

            eventSport = (EventSport) getIntent().getExtras().get("evento");
            latiDestino=eventSport.getLatitude();
            longitudDestino=eventSport.getLongitude();

          //  Toast toast = Toast.makeText(this, latiDestino+":"+longitudDestino, Toast.LENGTH_SHORT);
           // toast.show();


        }

        EventSport finalEventSport = eventSport;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),InfoEvent.class);
                i.putExtra("event", finalEventSport);
                startActivity(i);
                finish();
            }
        });




        }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);

        LatLng icesi = new LatLng(3.342262, -76.529901);
        locationUser = mMap.addMarker(new MarkerOptions().position(icesi).title("Usted").icon(
                BitmapDescriptorFactory.fromResource(R.drawable.marker_user)
        ));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(icesi, 15));

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  1000, 0, this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        selectedMarker = mMap.addMarker(new MarkerOptions().position(latLng));

        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this)
                .setTitle("Agregar sitio")
                .setMessage("¿Desea agregar este sitio?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedMarker.remove();
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("location", latLng);
                        setResult( RESULT_OK, intent );
                        finish();
                    }
                });
        builder.show();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        locationUser.setPosition(pos);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));


        if(actualPosition && llegar){

            msg.setText("Ruta");
            back.setVisibility(View.VISIBLE);

            latitudOrigin=location.getLatitude();
            longitudOrigin=location.getLongitude();
            actualPosition=false;

            LatLng mipos = new LatLng(latitudOrigin, longitudOrigin);
            locationUser.setPosition(mipos);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));

            LatLng destino= new LatLng(latiDestino, longitudDestino);

            Marker marker = mMap.addMarker(new MarkerOptions().position(destino).title("Destino"));

            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(latitudOrigin,longitudOrigin), new LatLng(latiDestino, longitudDestino))
                    .width(10)
                    .color(Color.RED));


            String url="https://maps.googleapis.com/maps/api/directions/json?origin="+latitudOrigin+","+longitudOrigin+"&destination="+latiDestino+","+longitudDestino+"&key=AIzaSyCZEGuaxoHFIzciOrwrU5WukeVQr6Puo1A";
            RequestQueue queue= Volley.newRequestQueue(this);
            StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        jso= new JSONObject(response);
                        trazarRuta(jso);
                        Log.i("RUTAA",response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(stringRequest);

        }

    }

    private void trazarRuta(JSONObject jso) {


        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){

                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length();j++){

                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k<jSteps.length();k++){


                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.GRAY).width(5));



                    }



                }



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
