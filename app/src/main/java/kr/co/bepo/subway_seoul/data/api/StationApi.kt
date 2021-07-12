package kr.co.bepo.subway_seoul.data.api

import kr.co.bepo.subway_seoul.data.db.entity.StationEntity
import kr.co.bepo.subway_seoul.data.db.entity.SubwayEntity

interface StationApi {

    suspend fun getStationDataUpdatedTimeMillis(): Long

    suspend fun getStationSubways(): List<Pair<StationEntity, SubwayEntity>>
}