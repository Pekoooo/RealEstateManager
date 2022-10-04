package com.example.masterdetailflowkotlintest.utils

import kotlin.math.roundToLong

class CurrencyConverter {

 companion object{


   fun convertDollarToEuro(dollars: Int): String{

     val result = (dollars * 0.812).roundToLong()
     return result.toString()

 }





  }


}