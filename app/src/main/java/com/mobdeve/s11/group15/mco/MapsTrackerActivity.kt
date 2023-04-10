package com.mobdeve.s11.group15.mco

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.*
import android.widget.Chronometer
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.mobdeve.s11.group15.mco.Database.Progress
import com.mobdeve.s11.group15.mco.Database.ProgressDbHelper
import com.mobdeve.s11.group15.mco.Database.UserDbHelper
import com.mobdeve.s11.group15.mco.databinding.ActivityGooglemapsBinding
import java.time.LocalDate
import java.util.*
import kotlin.math.roundToInt


class MapsTrackerActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var viewBinding: ActivityGooglemapsBinding

    //Maps
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    // Variables for DatabaseHelpers
    private lateinit var userDbHelper: UserDbHelper
    private lateinit var progressDbHelper: ProgressDbHelper
    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }

    private var geocoder: Geocoder? = null
    var locationRequest: LocationRequest? = null

    private lateinit var startingLocation: Location
    var startingLocationMarker: Marker? = null

    private lateinit var destinationLocation: Location
    var userLocationMarker: Marker? = null

    private lateinit var locationManager: LocationManager

    //Stopwatch
    private lateinit var timer: Chronometer

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set viewBinding to activity_googlemaps.xml
        viewBinding = ActivityGooglemapsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        timer = viewBinding.timerTextView
        timer.base = SystemClock.elapsedRealtime()
        timer.start()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        geocoder = Geocoder(this)

        startingLocation = Location("dummy")
        destinationLocation = Location("dummy")

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(2000)
            .setMaxUpdateDelayMillis(100)
            .build()

        //Get intent from previous activity
        val mapsTrackerIntent = intent
        val getEmail =
            mapsTrackerIntent.getStringExtra(IntentKeys.EMAIL_KEY.name) //Email from profile

        val dateToday = LocalDate.now().toString()

        userDbHelper =
            UserDbHelper.getInstance(this@MapsTrackerActivity)!! // Initialize UserDbHelper
        val getUser =
            userDbHelper.getUser(getEmail.toString()) // Get user from the email coming from intent

        // Get the radio button ID for the activityMET
        var radioAction = findViewById<RadioGroup>(R.id.radioActivityMaps)

        viewBinding.stopBtn.setOnClickListener {
            // Stops Timer
            timer.stop()

            // Calculation to get Minutes
            val elapsedMinutes = ((SystemClock.elapsedRealtime() - timer.base) / 1000) / 60

            // Calculation to get Seconds
            val elapsedSeconds = (SystemClock.elapsedRealtime() - timer.base) / 1000 % 60

            // Calculation to get Distance
            var theta = startingLocation.longitude - destinationLocation.longitude
            var distance = (Math.sin(startingLocation.latitude * Math.PI / 180.0)
                    * Math.sin(destinationLocation.latitude * Math.PI / 180.0)
                    + Math.cos(startingLocation.latitude * Math.PI / 180.0)
                    * Math.cos(destinationLocation.latitude * Math.PI / 180.0)
                    * Math.cos(theta * Math.PI / 180.0))
            distance = Math.acos(distance)
            distance = (distance * 180.0 / Math.PI) * 60 * 1.1515 //Miles

            distance *= 1609.344 //Meters

            // get selected radio button from radioAction
            var selectedId: Int = radioAction.checkedRadioButtonId
            // find the radiobutton by returned id
            var radioActionButton = findViewById<RadioButton>(selectedId)

            // Progress value
            var distanceTraveled =  distance.toInt()
            var timeElapsedMinutes = elapsedMinutes.toInt()
            var timeElapsedSeconds = elapsedSeconds.toInt()
            var caloriesBurned: Float =
                calculateCalBurn(radioActionButton.text.toString(), getUser.weight, timeElapsedMinutes, timeElapsedSeconds)
            var date: String = dateToday
            var email = getEmail

            //When STOP button is clicked send values to ProgressTrackerActivity.kt
            val stopIntent: Intent =
                Intent(this@MapsTrackerActivity, ProgressTrackerActivity::class.java)

            progressDbHelper =
                ProgressDbHelper.getInstance(this@MapsTrackerActivity)!! // Initialize ProgressDbHelper
            // Add progress record to the database
            progressDbHelper.addProgress(
                Progress(
                    radioActionButton.text.toString(),
                    distanceTraveled,
                    timeElapsedMinutes,
                    timeElapsedSeconds,
                    caloriesBurned,
                    date,
                    email.toString(),
                    0
                )
            )

            stopIntent.putExtra(
                IntentKeys.EMAIL_KEY.name,
                getEmail
            ) //Send email to ProgressTrackerActivity.kt
            startActivity(stopIntent)

            finish()
        }
    }

    private fun calculateCalBurn(Activity: String, weight: Int, minutes: Int, seconds: Int): Float {
        var totalCalBurn: Float
        var MET: Float = 0f

        var time: Float = minutes.toFloat() + (seconds.toFloat() / 60)
        if (Activity == "Walking") {
            MET = 3.5F
        } else if (Activity == "Jogging") {
            MET = 7.0F
        }

        totalCalBurn = (time * (MET * 3.5 * weight) / 200).toFloat()

        return (totalCalBurn * 100f).roundToInt() / 100f
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isZoomControlsEnabled = true

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                //We can show user a dialog why this permission is necessary
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                )
            }
        }

    }

    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if (mMap != null) {
                locationResult.lastLocation?.let { setUserLocationMarker(it) }
            }
        }
    }

    private fun setUserLocationMarker(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        if (userLocationMarker == null) {
            //Create a new marker
            val markerOptions = MarkerOptions()
            val markerOptionsDesitination = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.rotation(location.bearing)
            markerOptions.anchor(0.5.toFloat(), 0.5.toFloat())

            markerOptionsDesitination.position(latLng)
            markerOptionsDesitination.icon(BitmapDescriptorFactory.fromResource(R.drawable.vector))

            //Saves Starting Location
            startingLocation.latitude = location.latitude
            startingLocation.longitude = location.longitude
            startingLocationMarker = mMap.addMarker(markerOptions)

            //Makes Destination Pin
            userLocationMarker = mMap.addMarker(markerOptionsDesitination)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19f))
        } else {

            //Saves Destination Location
            destinationLocation.latitude = location.latitude
            destinationLocation.longitude = location.longitude


            //Uses Destination Pin
            userLocationMarker!!.position = latLng
            userLocationMarker!!.rotation = location.bearing
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationRequest?.let {
            fusedLocationClient.requestLocationUpdates(
                it,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        }
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun enableUserLocation() {
        mMap.isMyLocationEnabled = true
    }

    @SuppressLint("MissingPermission")
    private fun zoomToUserLocation() {
        val locationTask: Task<Location> = fusedLocationClient.lastLocation
        locationTask.addOnSuccessListener { location ->
            val latLng = LatLng(location.latitude, location.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation()
                zoomToUserLocation()
            }
        }
    }

}