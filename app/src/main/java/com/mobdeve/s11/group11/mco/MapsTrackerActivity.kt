package com.mobdeve.s11.group11.mco

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mobdeve.s11.group11.mco.databinding.ActivityGooglemapsBinding

class MapsTrackerActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    companion object {
        //Set of KEYs for String name of the values that are about to be sent to ProgressTrackerActivity.kt
        const val ACTIVITY_KEY = "ACTIVITY_KEY"
        const val DISTANCE_KEY = "DISTANCE_KEY"
        const val TIME_KEY = "TIME_KEY"
        const val CAL_BURNED_KEY = "CAL_BURNED_KEY"
        const val EMAIL_KEY = "EMAIL_KEY"
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set viewBinding to activity_googlemaps.xml
        val viewBinding : ActivityGooglemapsBinding = ActivityGooglemapsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        viewBinding.stopBtn.setOnClickListener{
            //When STOP button is clicked send values to ProgressTrackerActivity.kt
            val stopIntent: Intent = Intent(this@MapsTrackerActivity, ProgressTrackerActivity::class.java)

            //Send these values [P.S. these are just sample data]
            stopIntent.putExtra(ACTIVITY_KEY, "Jogging")
            stopIntent.putExtra(DISTANCE_KEY, 2000)
            stopIntent.putExtra(TIME_KEY, 120)
            stopIntent.putExtra(CAL_BURNED_KEY, 520.38f)
            stopIntent.putExtra(EMAIL_KEY, "aleck@gmail.com")

            startActivity(stopIntent)

            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()
    }

    private fun setUpMap(){

        //Asks for Location Permission
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_REQUEST_CODE
            )

            return
        }

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) {  location ->

            if(location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
            }

        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker) = false
}