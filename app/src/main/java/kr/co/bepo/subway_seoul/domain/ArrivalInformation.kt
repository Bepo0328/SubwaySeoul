package kr.co.bepo.subway_seoul.domain

data class ArrivalInformation(
    val subway: Subway,
    val direction: String,
    val message: String,
    val destination: String,
    val updateAt: String
)