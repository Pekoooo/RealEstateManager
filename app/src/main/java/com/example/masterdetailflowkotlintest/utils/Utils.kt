package com.example.masterdetailflowkotlintest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Philippe on 21/02/2018.
 */
object Utils {
    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */

    private const val dollarToEuroRate = Constants.DOLLARS_TO_EURO
    private const val euroToDollarRate = Constants.EURO_TO_DOLLARS

    fun convertDollarToEuro(dollars: Int): Int {
        return (dollars * dollarToEuroRate).roundToInt()
    }

    fun convertEuroToDollar(euro: Int): Int {
        return (euro * euroToDollarRate).roundToInt()
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val todayDate: String
        get() {
            return formatter.format(Date())
        }

    fun stringDateToLong(date: String): Long {
        val newDate = SimpleDateFormat("dd/MM/yyyy").parse(date)
        return newDate.time
    }

    fun longToDate(date: Long): String {
        return formatter.format(date)
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    fun isInternetAvailable(context: Context): Boolean {
        /*val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifi.isWifiEnabled*/
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isInternetAvailableBuildVersionCodAboveM(context)
        } else {
            isInternetAvailableBuildVersionBelowM()
        }
    }

    private fun isInternetAvailableBuildVersionBelowM(): Boolean {
        val ping = "/system/bin/ping -c 1 8.8.8.8"
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec(ping)
            val exitValue = ipProcess.waitFor()
            return (exitValue == 0)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    fun isInternetAvailableBuildVersionCodAboveM(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun snackBarMaker(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }
}