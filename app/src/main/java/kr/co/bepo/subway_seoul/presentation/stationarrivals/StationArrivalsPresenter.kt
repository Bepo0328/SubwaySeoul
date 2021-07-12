package kr.co.bepo.subway_seoul.presentation.stationarrivals

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kr.co.bepo.subway_seoul.data.repository.StationRepository
import kr.co.bepo.subway_seoul.domain.Station

class StationArrivalsPresenter(
    private val view: StationArrivalsContract.View,
    private val station: Station,
    private val stationRepository: StationRepository
) : StationArrivalsContract.Presenter {

    override val scope = MainScope()

    override fun fetchStationArrivals() {
        scope.launch {
            try {
                view.showLoadingIndicator()
                view.showStationArrivals(stationRepository.getStationArrivals(station.name))
            } catch (e: Exception) {
                e.printStackTrace()
                view.showErrorDescription(e.message ?: "알 수 없는 문제가 발생했어요 \uD83D\uDE22")
            } finally {
                view.hideLoadingIndicator()
            }
        }
    }

    override fun toggleStationFavorite() {
        scope.launch {
            stationRepository.updateStation(station.copy(isFavorited = !station.isFavorited))
        }
    }

    override fun onViewCreated() {
        fetchStationArrivals()
    }

    override fun onDestroyView() {}
}