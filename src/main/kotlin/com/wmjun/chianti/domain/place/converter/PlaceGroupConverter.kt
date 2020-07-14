package com.wmjun.chianti.domain.place.converter

import com.wmjun.chianti.domain.place.model.PlaceGroup

class PlaceGroupConverter private constructor() {

    companion object {
        fun fromKakaoCategory(categoryGroupCode: String): PlaceGroup =
                when (categoryGroupCode) {
                    "MT1" -> PlaceGroup.SUPER_MARKET
                    "CS2" -> PlaceGroup.CVS
                    "PS3" -> PlaceGroup.KINDERGARTEN
                    "SC4" -> PlaceGroup.SCHOOL
                    "AC5" -> PlaceGroup.ACADEMY
                    "PK6" -> PlaceGroup.PARKING_LOT
                    "OL7" -> PlaceGroup.GAS_STATION
                    "SW8" -> PlaceGroup.SUBWAY_STATION
                    "BK9" -> PlaceGroup.BANK
                    "CT1" -> PlaceGroup.COMMUNITY
                    "AG2" -> PlaceGroup.BROKERAGE
                    "PO3" -> PlaceGroup.PUBLIC_INS
                    "AT4" -> PlaceGroup.LANDMARK
                    "AD5" -> PlaceGroup.ACCOMODATION
                    "FD6" -> PlaceGroup.FOOD
                    "CE7" -> PlaceGroup.CAFFE
                    "HP8" -> PlaceGroup.HOSPITAL
                    "PM9" -> PlaceGroup.PHARMACY
                    else -> PlaceGroup.ETC
                }
    }
}















