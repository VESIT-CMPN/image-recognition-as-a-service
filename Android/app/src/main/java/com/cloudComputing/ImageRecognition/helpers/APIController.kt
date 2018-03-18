package com.cloudComputing.ImageRecognition.helpers

import com.cloudComputing.ImageRecognition.interfaces.ServiceInterface
import org.json.JSONObject

/**
 * Created by Nimesh on 16-03-2018.
 */
class APIController constructor(serviceInjection: ServiceInterface){
    class APIController constructor(serviceInjection: ServiceInterface) {
        private val service: ServiceInterface = serviceInjection

        fun getRegistry(completionHandler: (response: JSONObject?) -> Unit) {
            //val containerName = "frozen"
            //val path = "TensorFlowExports/$containerName/files/$filename?access_token=$access_token"
            val params = JSONObject()
            service.get(path, params, completionHandler)
        }

        fun getDocker(serviceAddress: String,servicePort: String, imagetobase64: String,rank: Int,completionHandler: (response: JSONObject?) -> Unit) {
            val containerName = "image_recognition"
            val path = "http://$serviceAddress:$servicePort/$containerName/$imagetobase64/$rank"
            val params = JSONObject()
            service.get(path, params, completionHandler)
        }
    }
}