package com.example.yuka.network

import android.net.Network
import com.example.yuka.NutritionFactsItem
import com.example.yuka.NutritionFacts
import com.example.yuka.Product
import com.google.gson.annotations.SerializedName

data class ServerResponse(
    @SerializedName("error")
    val error: String?,
    @SerializedName("response")
    val response: Response?,
    @SerializedName("nutritionFacts")
    val nutritionFacts: NutritionFacts?
) {
    data class Response(
        @SerializedName("additives")
        val additives: Map<String, String>?,
        @SerializedName("allergens")
        val allergens: List<String?>?,
        @SerializedName("altName")
        val altName: String?,
        @SerializedName("barcode")
        val barcode: String,
        @SerializedName("brands")
        val brands: List<String?>?,
        @SerializedName("containsPalmOil")
        val containsPalmOil: Boolean?,
        @SerializedName("ingredients")
        val ingredients: List<String?>?,
        @SerializedName("manufacturingCountries")
        val manufacturingCountries: List<String?>?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("novaScore")
        val novaScore: String?,
        @SerializedName("nutriScore")
        val nutriScore: String?,
        @SerializedName("nutritionFacts")
        val nutritionFacts: NutritionFacts?,
        @SerializedName("picture")
        val picture: String?,
        @SerializedName("quantity")
        val quantity: String?,
        @SerializedName("traces")
        val traces: List<String?>?
    )

    data class NutritionFacts(
        @SerializedName("calories")
        val calories: NutritionFact?,
        @SerializedName("carbohydrate")
        val carbohydrate: NutritionFact?,
        @SerializedName("energy")
        val energy: NutritionFact?,
        @SerializedName("fat")
        val fat: NutritionFact?,
        @SerializedName("fiber")
        val fiber: NutritionFact?,
        @SerializedName("proteins")
        val proteins: NutritionFact?,
        @SerializedName("salt")
        val salt: NutritionFact?,
        @SerializedName("saturatedFat")
        val saturatedFat: NutritionFact?,
        @SerializedName("servingSize")
        val servingSize: String?,
        @SerializedName("sodium")
        val sodium: NutritionFact?,
        @SerializedName("sugar")
        val sugar: NutritionFact?
    ) {
        data class NutritionFact(
            @SerializedName("per100g")
            val per100g: String?,
            @SerializedName("perServing")
            val perServing: String?,
            @SerializedName("unit")
            val unit: String?
        )
    }
}

fun toProduct(server: ServerResponse?): Product {
    val product = Product(
        server?.response?.name.toString(),
        server?.response?.brands.toString(),
        server?.response?.barcode.toString(),
        server?.response?.nutriScore.toString(),
        server?.response?.picture.toString(),
        server?.response?.quantity.toString(),
        server?.response?.manufacturingCountries,
        server?.response?.ingredients,
        server?.response?.allergens,
        server?.response?.additives?.values?.toList(),
        300,
        toNutritionFacts(server?.response?.nutritionFacts)
    )

    return product
}

fun toNutritionFacts(server: ServerResponse.NutritionFacts?): NutritionFacts {
    return NutritionFacts(
        toNutritionFact(server?.energy),
        toNutritionFact(server?.fat),
        toNutritionFact(server?.saturatedFat),
        toNutritionFact(server?.carbohydrate),
        toNutritionFact(server?.fiber),
        toNutritionFact(server?.sugar),
        toNutritionFact(server?.proteins),
        toNutritionFact(server?.salt),
        toNutritionFact(server?.sodium)

    )
}

fun toNutritionFact(server: ServerResponse.NutritionFacts.NutritionFact?): NutritionFactsItem {
    return NutritionFactsItem(
        server?.unit.toString(),
        server?.perServing.toString(),
        server?.per100g.toString()
    )
}