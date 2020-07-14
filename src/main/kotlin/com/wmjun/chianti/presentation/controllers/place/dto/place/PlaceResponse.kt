package com.wmjun.chianti.presentation.controllers.place.dto.place

import com.wmjun.chianti.domain.place.model.Place

data class PlaceResponse(
        val placeName: String,
        val placeGroupCode: String,
        val placeGroupName: String,
        val phone: String,
        val address: String,
        val roadAddress: String,
        val latitude: Double,
        val longitude: Double,
        val externalMapUrl: String
) {

    companion object {
        fun fromPlace(place: Place) = PlaceResponse(
                placeName = place.name,
                placeGroupCode = place.group.name,
                placeGroupName = place.group.description,
                phone = place.phone,
                address = place.address,
                roadAddress = place.roadAddress,
                latitude = place.coordinates.latitude,
                longitude = place.coordinates.longitude,
                externalMapUrl = place.externalMapUrl
        )
    }
}