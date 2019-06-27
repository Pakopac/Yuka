package com.example.yuka

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_cell.view.*
import kotlinx.android.synthetic.main.product_sheet.view.*
import org.w3c.dom.Text

class ProductAdapter(val products: List<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductItemCell(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_cell, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductItemCell).bindProduct(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

}

class ProductItemCell(v: View) : RecyclerView.ViewHolder(v){
    private val name : TextView = v.product_name
    private val image : ImageView = v.product_image
    private val mark : TextView = v.product_mark
    private val nutriscore : TextView = v.product_nutriscore
    private val calorie : TextView = v.product_cal

    fun bindProduct(product: Product){
        name.text = product.name
        Picasso.get().load(product.url).into(image)
        mark.text = product.mark
        nutriscore.text = nutriscore.context.getString(R.string.nutriscore, product.nutriscore)
        calorie.text = calorie.context.getString(R.string.calories, product.calories)
    }
}