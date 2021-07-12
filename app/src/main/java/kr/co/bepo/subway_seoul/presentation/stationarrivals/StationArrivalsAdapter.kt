package kr.co.bepo.subway_seoul.presentation.stationarrivals

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.bepo.subway_seoul.databinding.ItemArrivalBinding
import kr.co.bepo.subway_seoul.domain.ArrivalInformation


class StationArrivalsAdapter : RecyclerView.Adapter<StationArrivalsAdapter.ViewHolder>() {

    var data: List<ArrivalInformation> = emptyList()

    inner class ViewHolder(
        private val binding: ItemArrivalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(arrival: ArrivalInformation) = with(binding) {
            labelTextView.badgeColor = arrival.subway.color
            labelTextView.text = "${arrival.subway.label} - ${arrival.destination}"
            destinationTextView.text = "🚩 ${arrival.destination}"
            arrivalMessageTextView.text = arrival.message
            arrivalMessageTextView.setTextColor(if (arrival.message.contains("당역")) Color.RED else Color.DKGRAY)
            updatedTimeTextView.text = "측정 시간: ${arrival.updateAt}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemArrivalBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position])


    override fun getItemCount(): Int = data.size
}