package com.example.digibuddy.features.recommendations

import android.content.Context
import com.example.digibuddy.core.database.HistoryEntity
import org.json.JSONObject
import java.io.IOException

class RecommendationEngine(private val context: Context) {

    fun getRecommendation(latestHistory: HistoryEntity?): String {
        if (latestHistory == null) {
            return "No data available to generate recommendations."
        }

        val recommendations = mutableListOf<String>()

        if (latestHistory.stressLevel == "High") {
            recommendations.add("Your stress level seems high. It's a good idea to talk to a healthcare professional.")
        }

        try {
            val rulesJson = loadRulesJson()
            val rulesArray = JSONObject(rulesJson).getJSONArray("rules")

            for (i in 0 until rulesArray.length()) {
                val rule = rulesArray.getJSONObject(i)
                val feature = rule.getString("feature")
                val idealValue = rule.getDouble("ideal_value")
                val adviceIfLow = rule.getString("advice_if_low")
                val adviceIfHigh = rule.getString("advice_if_high")

                val actualValue = when (feature) {
                    "sleep_hours" -> latestHistory.sleep_hours.toDouble()
                    "device_hours_per_day" -> latestHistory.device_hours_per_day.toDouble()
                    "notifications_per_day" -> latestHistory.notifications_per_day.toDouble()
                    "phone_unlocks" -> latestHistory.phone_unlocks.toDouble()
                    "sleep_quality" -> latestHistory.sleep_quality.toDouble()
                    else -> null
                }

                if (actualValue != null) {
                    if (actualValue < idealValue) {
                        recommendations.add(adviceIfLow)
                    } else {
                        recommendations.add(adviceIfHigh)
                    }
                }
            }
        } catch (e: IOException) {
            // Could not load rules
        } catch (e: org.json.JSONException) {
            // JSON is malformed
        }

        if (recommendations.isEmpty()) {
            return "Your stress level is low. Keep up the good habits!"
        }

        return "Here are some recommendations based on your recent activity:\n- " + recommendations.joinToString("\n- ")
    }

    private fun loadRulesJson(): String {
        return context.assets.open("rules.json").bufferedReader().use { it.readText() }
    }
}
