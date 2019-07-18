package com.example.yuka

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.list.*
import kotlin.system.exitProcess

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.barcode_icon -> {
                scan()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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


        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.degrade))
        supportActionBar?.setTitle(getString(R.string.my_products))

    }
  override fun onClick(product: Product) {
      /*val intent = Intent(this,SecondActivity::class.java)
      intent.putExtra("product", product1)
      startActivity(intent)*/
  }
    fun scan() {
        val intent = Intent("com.google.zxing.client.android.SCAN")
        intent.putExtra("SCAN_FORMATS ", "EAN_13")
        startActivityForResult(intent,0)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        val intent = Intent(this,SecondActivity::class.java)
        if(data != null && data.extras.containsKey("SCAN_RESULT")) {
            intent.putExtra("barcode", data?.getStringExtra("SCAN_RESULT").toString())
            intent.putExtra("product", product1)
            startActivity(intent)
        }
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
    var cities: List<String?>?,
    var ingredients: List<String?>?,
    var allergen: List<String?>?,
    var additives: List<String?>?,
    var calories: Int) : Parcelable {
}
