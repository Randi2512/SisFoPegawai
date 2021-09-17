package com.example.tugasbesar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
    public static final int REQUEST_LOCATION_PERMISSION=1;
    public static final float INITIAL_ZOOM = 12f;
    private static final String URL = "https://192.168.13.27/ServerTugasBesar/addmarker.php";
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private MarkerOptions marker = new MarkerOptions();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMapStyles(mMap);
        enableDynamicMarker();

        // Add a marker in Sydney and move the camera
        LatLng kbup = new LatLng(0.1403736, 100.1648054);//kantua bupati
        latlngs.add(new LatLng(0.1364361,100.1672194 ));//smanluxika
        latlngs.add(new LatLng(0.1782852,100.0848678));//puncak tonang
        latlngs.add(new LatLng(0.1419468,100.1651579));//polres pasaman
        latlngs.add(new LatLng(0.1284539,100.1678095));//pasar lama lbs
        mMap.addMarker(new MarkerOptions().position(kbup).title("Kantor Bupati Pasaman"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kbup));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kbup,INITIAL_ZOOM));
        for (LatLng point : latlngs){
            marker.title("Point of View");
            marker.snippet("Tempat terdekat dari Kantor Bupati Pasaman ");
            marker.position(point);
            googleMap.addMarker(marker);
        }
        enableMyLocation();
        enableLongClick(mMap);
        enableDropPin(mMap);
        enableMapStyles(mMap);
        enableDynamicMarker();
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing gagal.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Tidak dapat menemukan style. Error: ", e);
        }


    }



    private void enableMapStyles(GoogleMap googleMap) {
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));
            if (!success) {
                Log.e(TAG, "Style parsing gagal.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Tidak dapat menemukan style. Error: ", e);
        }
    }


    private void enableMyLocation() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this,new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }
    private void enableDynamicMarker() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSONResult" , response.toString());
                        JSONObject jObj = null;
                        try{
                            jObj =new JSONObject(response);
                            JSONArray result = jObj.getJSONArray("LOCATION");
                            for(int i=0;i<result.length();i++){
                                JSONObject jsonObject1=result.getJSONObject(i);
                                String lat_i = jsonObject1.getString("1");
                                String long_i = jsonObject1.getString("2");
                                String locationName = jsonObject1.getString("3");
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(lat_i) , Double.parseDouble(long_i)))
                                        .title(Double.valueOf(lat_i).toString() + "," + Double.valueOf(long_i).toString())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                        .snippet(locationName));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-0.9021187,100.3489041), 11.0f));
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        } }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MapsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }});
        int socketTimeout = 10000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void enableLongClick(final GoogleMap mMap) {
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String snippet = String.format(Locale.getDefault(),
                        getString(R.string.lat_long_snippet),
                        latLng.latitude,
                        latLng.longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.dropped_pin))
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_BLUE)));
            }
        });
    }
    private void enableDropPin(final GoogleMap mMap) {
        mMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
                                       @Override
                                       public void onPoiClick(PointOfInterest poi) {
                                           Marker poiMarker = mMap.addMarker(new MarkerOptions()
                                                   .position(poi.latLng)
                                                   .title(poi.name));
                                           poiMarker.showInfoWindow();
                                       }
                                   }
        );
    }





    }

