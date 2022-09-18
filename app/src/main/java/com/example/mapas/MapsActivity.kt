package com.example.mapas

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.mapas.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var deliveryMarker: Marker
    private var flagMarker: Boolean = false

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

       /* val buttonClick = findViewById<Button>(R.id.buttonConfirm)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Prueba::class.java)
            startActivity(intent)
        }*/

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
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener (this)
        createMarkers()
        enableLocation()

        map.setOnMapClickListener(this)
    }

    private fun createMarkers(){
        val coordinatesBenjamin = LatLng(9.970731625023669, -84.12848131795988)
        val coordinatesRoyo = LatLng (9.998414139789045, -84.11084207467582)
        val coordinatesAgrarias = LatLng(9.999920900811967, -84.10862571264134)
        val coordinatesBiologia = LatLng(10.000689977258341, -84.10958409099128)
        val coordinatesCide = LatLng(10.000291887862609, -84.10648387806322)
        val benjaminMarket = MarkerOptions().position(coordinatesBenjamin).title("Soda Campus Benjamín Nuñez")
        val royoMarket = MarkerOptions().position(coordinatesRoyo).title("Soda Padre Royo")
        val agrariasMarket = MarkerOptions().position(coordinatesAgrarias).title("Soda Agrarias")
        val biologiaMarket = MarkerOptions().position(coordinatesBiologia).title("Soda de Biología")
        val cideMarket = MarkerOptions().position(coordinatesCide).title("Soda del CIDE")
        benjaminMarket.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_red_round))
        royoMarket.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_red_round))
        agrariasMarket.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_red_round))
        biologiaMarket.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_red_round))
        cideMarket.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo_red_round))
        map.addMarker(benjaminMarket)
        map.addMarker(royoMarket)
        map.addMarker(agrariasMarket)
        map.addMarker(biologiaMarket)
        map.addMarker(cideMarket)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinatesBenjamin,17f),
            4000,
            null
        )
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if(!::map.isInitialized) return
        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled = true
        }else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        ){
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    this,
                    "Para activar la localización ve a ajustes y acepta los permisos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled = false
            Toast.makeText(
                this,
                "Para activar la localización ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(
            this,
            "Cargando ubicación actual",
            Toast.LENGTH_SHORT
        ).show()

        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(
            this,
            "Ubicación actual: ${p0.latitude}, ${p0.longitude}",
            Toast.LENGTH_SHORT
        ).show()

        deliveryMarker.remove()
    }


    override fun onMapClick(p0: LatLng) {
        if(this.flagMarker == true){
            deliveryMarker.remove()
        }
        /*Toast.makeText(
            this,
            "Mi posición: ${p0.latitude} , ${p0.longitude}",
            Toast.LENGTH_SHORT
        ).show()*/

        val markerOptions = MarkerOptions().position(p0).draggable(true)
        val titleStr = "Punto de entrega"
        markerOptions.title(titleStr)
        deliveryMarker = map.addMarker(markerOptions)
        this.flagMarker = true
        val buttonConfirm = findViewById<Button>(R.id.buttonConfirm)
        buttonConfirm.isVisible = true
    }



}