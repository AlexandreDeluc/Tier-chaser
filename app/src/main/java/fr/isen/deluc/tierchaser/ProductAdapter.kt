package fr.isen.deluc.tierchaser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private val productList : ArrayList<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item,
        parent, false)
        return ProductViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val currentItem = productList[position]

        holder.address.text = currentItem.address
        holder.city.text = currentItem.city
        holder.date.text = currentItem.date
        holder.image.text = currentItem.imageUrl
        Glide.with(holder.itemView).load(currentItem).into(holder.itemView.findViewById(R.id.logoLocation))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val address : TextView = itemView.findViewById(R.id.address)
        val city : TextView = itemView.findViewById(R.id.city)
        val date : TextView = itemView.findViewById(R.id.date)
        val image : TextView = itemView.findViewById(R.id.productImage)

    }

}