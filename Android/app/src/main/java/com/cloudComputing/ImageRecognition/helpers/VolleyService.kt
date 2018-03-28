package com.cloudComputing.ImageRecognition.helpers

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.cloudComputing.ImageRecognition.imageccApplication
import com.cloudComputing.ImageRecognition.interfaces.ServiceInterface
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Nimesh on 16-03-2018.
 */
class VolleyService : ServiceInterface {
    override fun get(path: String, params: JSONObject, overrideBase: Boolean, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.GET, path, params,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "/get request OK! Response: $response")
                    completionHandler(response)
                },
                Response.ErrorListener { error ->
                    Log.e(TAG, "/get request fail! Error: ${error}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }
        jsonObjReq.retryPolicy = DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        imageccApplication.instance?.addToRequestQueue(jsonObjReq, TAG)

    }

    val TAG = VolleyService::class.java.simpleName
    companion object {
        const val basePath = "http://192.168.43.40:8500/"
    }

    override fun get(path: String, params: JSONArray, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonArrayRequest(Method.GET, basePath + path, params,
                Response.Listener<JSONArray> { response ->
                    Log.d(TAG, "/get request OK! Response: $response")
                    completionHandler(response.get(0) as @kotlin.ParameterName(name = "response") JSONObject)
                },
                Response.ErrorListener { error ->
                    Log.e(TAG, "/get request fail! Error: ${error.message}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }

        imageccApplication.instance?.addToRequestQueue(jsonObjReq, TAG)
    }
}