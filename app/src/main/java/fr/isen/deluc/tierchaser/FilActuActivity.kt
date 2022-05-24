package fr.isen.deluc.tierchaser

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fr.isen.deluc.tierchaser.adapter.ImageAdapter
import fr.isen.deluc.tierchaser.databinding.ActivityFilActuBinding

class FilActuActivity : AppCompatActivity() {

    lateinit var binding: ActivityFilActuBinding

    var imagelist: ArrayList<String> = ArrayList()
    var root: StorageReference? = null
    var adapter: ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilActuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val listRef = FirebaseStorage.getInstance().reference.child("objectsImage/")
        listRef.listAll().addOnSuccessListener { listResult ->
            for (file in listResult.items) {
                file.downloadUrl.addOnSuccessListener { uri ->
                    imagelist.add(uri.toString())
                    Log.e("Itemvalue", uri.toString())
                }.addOnSuccessListener {
                    binding.filactuRecyclerView.layoutManager = LinearLayoutManager(this)
                    binding.filactuRecyclerView.adapter = ImageAdapter(imagelist)
                }
            }
        }

    }
}