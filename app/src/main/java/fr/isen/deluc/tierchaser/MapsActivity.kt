package fr.isen.deluc.tierchaser

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import fr.isen.deluc.tierchaser.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val TAG = "MapsActivity"
    val db = FirebaseFirestore.getInstance()
    var arMarkets= arrayListOf<Market>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        db.collection("markets")
            .addSnapshotListener { result, e ->
                if(e != null) {
                    Log.w(TAG, "Listen failed", e)
                    return@addSnapshotListener
                }
                mMap.clear()
                arMarkets.clear()
                arMarkets.addAll(result!!.toObjects(Market::class.java))

                for(markets in arMarkets){
                    val geoPosition = LatLng(markets.latitude, markets.longitude)
                    mMap.addMarker(MarkerOptions().position(geoPosition).title(markets.name))
                }
            }

        //Add a marker in Sidney
        /*val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Sidney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)) //permet de centrer la carte sur un point
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20f)) //permet de zoomer*/
    }
}