package com.example.meliexample.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val title: String,
    val condition: String,
    @SerializedName("thumbnail_id") val thumbnailId: String,
    @SerializedName("catalog_product_id") val catalogProductId: String,
    @SerializedName("listing_type_id") val listingTypeId: String,
    val permalink: String,
    @SerializedName("buying_mode") val buyingMode: String,
    @SerializedName("site_id") val siteId: String,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("domain_id") val domainId: String,
    val thumbnail: String,
    @SerializedName("currency_id") val currencyId: String,
    @SerializedName("order_backend") val orderBackend: Int,
    val price: Double,
    @SerializedName("original_price") val originalPrice: Double,
    @SerializedName("sale_price") val salePrice: Double?,
    @SerializedName("sold_quantity") val soldQuantity: Int,
    @SerializedName("available_quantity") val availableQuantity: Int,
    @SerializedName("official_store_id") val officialStoreId: String,
    @SerializedName("use_thumbnail_id") val useThumbnailId: Boolean,
    @SerializedName("accepts_mercadopago") val acceptsMercadoPago: Boolean,
    val tags: List<String>,
    val shipping: Shipping?,
    @SerializedName("stop_time") val stopTime: String,
    @SerializedName("winner_item_id") val winnerItemId: String,
    @SerializedName("catalog_listing") val catalogListing: Boolean,
    val discounts: String?,
    val promotions: List<String>? = emptyList(),
    @SerializedName("inventory_id") val inventoryId: String,
    val attributes: List<Attribute> = emptyList(),
    val pictures: List<Picture> = emptyList(),
) : Parcelable
