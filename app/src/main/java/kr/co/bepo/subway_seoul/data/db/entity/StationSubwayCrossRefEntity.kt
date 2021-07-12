package kr.co.bepo.subway_seoul.data.db.entity

import androidx.room.Entity
import androidx.room.Index

// Cross Reference
@Entity(primaryKeys = ["stationName", "subwayId"])
data class StationSubwayCrossRefEntity(
    val stationName: String,
    val subwayId: Int
)
