package fr.isen.deluc.tierchaser

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ProductListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productArrayList: ArrayList<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fil_actu)

        productRecyclerView = findViewById(R.id.market_list)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)

        productArrayList = arrayListOf<ProductModel>()

        getProductData()
    }

    private fun getProductData() {

        dbref = FirebaseDatabase.getInstance().getReference("Products")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    // boucle sur chaque produit qui sera contenu dans 'productSnapshot'
                    for (productSnapshot in snapshot.children) {

                        // on récupère le produit actuel
                        val product = productSnapshot.getValue(ProductModel::class.java)
                        // on le met dans 'productArrayList'
                        productArrayList.add(product!!)
                    }
                    productRecyclerView.adapter = ProductAdapter(productArrayList)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}
