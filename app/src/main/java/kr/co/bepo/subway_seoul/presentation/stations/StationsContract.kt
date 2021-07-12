package kr.co.bepo.subway_seoul.presentation.stations

import kr.co.bepo.subway_seoul.domain.Station
import kr.co.bepo.subway_seoul.presentation.BasePresenter
import kr.co.bepo.subway_seoul.presentation.BaseView

interface StationsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showStations(stations: List<Station>)
    }

    interface Presenter : BasePresenter {

        fun filterStations(query: String)

        fun toggleStationFavorite(station: Station)
    }
}