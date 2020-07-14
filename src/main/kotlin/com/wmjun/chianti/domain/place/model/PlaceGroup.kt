package com.wmjun.chianti.domain.place.model

enum class PlaceGroup(val description: String) {

    SUPER_MARKET("대형마트"),
    CVS("편의점"),
    KINDERGARTEN("어린이집/유치원"),
    SCHOOL("학교"),
    ACADEMY("학원"),
    PARKING_LOT("주차장"),
    GAS_STATION("주유소/충전소"),
    SUBWAY_STATION("지하철역"),
    BANK("은행"),
    COMMUNITY("문화시설"),
    BROKERAGE("중개업소"),
    PUBLIC_INS("공공기관"),
    LANDMARK("관광명소"),
    ACCOMODATION("숙박"),
    FOOD("음식점"),
    CAFFE("카페"),
    HOSPITAL("병원"),
    PHARMACY("약국"),
    ETC("기타")
}