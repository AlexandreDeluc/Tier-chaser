package fr.isen.deluc.tierchaser

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.isen.deluc.tierchaser.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

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

        setNewMarket(mMap)

    }

    private fun goToAnotherActivity() {
        val intent = Intent(this, ShowObjectActivity::class.java)
        startActivity(intent)
    }

    fun showDialog() {
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Souhaitez-vous changer de page?")

        builder.setPositiveButton("Post", DialogInterface.OnClickListener { dialog, which ->
            goToAnotherActivity()
        })
        builder.setNegativeButton(
            "Carte",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }


    private fun setNewMarket(map: GoogleMap){

            val database = Firebase.database("https://tierchaser-default-rtdb.firebaseio.com/").getReference("Markets")

            database.get().addOnSuccessListener {

                for (ds in it.children) {

                    Toast.makeText(this,"La base de donnees a été lue", Toast.LENGTH_SHORT).show()

                            if (it.exists()) {

                                val latitude = ds.child("latitude").getValue(Double::class.java) as Double
                                val longitude = ds.child("longitude").getValue(Double::class.java) as Double
                                val name = ds.child("name").getValue(String::class.java)
                                val cities = LatLng(latitude, longitude)
                                val zoomLevel = 15f // f : float number

                                map.addMarker(MarkerOptions().position(cities).title(name))
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(cities, zoomLevel))


                                mMap.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
                                    showDialog()
                                    false
                                }

                            }

                }
            }
    }
}
