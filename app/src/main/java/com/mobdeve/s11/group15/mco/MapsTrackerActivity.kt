package com.mobdeve.s11.group15.mco

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
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
import com.mobdeve.s11.group15.mco.Database.Progress
import com.mobdeve.s11.group15.mco.Database.ProgressDbHelper
import com.mobdeve.s11.group15.mco.Database.UserDbHelper
import com.mobdeve.s11.group15.mco.databinding.ActivityGooglemapsBinding

class MapsTrackerActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userDbHelper: UserDbHelper
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

        //Get intent from previous activity
        val mapsTrackerIntent = intent
        val getEmail = mapsTrackerIntent.getStringExtra(IntentKeys.EMAIL_KEY.name) //Email from profile

        userDbHelper = UserDbHelper.getInstance(this@MapsTrackerActivity)!! // Initialize UserDbHelper
        val getUser = userDbHelper.getUser(getEmail.toString()) // Get user from the email coming from intent

        // Get the radio button ID for the activityMET
        var radioAction = findViewById<RadioGroup>(R.id.radioActivityMaps)

        viewBinding.stopBtn.setOnClickListener{
            // get selected radio button from radioAction
            var selectedId: Int = radioAction.checkedRadioButtonId
            // find the radiobutton by returned id
            var radioActionButton = findViewById<RadioButton>(selectedId)

            // [THIS IS JUST SAMPLE TO DATA THAT WILL STORE INTO THE DATABASE TO CHECK PROGRESS TRACKER]
            var distanceTraveled: Int = 1000
            var timeElapsed: Int = 60
            var caloriesBurned: Float = calculateCalBurn(radioActionButton.text.toString(), getUser.weight, timeElapsed)
            var date: String = "March 01, 2023"
            var email = getEmail

            //When STOP button is clicked send values to ProgressTrackerActivity.kt
            val stopIntent: Intent = Intent(this@MapsTrackerActivity, ProgressTrackerActivity::class.java)

            progressDbHelper = ProgressDbHelper.getInstance(this@MapsTrackerActivity)!! // Initialize ProgressDbHelper
            // Add progress record to the database
            progressDbHelper.addProgress(Progress(radioActionButton.text.toString(), distanceTraveled, timeElapsed, caloriesBurned, date, email.toString(), 0))

            stopIntent.putExtra(IntentKeys.EMAIL_KEY.name, getEmail) //Send email to ProgressTrackerActivity.kt
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