package fr.isen.deluc.tierchaser

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import fr.isen.deluc.tierchaser.databinding.ActivityTensorFlowTestBinding
import fr.isen.deluc.tierchaser.ml.MobilenetV110224Quant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class TensorFlowTestActivity : AppCompatActivity() {

    private lateinit var bitmap: Bitmap
    private lateinit var imgView: ImageView
    lateinit var binding: ActivityTensorFlowTestBinding

    private val Image_Capture_Code = 99
    private val OPERATION_CHOOSE_PHOTO = 98

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTensorFlowTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgView = findViewById(R.id.preview)

        listenClick()

    }

    private fun listenClick(){

        val fileName = "labels.txt"
        val inputString = application.assets.open(fileName).bufferedReader().use { it.readText() }
        val townList = inputString.split("\n")
        val tv:TextView = findViewById(R.id.textView)

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
        })
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
                }
            }
        }else if(requestCode == OPERATION_CHOOSE_PHOTO){
            if (resultCode == RESULT_OK) {
                imgView.setImageURI(data?.data)
                var uri: Uri?= data?.data
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                binding.noPhotoText.visibility = View.INVISIBLE
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