package com.example.yuka

import android.content.res.ColorStateList
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
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.second_activity.*
import kotlinx.android.synthetic.main.product_sheet.*
import androidx.fragment.app.FragmentManager
import com.example.yuka.network.NetworkRequest.getAPI
import com.example.yuka.network.ServerResponse
import com.example.yuka.network.toProduct
import kotlinx.android.synthetic.main.infos.*
import kotlinx.android.synthetic.main.nutrition.*
import kotlinx.android.synthetic.main.nutrition.fat
import kotlinx.android.synthetic.main.nutrition.salt
import kotlinx.android.synthetic.main.nutrition.sugar
import kotlinx.android.synthetic.main.nutrition.view.*
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

                val getProducts = toProduct(response?.body())
                Log.d("abcde", toProduct(response.body()).toString())
                viewpager.adapter = ProductDetailsAdapter(supportFragmentManager, getProducts!!)

                circle_fat.setCircleColor(getProducts?.nutrition?.fat?.quantityFor100g!!.toFloat(),3.toFloat(),20.toFloat())
                circle_fattyAcid.setCircleColor(getProducts?.nutrition?.saturatedFattyAcid?.quantityFor100g!!.toFloat(),1.5.toFloat(),5.toFloat())
                circle_sugar.setCircleColor(getProducts?.nutrition?.sugar?.quantityFor100g!!.toFloat(),5.toFloat(),12.5.toFloat())
                circle_salt.setCircleColor(getProducts?.nutrition?.salt?.quantityFor100g!!.toFloat(),0.3.toFloat(),1.5.toFloat())
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
    companion object {
        fun newInstance(product: Product) : NutritionFragment {
            val fragment = NutritionFragment()
            val args = Bundle()
            args.putParcelable("product", product)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.nutrition,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val product = arguments?.getParcelable<Product>("product")
        super.onViewCreated(view, savedInstanceState)

        fat?.setNutritionText(product?.nutrition?.fat?.quantityFor100g,product?.nutrition?.fat?.unit, resources.getString(R.string.nutrition_Fat))
        fatty_acide?.setNutritionText(product?.nutrition?.saturatedFattyAcid?.quantityFor100g,product?.nutrition?.saturatedFattyAcid?.unit, resources.getString(R.string.nutrition_fattyAcid))
        sugar?.setNutritionText(product?.nutrition?.sugar?.quantityFor100g,product?.nutrition?.sugar?.unit, resources.getString(R.string.nutrition_sugar))
        salt?.setNutritionText(product?.nutrition?.salt?.quantityFor100g,product?.nutrition?.salt?.unit, resources.getString(R.string.nutrition_salt))

    }



}

class InfosFragment() : Fragment(){
    companion object {
        fun newInstance(product: Product) : InfosFragment {
            val fragment = InfosFragment()
            val args = Bundle()
            args.putParcelable("product", product)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.infos,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val product = arguments?.getParcelable<Product>("product")
        super.onViewCreated(view, savedInstanceState)

        energy_value100g.setInfosText(product?.nutrition?.energy?.quantityFor100g,product?.nutrition?.energy?.unit)
        energy_valuePart.setInfosText(product?.nutrition?.energy?.quantityPerPortion,product?.nutrition?.energy?.unit)

        fat_value100g.setInfosText(product?.nutrition?.fat?.quantityFor100g,product?.nutrition?.fat?.unit)
        fat_valuePart.setInfosText(product?.nutrition?.fat?.quantityPerPortion,product?.nutrition?.fat?.unit)

        saturedFatty_value100g.setInfosText(product?.nutrition?.saturatedFattyAcid?.quantityFor100g,product?.nutrition?.saturatedFattyAcid?.unit)
        saturedFatty_valuePart.setInfosText(product?.nutrition?.saturatedFattyAcid?.quantityPerPortion,product?.nutrition?.saturatedFattyAcid?.unit)

        carbohydrate_value100g.setInfosText(product?.nutrition?.carbohydrates?.quantityFor100g,product?.nutrition?.carbohydrates?.unit)
        carbohydrate_valuePart.setInfosText(product?.nutrition?.carbohydrates?.quantityPerPortion,product?.nutrition?.carbohydrates?.unit)

        sugar_value100g.setInfosText(product?.nutrition?.sugar?.quantityFor100g,product?.nutrition?.sugar?.unit)
        sugar_valuePart.setInfosText(product?.nutrition?.sugar?.quantityPerPortion,product?.nutrition?.sugar?.unit)

        fibers_value100g.setInfosText(product?.nutrition?.fibers?.quantityFor100g,product?.nutrition?.fibers?.unit)
        fibers_valuePart.setInfosText(product?.nutrition?.fibers?.quantityPerPortion,product?.nutrition?.fibers?.unit)

        proteins_value100g.setInfosText(product?.nutrition?.proteins?.quantityFor100g,product?.nutrition?.proteins?.unit)
        proteins_valuePart.setInfosText(product?.nutrition?.proteins?.quantityPerPortion,product?.nutrition?.proteins?.unit)

        salt_value100g.setInfosText(product?.nutrition?.salt?.quantityFor100g,product?.nutrition?.salt?.unit)
        salt_valuePart.setInfosText(product?.nutrition?.salt?.quantityPerPortion,product?.nutrition?.salt?.unit)

        sodium_value100g.setInfosText(product?.nutrition?.sodium?.quantityFor100g,product?.nutrition?.sodium?.unit)
        sodium_valuePart.setInfosText(product?.nutrition?.sodium?.quantityPerPortion,product?.nutrition?.sodium?.unit)

    }



}

fun TextView.setNutritionText(value: String?, unit: String?, element: String) {
    text = SpannableString("$value$unit $element").apply {
        setSpan(StyleSpan(Typeface.NORMAL), 0, element.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }
}

fun TextView.setInfosText(value: String?, unit: String?) {
    text = SpannableString("$value$unit").apply {
        setSpan(StyleSpan(Typeface.NORMAL), 0, value!!.length + unit!!.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }

}

fun View.setCircleColor(value:Float, min:Float , max:Float) {
    var color:Int = R.color.nutrient_level_low

        if(value < min){
            color = R.color.nutrient_level_low
        }
        else if(min < value && value < max){
            color = R.color.nutrient_level_moderate
        }
        else if(value > max){
            color = R.color.nutrient_level_high
        }

    DrawableCompat.setTintList(background, ColorStateList.valueOf(resources.getColor(color)))
}

class ProductDetailsAdapter(fm: FragmentManager, product: Product) : FragmentPagerAdapter(fm) {
    val product = product
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SheetFragment.newInstance(product)
            1 -> NutritionFragment.newInstance(product)
            2 -> InfosFragment.newInstance(product)
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
