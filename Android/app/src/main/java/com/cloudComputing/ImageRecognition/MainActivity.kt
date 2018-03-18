package com.cloudComputing.ImageRecognition

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.PrintWriter

class MainActivity : AppCompatActivity() {
    internal val REQUEST_IMAGE_CAPTURE = 1
    private val MY_CAMERA_REQUEST_CODE = 100
    private lateinit var mCurrentPhotoPath: String

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                ex.printStackTrace()
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    /*fun <T : View> Activity.bind(@IdRes res : Int) : T {
        @Suppress("UNCHECKED_CAST")
        return findViewById(res) as T
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = Click
        button.setOnClickListener { dispatchTakePictureIntent() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageFileName = "tmpimage.jpg"
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image: File
            try {
                image = File(
                        storageDir,
                        imageFileName)
                Log.d("boo", image.absolutePath)
                val imageBitmap = BitmapFactory.decodeFile(image.absolutePath)
//                val inputStream = FileInputStream(image.absolutePath)//You can get an inputStream using any IO API
//                val bytes: ByteArray
//                val buffer = ByteArray(8192)
//                var bytesRead: Int
//                val output = ByteArrayOutputStream()
//                try {
//                    bytesRead = inputStream.read(buffer)
//                    while (bytesRead != -1) {
//                        output.write(buffer, 0, bytesRead)
//                        bytesRead = inputStream.read(buffer)
//                    }
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//
//                bytes = output.toByteArray()
//                var encodedString = Base64.encodeToString(bytes, Base64.NO_WRAP)

                val baos = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos) //bm is the bitmap object
                val b = baos.toByteArray()
                var encodedString = Base64.encodeToString(b, Base64.NO_WRAP)
                encodedString = encodedString.replace('/', '_')
                encodedString = encodedString.replace('+','-')
                mImageView.setImageBitmap(imageBitmap)
                apiController.getRegistry(){ response ->
                    serviceAddress = response.get(serviceAddress)
                    servicePort = Response.get(servicePort)
                    apiController.getDocker(serviceAddress: serviceAddress,servicePort:servicePort,imagetobase64: encodedString, rank: 2){ response ->
                    Log.d(TAG, "Response": $response)
                }
                }
//                val out = PrintWriter(File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "tmpimage.txt").absolutePath)
//                out.print(encodedString)
//                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        //val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "tmpimage.jpg"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File(
                storageDir,
                imageFileName)

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }
}
