package dsa.eetac.upc.edu.etakemon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class Capturar extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    List<Marker> markers=new ArrayList<>();
    LocationManager locationMgr;
    HashMap<Integer,Position> positions=new HashMap<>();
    HashMap<Integer,Etakemon>hs=new HashMap<>();
    HashMap<String,Integer>rel=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Inici.RestClient.get("/dsa/android/all/jeje", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                try {
                    for (int i = 0; i < json.length(); i++) {
                        int id = Integer.parseInt(json.getJSONObject(i).get("id").toString());
                        String name = json.getJSONObject(i).get("name").toString();
                        String description = json.getJSONObject(i).get("description").toString();
                        int health = Integer.parseInt(json.getJSONObject(i).get("health").toString());
                        String type = json.getJSONObject(i).get("type").toString();
                        Etakemon e = new Etakemon(name, description, type, id, health);
                        hs.put(id, e);
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCOde, Header[] headers, String s, Throwable i){

            }

        });

        Inici.RestClient.get("/dsa/android/positions", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                try {
                    for (int i = 0; i < json.length(); i++) {
                        int id = Integer.parseInt(json.getJSONObject(i).get("id").toString());
                        String name = json.getJSONObject(i).get("name").toString();
                        float xposition= Float.parseFloat(json.getJSONObject(i).get("xposition").toString());
                        float yposition= Float.parseFloat( json.getJSONObject(i).get("yposition").toString());
                        Position p=new Position(name,id,yposition,xposition);
                        positions.put(id, p);
                    }
positions.size();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCOde, Header[] headers, String s, Throwable i){

            }

        });


        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {
                                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                        mGoogleApiClient);
                            }
                            onLocationChanged(mLastLocation);
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask,1,1000);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                update();
            }
        }, 1000);

        /*Timer timerPos = new Timer();
        TimerTask dotimerPos = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        mGoogleMap.clear();
                        update();
                    }
                });
            }
        };
        timerPos.schedule(dotimerPos,1,900000);*/
    }

    public void comparePosition(int idEtak, int idPos){
        rel.put(hs.get(idEtak).getName(),idPos);
        Marker m= mGoogleMap.addMarker(new MarkerOptions()
                .visible(false)
                .title(hs.get(idEtak).getName())
                .position(new LatLng(positions.get(idPos).getXposition(), positions.get(idPos).getYposition()))
        );
        markers.add(m);
    }
public void update() {
        mGoogleMap.clear();
    Inici.RestClient.get("/dsa/android/relpositions", new TextHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String c) {

            String[] trozos = c.split("-");
            for (int i = 0; i < trozos.length; i++) {
                String[] uniquepos = trozos[i].split(",");
                String idEtak = uniquepos[0];
                String idPos = uniquepos[1];
                comparePosition(Integer.parseInt(idEtak), Integer.parseInt(idPos));
            }
        }

        @Override
        public void onFailure(int statusCOde, Header[] headers, String s, Throwable i) {

        }
    });
}
    @Override
public void onStart(){
    super.onStart();


}
    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setScrollGesturesEnabled(false);

            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setScrollGesturesEnabled(false);
        }
        googleMap.setOnMarkerClickListener(this);

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
            String name=marker.getTitle();
            String position=positions.get(rel.get(name)).getName();
            Bundle extra=getIntent().getExtras();
            String usuari=extra.getString("name");
            Bundle extra1=new Bundle();
            extra1.putString("name",name);
            extra1.putString("username",usuari);
            extra1.putString("position",position);
            Intent caza = new Intent(getApplicationContext(), Caza.class);
            caza.putExtras(extra1);
            marker.remove();
            startActivity(caza);

        return false;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        for (int i=0;i<markers.size();i++) {
            float[] results = new float[1];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                    markers.get(i).getPosition().latitude, markers.get(i).getPosition().longitude, results);
            if (results[0] < 15) {
                String title=markers.get(i).getTitle().toLowerCase();
                int imag = getResources().getIdentifier(title+"i", "drawable", getPackageName());
                markers.get(i).setIcon(BitmapDescriptorFactory.fromResource(imag));
                markers.get(i).setVisible(true);

            } else {
                markers.get(i).setVisible(false);
            }
        }
        //optionally, stop location updates if only current location is needed
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Capturar.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass
        // Stop method tracing that the activity started during onCreate()

        Debug.stopMethodTracing();
    }

}