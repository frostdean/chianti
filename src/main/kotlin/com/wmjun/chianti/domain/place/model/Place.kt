package com.wmjun.chianti.domain.place.model

class Place(
        val name: String,
        val group: PlaceGroup,
        val phone: String,
        val address: String,
        val roadAddress: String,
        val coordinates: Coordinates,
        val externalMapUrl: String
)