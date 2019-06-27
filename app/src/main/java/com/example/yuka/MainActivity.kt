package com.example.yuka

import android.graphics.Typeface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list.*
import kotlinx.android.synthetic.main.product_sheet.*

class MainActivity : AppCompatActivity() {

    val listEmpty = listOf<Product>()

    val productList = listOf(Product(
        "Petits pois et carottes",
        "Cassegrain",
        "Code barres: 3083680085304",
        "A",
        "https://static.openfoodfacts.org/images/products/308/368/008/5304/front_fr.7.400.jpg",
        "400 g (280 g net égoutté)",
        listOf("France", "Japon", "Suisse"),
        listOf("Petits pois 66%", "eau, garniture 2","8% (salade, oignon grelot)", "sucre", "sel", "arôme naturel"),
        listOf("Aucune"),
        listOf("Aucun"),
        234

    ),
        Product(
            "Petits pois et carottes",
            "Cassegrain",
            "Code barres: 3083680085304",
            "A",
            "https://static.openfoodfacts.org/images/products/308/368/008/5304/front_fr.7.400.jpg",
            "400 g (280 g net égoutté)",
            listOf("France", "Japon", "Suisse"),
            listOf("Petits pois 66%", "eau, garniture 2","8% (salade, oignon grelot)", "sucre", "sel", "arôme naturel"),
            listOf("Aucune"),
            listOf("Aucun"),
            234

        ))

    //liste à tester
    var currentList = listEmpty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(currentList.isEmpty()) {
            setContentView(R.layout.list_empty)
        }
        else{
            setContentView(R.layout.list)
            list.adapter = ProductAdapter(currentList)
            list.layoutManager = LinearLayoutManager(this)
        }


        /*
       val viewEmpty : TextView = findViewById(R.id.empty)
       val viewNotEmpty : RecyclerView = findViewById(R.id.list)

        if(listEmpty.isEmpty()){
            viewEmpty.visibility = View.VISIBLE
        }
        else{
            viewEmpty.visibility = View.GONE
        }*/


        /*supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.degrade))


        val barCode = findViewById<TextView>(R.id.barCode)
        barCode.setTitleValue("Code barres",product1.barCode)

        val quantity = findViewById<TextView>(R.id.quantity)
        quantity.setTitleValue("Quantité",product1.quantity)

        val cities = findViewById<TextView>(R.id.cities)
        cities.setTitleValue("Vendu en", product1.cities.joinToString())

        val ingredients = findViewById<TextView>(R.id.ingredients)
        ingredients.setTitleValue("Ingrédients", product1.ingredients.joinToString())

        val allergen = findViewById<TextView>(R.id.allergen)
        allergen.setTitleValue("Substances allergènes", product1.allergen.joinToString())

        val additives = findViewById<TextView>(R.id.additives)
        additives.setTitleValue("Additifs", product1.additives.joinToString())

        val image = findViewById<ImageView>(R.id.image_product_sheet)

        Picasso.get().load("https://static.openfoodfacts.org/images/products/308/368/008/5304/front_fr.7.400.jpg").into(image)

        if(product1.ingredients.isEmpty()){
            getString(R.string.empty_ingredients)
        }

        var nutriscore = "a"
        nutriscore_image.setImageResource(resources.getIdentifier("nutriscore_${nutriscore.toLowerCase()}", "drawable", packageName))*/


    }
    val product1 = Product(
        "Petits pois et carottes",
        "Cassegrain",
        "Code barres: 3083680085304",
        "A",
        "https://static.openfoodfacts.org/images/products/308/368/008/5304/front_fr.7.400.jpg",
        "400 g (280 g net égoutté)",
        listOf("France", "Japon", "Suisse"),
        listOf("Petits pois 66%", "eau, garniture 2","8% (salade, oignon grelot)", "sucre", "sel", "arôme naturel"),
        listOf("Aucune"),
        listOf("Aucun"),
        234

    )

    fun TextView.setTitleValue(title: String, value: String) {
        text = SpannableString("$title: $value").apply {
            setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
    }

}


class Product(
    var name:String,
    var mark:String,
    var barCode:String,
    var nutriscore: String,
    var url:String,
    var quantity: String,
    var cities: List<String>,
    var ingredients: List<String>,
    var allergen: List<String>,
    var additives: List<String>,
    var calories: Int){
}
