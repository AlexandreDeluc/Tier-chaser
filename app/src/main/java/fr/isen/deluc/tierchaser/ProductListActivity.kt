package fr.isen.deluc.tierchaser

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProductListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productArrayList: ArrayList<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fil_actu)

        productRecyclerView = findViewById(R.id.market_list)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)

        productArrayList = arrayListOf<ProductModel>()

        getProductData()
    }

    private fun getProductData() {

        // connexion avec la base de données sur la table 'Products'
        dbref = Firebase.database("https://tierchaser-default-rtdb.firebaseio.com/").getReference("Products")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    Toast.makeText(this@ProductListActivity, "snapshot exist", Toast.LENGTH_SHORT).show()
                    // boucle sur chaque produit qui sera contenu dans 'productSnapshot'
                    for (productSnapshot in snapshot.children) {

                        Toast.makeText(this@ProductListActivity, "bdd lu", Toast.LENGTH_SHORT).show()
                        // on récupère le produit actuel
                        val product = productSnapshot.getValue(ProductModel::class.java)
                        // on le met dans 'productArrayList'
                        productArrayList.add(product!!)
                    }
                    productRecyclerView.adapter = ProductAdapter(productArrayList)
                } else {
                    Toast.makeText(this@ProductListActivity, "ERROR", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProductListActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

}
