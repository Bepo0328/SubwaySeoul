package kr.co.bepo.subway_seoul.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT
}