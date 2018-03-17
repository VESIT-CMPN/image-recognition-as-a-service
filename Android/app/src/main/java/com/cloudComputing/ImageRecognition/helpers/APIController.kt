package com.cloudComputing.ImageRecognition.helpers

import com.cloudComputing.ImageRecognition.interfaces.ServiceInterface
import org.json.JSONObject

/**
 * Created by Nimesh on 16-03-2018.
 */
class APIController constructor(serviceInjection: ServiceInterface){
    class APIController constructor(serviceInjection: ServiceInterface) {
        private val service: ServiceInterface = serviceInjection

        fun getFileInfo(filename: String, access_token: String, completionHandler: (response: JSONObject?) -> Unit) {
            val containerName = "frozen"
            val path = "TensorFlowExports/$containerName/files/$filename?access_token=$access_token"
            val params = JSONObject()
            service.get(path, params, completionHandler)
        }
    }
}