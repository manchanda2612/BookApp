package com.neeraj.common

import java.lang.Exception

class IOError(val code : Int, message : String?) : Exception(message)
class DataError(message: String?) : Exception(message)
class InternetError(message: String?) : Exception(message)
