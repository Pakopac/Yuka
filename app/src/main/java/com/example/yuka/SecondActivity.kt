package com.example.yuka

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.second_activity.*
import kotlinx.android.synthetic.main.product_sheet.*
import androidx.fragment.app.FragmentManager
import com.example.yuka.network.NetworkRequest.getAPI
import com.example.yuka.network.ServerResponse
import kotlinx.android.synthetic.main.product_sheet.view.*
import retrofit2.Call
import retrofit2.Response


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        tabs.setupWithViewPager(viewpager)
        setSupportActionBar(toolbar)
        val product1 = intent.getParcelableExtra<Product>("product")
        val barCode = intent.getStringExtra("barcode")

        //val product = getAPI(intent.getStringExtra("barcode"))
        //viewpager.adapter = ProductDetailsAdapter(supportFragmentManager, product)

            getAPI(barCode, object : retrofit2.Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>,
                                    response: Response<ServerResponse>
            ) {

                val getProducts = response.body()?.toProduct(response.body())
                Log.d("abcde",response.body()?.toProduct(response.body()).toString())
                viewpager.adapter = ProductDetailsAdapter(supportFragmentManager, getProducts!!)
            }

            override fun onFailure(call: Call<ServerResponse>,
                                   t: Throwable) {
                t.printStackTrace();
            }
        })

        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.degrade))
        supportActionBar?.setTitle(getString(R.string.details))


        /*var nutriscore = "a"
        nutriscore_image.setImageResource(resources.getIdentifier("nutriscore_${nutriscore.toLowerCase()}", "drawable", packageName))*/

        /*val first_circle: View = findViewById(R.id.first_circle)

        DrawableCompat.setTintList(first_circle.background, ColorStateList.valueOf(ContextCompat.getColor(this,(R.color.nutrient_level_low))))*/

    }
    fun TextView.setTitleValue(title: String, value: String) {
        text = SpannableString("$title: $value").apply {
            setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
    }

}


class SheetFragment() : Fragment(){
    companion object {
        fun newInstance(product: Product) : SheetFragment {
            val fragment = SheetFragment()
            val args = Bundle()
            args.putParcelable("product", product)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


       // Picasso.get().load("https://static.openfoodfacts.org/images/products/308/368/008/5304/front_fr.7.400.jpg").into(layout.image_product_sheet)

        return inflater.inflate(R.layout.product_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val product = arguments?.getParcelable<Product>("product")
        super.onViewCreated(view, savedInstanceState)
        title.text = product?.name
        mark.text = product?.mark
        barCode.setTitleValue( resources.getString(R.string.barCode_title),product?.barCode)
        quantity.setTitleValue( resources.getString(R.string.quantity_title),product?.quantity)
        cities.setTitleValue( resources.getString(R.string.cities_title),product?.cities?.joinToString())
        ingredients.setTitleValue( resources.getString(R.string.Ingredients_title),product?.ingredients?.joinToString())
        allergen.setTitleValue( resources.getString(R.string.allergens_title),product?.allergen?.joinToString())
        additives.setTitleValue( resources.getString(R.string.additives_title),product?.additives?.joinToString())

        Picasso.get().load(product?.url).into(image_product_sheet)
        nutriscore_image.setImageResource(resources.getIdentifier("nutriscore_${product?.nutriscore?.toLowerCase()}", "drawable", view.context.packageName))
    }



}

fun TextView.setTitleValue(title: String, value: String?) {
    text = SpannableString("$title: $value").apply {
        setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }
}

class NutritionFragment() : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.nutrition,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }



}

class ProductDetailsAdapter(fm: FragmentManager, product: Product) : FragmentPagerAdapter(fm) {
    val product = product
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SheetFragment.newInstance(product)
            1 -> NutritionFragment()
            2 -> NutritionFragment()
            else -> throw Exception("Unknown position")
        }
    }

    // Le nombre d'onglets à afficher
    override fun getCount(): Int = 3

    // Le titre à afficher dans l'onglet selon la position
    // TODO Utiliser des ressources
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Fiche"
            1 -> "Nutrition"
            2 -> "Infos nutritionnelles"
            else -> throw Exception("Unknown position")
        }
    }
}
