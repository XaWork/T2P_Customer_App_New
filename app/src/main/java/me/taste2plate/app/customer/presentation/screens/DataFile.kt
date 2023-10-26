package me.taste2plate.app.customer.presentation.screens

import me.taste2plate.app.customer.R


val ImageItemList = listOf(
    R.drawable.home_city,
    R.drawable.home_brand,
    R.drawable.home_category,
    R.drawable.home_cuisine
)

val productList: List<Product> = listOf(
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
    Product(),
)

data class Product(
    val name: String = "Product Name",
    val brand: String = "Brand Name",
    val image: String = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
    val flatOff : String = "150",
    val rating : String = "4.3",
    val address: String = "Product provider address",
    val delivery : String = "01 Dec, 4PM - 9PM",
    val price : String = "180"
)

val countries = listOf(
    "India"
)