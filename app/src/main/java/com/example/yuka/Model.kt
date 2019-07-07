package com.example.yuka

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
    fun toProduct(serverResponse: ServerResponse) : Product{
        val product = Product(serverResponse.response?.name.toString()
        ,serverResponse.response?.brands.toString(),
            serverResponse.response?.barcode.toString(),
            serverResponse.response?.nutriScore.toString(),
            serverResponse.response?.picture.toString(),
            serverResponse.response?.quantity.toString(),
            serverResponse.response?.manufacturingCountries,
            serverResponse.response?.ingredients,
            serverResponse.response?.allergens,
            serverResponse.response?.additives,
            300)

        return product
    }
}
interface API {

    @GET("ServerResponses")
    fun getServerResponses() : Call<List<ServerResponse>>

    @GET("ServerResponse")
    fun getServerResponse(@Query("ServerResponseId") id : String)
            : Call<ServerResponse>
}

val api = Retrofit.Builder()
    .baseUrl("https://api.formation-android.fr/getProduct")
    .addConverterFactory(
        GsonConverterFactory.create())
    .build()
    .create(API::class.java)

api.getServerResponses().enqueue(object: Callback<List<ServerResponse>> {
    override fun onResponse(call: Call<List<ServerResponse>>,
                            response: Response<List<ServerResponse>>
    ) {

        val serverResponses = response.body();

    }

    override fun onFailure(call: Call<List<ServerResponse>>,
                           t: Throwable) {
        t.printStackTrace();
    }
});