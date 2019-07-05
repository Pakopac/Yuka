package com.example.yuka

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.second_activity.*
import kotlinx.android.synthetic.main.product_sheet.*
import kotlinx.android.synthetic.main.product_sheet.view.*
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.second_activity.*



class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        tabs.setupWithViewPager(viewpager)
        setSupportActionBar(toolbar)
        val product1 = intent.getParcelableExtra<Product>("product")
        viewpager.adapter = ProductDetailsAdapter(supportFragmentManager, product1)



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
        barCode.text = product?.barCode
        quantity.text = product?.quantity
        cities.text = product?.cities?.joinToString()
        ingredients.text = product?.ingredients?.joinToString()
        allergen.text = product?.allergen?.joinToString()
        additives.text = product?.additives?.joinToString()

        Picasso.get().load("https://static.openfoodfacts.org/images/products/308/368/008/5304/front_fr.7.400.jpg").into(image_product_sheet)


       /*var nutriscore = "a"
       nutriscore_image.setImageResource(resources.getIdentifier("nutriscore_${nutriscore.toLowerCase()}", "drawable", getActivity?.getPackage))*/
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