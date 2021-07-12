package kr.co.bepo.subway_seoul.data.db.entity.mapper

import kr.co.bepo.subway_seoul.data.api.response.RealtimeArrival
import kr.co.bepo.subway_seoul.domain.ArrivalInformation
import kr.co.bepo.subway_seoul.domain.Subway
import java.text.SimpleDateFormat
import java.util.*

private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.'0'", Locale.KOREA)
private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA)

private const val INVALID_FIELD = "-"

fun RealtimeArrival.toArrivalInformation(): ArrivalInformation =
    ArrivalInformation(
        subway = Subway.findById(subwayId),
        direction = trainLineNm?.split("-")
            ?.get(1)
            ?.trim()
            ?: INVALID_FIELD,
        destination = bstatnNm ?: INVALID_FIELD,
        message = arvlMsg2
            ?.replace(statnNm.toString(), "당역")
            ?.replace("[\\[\\]]".toRegex(), "")
            ?: INVALID_FIELD,
        updateAt = recptnDt
            ?.let { apiDateFormat.parse(it) }
            ?.let { dateFormat.format(it) }
            ?: INVALID_FIELD
    )

fun List<RealtimeArrival>.toArrivalInformation(): List<ArrivalInformation> =
    map { it.toArrivalInformation() }
