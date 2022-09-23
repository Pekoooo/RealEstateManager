package com.example.masterdetailflowkotlintest.utils

class PathConverter {

    ///-1/1/content://media/external/images/media/28/ORIGINAL/NONE/image/jpeg/1429188946 = 81

    companion object {

        fun convertPath(oldPath: String): String {

            var newPath = " "

            when (oldPath.length) {

                81 -> newPath = oldPath.substring(6..45)
                82 -> newPath = oldPath.substring(6..46)
                83 -> newPath = oldPath.substring(6..47)


            }


            return newPath



        }

    }



}