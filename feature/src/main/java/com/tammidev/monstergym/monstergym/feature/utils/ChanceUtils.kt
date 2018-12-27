package com.tammidev.monstergym.monstergym.feature.utils

import android.util.Log

open class ChanceUtils {

    companion object {

        /**
         * Start and end are included.
         */
        fun randomNumberBetween(min: Float, max: Float): Double {
            return Math.floor(Math.random() * (max - min + 1) + min)
        }

        /**
         * Example: 50% chance to dodge. As param use 50. Result will be either true, for dodged, or false.
         *
         * @param percentage The chance that the outcome is true.
         * @return whether the outcome is true or not.
         *
         */
        fun chance(percentage: Double): Boolean {
            if (percentage < 0 || percentage > 100)
                throw Exception("ChanceUtils - chance(percentage: Double) \n Percentage param given is either lower than 0 or higher than 100")

            val number = randomNumberBetween(0f, 99f)
            Log.d("Debug", "chance number: $number")
            return number < percentage
        }
    }
}