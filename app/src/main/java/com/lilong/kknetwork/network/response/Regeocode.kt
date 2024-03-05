package com.lilong.kknetwork.network.response

/**
 * 高德地图返回实体类
 *
 */

data class Regeocode(
    val addressComponent: AddressComponent,
    val formatted_address: String
)

data class AddressComponent(
    val adcode: String,
    val building: Building,
    val businessAreas: List<List<Any>>,
    val city: List<Any>,
    val citycode: String,
    val country: String,
    val district: String,
    val neighborhood: Neighborhood,
    val province: String,
    val streetNumber: StreetNumber,
    val towncode: String,
    val township: String
)

data class Building(
    val name: List<Any>,
    val type: List<Any>
)

data class Neighborhood(
    val name: List<Any>,
    val type: List<Any>
)

data class StreetNumber(
    val direction: String,
    val distance: String,
    val location: String,
    val number: String,
    val street: String
)