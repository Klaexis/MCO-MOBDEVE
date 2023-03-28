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
import com.mobdeve.s11.group11.mco.Database.ProgressDbHelper
import com.mobdeve.s11.group11.mco.Database.UserDbHelper
import com.mobdeve.s11.group11.mco.databinding.ActivityGooglemapsBinding

class MapsTrackerActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var progressDbHelper: ProgressDbHelper

    companion object {
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
            stopIntent.putExtra(IntentKeys.ACTIVITY_KEY.name, "Jogging")
            stopIntent.putExtra(IntentKeys.DISTANCE_KEY.name, 2000)
            stopIntent.putExtra(IntentKeys.TIME_KEY.name, 120)
            stopIntent.putExtra(IntentKeys.CAL_BURNED_KEY.name, 520.38f)
            stopIntent.putExtra(IntentKeys.EMAIL_KEY.name, "aleck@gmail.com")

            startActivity(stopIntent)

            finish()
        }
    }

    private fun calculateCalBurn(Activity: String, weight: Int, time : Int): Float{
        var totalCalBurn: Float
        var MET: Float = 0f
        if(Activity == "Walking"){
            MET = 3.5F
        } else if(Activity == "Jogging"){
            MET = 7.0F
        }

        totalCalBurn = (time * (MET * 3.5 * weight) / 200).toFloat()
        return totalCalBurn
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