package kr.co.bepo.subway_seoul.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kr.co.bepo.subway_seoul.data.api.StationApi
import kr.co.bepo.subway_seoul.data.api.StationArrivalsApi
import kr.co.bepo.subway_seoul.data.db.StationDao
import kr.co.bepo.subway_seoul.data.db.entity.StationSubwayCrossRefEntity
import kr.co.bepo.subway_seoul.data.db.entity.mapper.toArrivalInformation
import kr.co.bepo.subway_seoul.data.db.entity.mapper.toStationEntity
import kr.co.bepo.subway_seoul.data.db.entity.mapper.toStations
import kr.co.bepo.subway_seoul.data.preference.PreferenceManager
import kr.co.bepo.subway_seoul.domain.ArrivalInformation
import kr.co.bepo.subway_seoul.domain.Station

class StationRepositoryImpl(
    private val stationArrivalsApi: StationArrivalsApi,
    private val stationApi: StationApi,
    private val stationDao: StationDao,
    private val preferenceManager: PreferenceManager,
    private val dispatcher: CoroutineDispatcher
): StationRepository {

    companion object {
        private const val KEY_LAST_DATABASE_UPDATED_TIME_MILLIS = "KEY_LAST_DATABASE_UPDATED_TIME_MILLIS"
    }

    override val stations: Flow<List<Station>> =
        stationDao.getStationWithSubways()
            .distinctUntilChanged()
            .map { stations -> stations.toStations().sortedByDescending { it.isFavorited } }
            .flowOn(dispatcher)


    override suspend fun refreshStations() = withContext(dispatcher) {
        val fileUpdateTimeMillis = stationApi.getStationDataUpdatedTimeMillis()
        val lastDatabaseUpdatedTimeMillis = preferenceManager.getLong((KEY_LAST_DATABASE_UPDATED_TIME_MILLIS))

        if (lastDatabaseUpdatedTimeMillis == null || fileUpdateTimeMillis > lastDatabaseUpdatedTimeMillis) {
            stationDao.insertStationSubways(stationApi.getStationSubways())
            preferenceManager.putLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS, fileUpdateTimeMillis)
        }
    }

    override suspend fun getStationArrivals(stationName: String): List<ArrivalInformation> = withContext(dispatcher) {
        stationArrivalsApi.getRealtimeStationArrivals(stationName)
            .body()
            ?.realtimeArrivalList
            ?.toArrivalInformation()
            ?.distinctBy { it.direction }
            ?.sortedBy { it.subway }
            ?: throw RuntimeException("도착 정보를 불러오는 데에 실패했습니다.")
    }

    override suspend fun updateStation(station: Station) = withContext(dispatcher) {
        stationDao.updateStation(station.toStationEntity())
    }
}