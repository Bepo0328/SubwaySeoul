package kr.co.bepo.subway_seoul.data.db.entity.mapper

import kr.co.bepo.subway_seoul.data.db.entity.StationEntity
import kr.co.bepo.subway_seoul.data.db.entity.StationWithSubwaysEntity
import kr.co.bepo.subway_seoul.data.db.entity.SubwayEntity
import kr.co.bepo.subway_seoul.domain.Station
import kr.co.bepo.subway_seoul.domain.Subway

fun StationWithSubwaysEntity.toStation() =
    Station(
        name = station.stationName,
        isFavorited = station.isFavorited,
        connectedSubways = subways.toSubways()
    )

fun Station.toStationEntity() =
    StationEntity(
        stationName = name,
        isFavorited = isFavorited
    )

fun List<StationWithSubwaysEntity>.toStations() = map { it.toStation() }

fun List<SubwayEntity>.toSubways(): List<Subway> = map { Subway.findById(it.subwayId) }