package kr.co.bepo.subway_seoul.presentation.stationarrivals

import kr.co.bepo.subway_seoul.domain.ArrivalInformation
import kr.co.bepo.subway_seoul.presentation.BasePresenter
import kr.co.bepo.subway_seoul.presentation.BaseView

interface StationArrivalsContract {

    interface View : BaseView<Presenter> {
        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message: String)

        fun showStationArrivals(arrivalInformation: List<ArrivalInformation>)
    }

    interface Presenter : BasePresenter {
        fun fetchStationArrivals()

        fun toggleStationFavorite()
    }
}