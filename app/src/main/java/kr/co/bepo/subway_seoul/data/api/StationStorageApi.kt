package kr.co.bepo.subway_seoul.data.api

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kr.co.bepo.subway_seoul.data.db.entity.StationEntity
import kr.co.bepo.subway_seoul.data.db.entity.SubwayEntity

class StationStorageApi(
    firebaseStorage: FirebaseStorage
): StationApi {

    companion object {
        private const val STATION_DATA_FILE_NAME = "station_data.csv"
    }

    private val sheetReference = firebaseStorage.reference.child(STATION_DATA_FILE_NAME)

    override suspend fun getStationDataUpdatedTimeMillis(): Long =
        sheetReference.metadata.await().updatedTimeMillis

    override suspend fun getStationSubways(): List<Pair<StationEntity, SubwayEntity>> {
        val downloadSizeBytes = sheetReference.metadata.await().sizeBytes
        val byteArray = sheetReference.getBytes(downloadSizeBytes).await()


        val list = byteArray.decodeToString()
            .lines()
            .drop(1)
            .map { it.split(",") }
            .map { StationEntity(it[1]) to SubwayEntity(it[0].toInt()) }

        Log.d("StationStorageApi", "$list")

        return list
    }
}