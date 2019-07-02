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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_sheet.*
import kotlinx.android.synthetic.main.product_sheet.view.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment)

        val product1 = intent.getParcelableExtra<Product>("product")

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment1, NutritionFragment(product1))
            .commit()


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

class NutritionFragment(product:Product) : Fragment(){
    var product = product
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var layout = inflater.inflate(R.layout.product_sheet, container, false)
        layout.barCode.text = product.barCode
        layout.quantity.text = product.quantity
        layout.cities.text = product.cities.joinToString()
        layout.ingredients.text = product.ingredients.joinToString()
        layout.allergen.text = product.allergen.joinToString()
        layout.additives.text = product.additives.joinToString()

        Picasso.get().load("https://static.openfoodfacts.org/images/products/308/368/008/5304/front_fr.7.400.jpg").into(layout.image_product_sheet)

        var nutriscore = "a"
        layout.nutriscore_image.setImageResource(resources.getIdentifier("nutriscore_${nutriscore.toLowerCase()}", "drawable",  getActivity()?.getPackageName()))




    return layout
    }

}