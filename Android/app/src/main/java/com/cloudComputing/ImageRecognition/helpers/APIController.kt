package com.cloudComputing.ImageRecognition.helpers

import android.util.Log
import com.cloudComputing.ImageRecognition.interfaces.ServiceInterface
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Nimesh on 16-03-2018.
 */
class APIController constructor(serviceInjection: ServiceInterface){
    private val service: ServiceInterface = serviceInjection

    fun getRegistry(completionHandler: (response: JSONObject?) -> Unit) {
        //val containerName = "frozen"
        val path = "v1/catalog/service/Image_recognition"
        val params = JSONArray()
        Log.d("req", "here")
        service.get(path, params, completionHandler)
    }

    fun getDocker(serviceAddress: String,servicePort: String, imagetobase64: String,rank: Int,completionHandler: (response: JSONObject?) -> Unit) {
        val path = "http://$serviceAddress:$servicePort/image_recognition/$imagetobase64/$rank"
        val params = JSONObject()
        service.get(path, params, true,  completionHandler)
    }
}