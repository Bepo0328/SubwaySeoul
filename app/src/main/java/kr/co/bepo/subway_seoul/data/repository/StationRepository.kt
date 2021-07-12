package kr.co.bepo.subway_seoul.data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.bepo.subway_seoul.domain.ArrivalInformation
import kr.co.bepo.subway_seoul.domain.Station


interface StationRepository {

    val stations: Flow<List<Station>>

    suspend fun refreshStations()

    suspend fun getStationArrivals(stationName: String): List<ArrivalInformation>

    suspend fun updateStation(station: Station)
}