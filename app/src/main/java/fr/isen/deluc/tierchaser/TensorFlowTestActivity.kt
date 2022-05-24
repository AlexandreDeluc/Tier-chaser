package fr.isen.deluc.tierchaser

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import fr.isen.deluc.tierchaser.databinding.ActivityTensorFlowTestBinding
import fr.isen.deluc.tierchaser.ml.MobilenetV110224Quant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class TensorFlowTestActivity : AppCompatActivity() {

    lateinit var binding: ActivityTensorFlowTestBinding

    private lateinit var bitmap: Bitmap
    private lateinit var imgView: ImageView
    lateinit var imageUri: Uri

    private lateinit var fileName: TextView

    lateinit var database: DatabaseReference

    lateinit var lien: Button

    private val Image_Capture_Code = 99
    private val OPERATION_CHOOSE_PHOTO = 98

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTensorFlowTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgView = findViewById(R.id.preview)

        lien = findViewById(R.id.linkObject)


        val fileName = "labels.txt"
        val inputString = application.assets.open(fileName).bufferedReader().use { it.readText() }
        val townList = inputString.split("\n")
        val tv:TextView = findViewById(R.id.recognizedObject)

        binding.importPhotoButton.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
        })

        binding.takePhotoButton.setOnClickListener(View.OnClickListener {
            //Start Camera
            val cInt: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cInt, Image_Capture_Code)

        })

        binding.predictButton.setOnClickListener(View.OnClickListener {

            val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val model = MobilenetV110224Quant.newInstance(this)

// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
            val tbuffer = TensorImage.fromBitmap(resized)
            val byteBuffer = tbuffer.buffer
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var max = getMax(outputFeature0.floatArray)

            tv.setText(townList[max])

// Releases model resources if no longer used.
            model.close()

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Uploading file ...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val formatter = SimpleDateFormat("yyyy_MM_dd_MM_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)

            val ref = FirebaseStorage.getInstance().getReference("objectsImage/$fileName")

            ref.putFile(imageUri)
                .addOnSuccessListener {
                    binding.preview.setImageURI(imageUri)
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                }
        })

        binding.linkObject.setOnClickListener(View.OnClickListener {

            val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val model = MobilenetV110224Quant.newInstance(this)

// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
            val tbuffer = TensorImage.fromBitmap(resized)
            val byteBuffer = tbuffer.buffer
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var max = getMax(outputFeature0.floatArray)

            Log.d("HERE", "max : ")
            getLink(max)

        })

    }

    fun getLink(a : Int){
        //Chaussures de course à pied
        if(a == 771) {
            lien.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=chaussure+asics+course&sa=X&biw=1536&bih=714&tbm=shop&sxsrf=ALiCzsZjUEgsJb1liohsYnRlfTB-YNHJsA%3A1653306759054&ei=h3WLYtLJApHClwSKxoKwCg&ved=0ahUKEwiSqc_Qx_X3AhUR4YUKHQqjAKYQ4dUDCAY&uact=5&oq=chaussure+asics+course&gs_lcp=Cgtwcm9kdWN0cy1jYxADMgQIABAYMgYIABAeEBYyBggAEB4QFjIKCAAQHhAPEBYQGDIICAAQHhAWEBgyCAgAEB4QFhAYMggIABAeEBYQGDIICAAQHhAWEBgyCggAEB4QDxAWEBgyCAgAEB4QFhAYOgcIIxDqAhAnOgQIABBDOgsIABCABBCxAxCDAToECAAQAzoFCAAQgAQ6CggAELEDEIMBEENKBAhBGABQnw5YsDBguDFoAXAAeACAAZMBiAGWE5IBBDIuMTmYAQCgAQGwAQjAAQE&sclient=products-cc"))
                startActivity(i)
            }
        }
        //Ballon de rugby
        if(a == 769) {
            lien.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=ballon+de+rugby&sxsrf=ALiCzsa1AP5I2qH3T0tNVs2NKYx-bRYUyQ:1653305962972&source=lnms&tbm=shop&sa=X&ved=2ahUKEwiS1oLVxPX3AhU4QkEAHRfJDCQQ_AUoAnoECAEQBA&biw=1536&bih=714&dpr=1.25"))
                startActivity(i)
            }
        }
        //Ballon de foot
        if(a == 806) {
            lien.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=ballon+de+foot+fff&sxsrf=ALiCzsZGsPyIi9vqWZhP39ijdKNHA4mKOQ:1653306756888&source=lnms&tbm=shop&sa=X&ved=2ahUKEwjPucvPx_X3AhUCixoKHSMGA1oQ_AUoAXoECAEQAw&biw=1536&bih=714&dpr=1.25"))
                startActivity(i)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                val bp = data?.extras!!["data"] as Bitmap?
                if(bp != null){
                    binding.preview.background = null
                    binding.preview.setImageBitmap(bp)
                    binding.noPhotoText.visibility = View.INVISIBLE
                    imageUri=data?.data!!
                    binding.preview.setImageURI(imageUri)
                }
            }
        }else if(requestCode == OPERATION_CHOOSE_PHOTO){
            if (resultCode == RESULT_OK) {
                val imgView = data?.data
                binding.preview.setImageURI(imgView)
                var uri: Uri?= data?.data
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                binding.noPhotoText.visibility = View.INVISIBLE
                imageUri=data?.data!!
                binding.preview.setImageURI(imageUri)
            }
        }
    }

    private fun getMax(arr:FloatArray) : Int{
        var ind = 0
        var min = 0.0f

        for (i in 0..1000){

            if (arr[i]>min)
            {
                ind = i
                min = arr[i]
            }
        }
        return ind
    }
}