package com.cloudComputing.ImageRecognition.interfaces

import org.json.JSONObject

/**
 * Created by Nimesh on 16-03-2018.
 */
interface ServiceInterface {
    //fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
    fun get(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
}