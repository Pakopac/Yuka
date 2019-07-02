package com.example.yuka

import android.content.Intent
import android.graphics.Typeface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.list.*
import kotlinx.android.synthetic.main.product_sheet.*

class MainActivity : AppCompatActivity(), ItemListener {


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

    //liste à tester
    var currentList = productList

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(currentList.isEmpty()) {
            setContentView(R.layout.list_empty)
        }
        else{
            setContentView(R.layout.list)
            list.adapter = ProductAdapter(currentList, this)
            list.layoutManager = LinearLayoutManager(this)

        }



        // Ouvrir un nouvel écran
        //val intent = Intent(this,SecondActivity::class.java)
        //startActivity(intent)
        //intent.putExtra("name", "toto")
        //intent.putExtra("product", product1)

        //Fermer l'écran en cours
        //finish()

        /*
       val viewEmpty : TextView = findViewById(R.id.empty)
       val viewNotEmpty : RecyclerView = findViewById(R.id.list)

        if(listEmpty.isEmpty()){
            viewEmpty.visibility = View.VISIBLE
        }
        else{
            viewEmpty.visibility = View.GONE
        }*/


        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.degrade))
        supportActionBar?.setTitle(getString(R.string.my_products))

    }

    fun TextView.setTitleValue(title: String, value: String) {
        text = SpannableString("$title: $value").apply {
            setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
    }
  override fun onClick(product: Product) {
      Log.d("yo",product1.name)
      val intent = Intent(this,SecondActivity::class.java)
      intent.putExtra("product", product1)
      startActivity(intent)

  }

}

@Parcelize
data class Product(
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
    var calories: Int) : Parcelable {
}
