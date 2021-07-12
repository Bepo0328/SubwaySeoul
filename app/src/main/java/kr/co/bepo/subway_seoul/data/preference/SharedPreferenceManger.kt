package kr.co.bepo.subway_seoul.data.preference

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferenceManger(
    private val sharedPreferences: SharedPreferences
): PreferenceManager {

    companion object {
        private const val INVALID_LONG_VALUE = Long.MIN_VALUE
    }

    override fun getLong(key: String): Long? {
        val value = sharedPreferences.getLong(key, INVALID_LONG_VALUE)

        return if (value == INVALID_LONG_VALUE) {
            null
        } else {
            value
        }
    }

    override fun putLong(key: String, value: Long) =
        sharedPreferences.edit { putLong(key, value) }

}