package com.example.mapas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.location.Location
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.*

class Mapa : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener{


    private lateinit var map: GoogleMap
    private lateinit var mapView: MapView;
    private lateinit var deliveryMarker: Marker
    private var marcador: Boolean = false

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapa, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
       // map.setOnMyLocationButtonClickListener(this)
       // map.setOnMyLocationClickListener (this)
        createMarkers()
        //enableLocation()



        //map.setOnMapClickListener(this)
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

    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun onMapClick(p0: LatLng) {
        TODO("Not yet implemented")
    }
}