package com.example.fenrirassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener{

    private LocationManager locationManager;
    private String latitude;
    private String longitude;
    private Spinner rangeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        rangeSpinner = (Spinner)findViewById(R.id.range);
    }

    public void search(View view){
        RestaurantSearcher restaurantSearcher = new RestaurantSearcher(this);
        restaurantSearcher.execute(latitude, longitude, String.valueOf(rangeSpinner.getSelectedItemPosition() + 1));
    }

    public void transSearchResult(String result){
        if(result != null) {
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("result", result);
            startActivity(intent);
        }else{
            TextView error = (TextView)findViewById(R.id.error);
            error.setText("お店が見つかりません");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.latitude = String.valueOf(location.getLatitude());
        this.longitude = String.valueOf(location.getLongitude());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
