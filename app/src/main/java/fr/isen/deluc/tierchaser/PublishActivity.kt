package fr.isen.deluc.tierchaser

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import fr.isen.deluc.tierchaser.databinding.ActivityPublishBinding

class PublishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublishBinding

    val Image_Capture_Code = 99
    val OPERATION_CHOOSE_PHOTO = 98

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPublishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClick()

    }

    fun setOnClick(){
        //Make actions
        binding.importPic.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
        })
        binding.takePic.setOnClickListener(View.OnClickListener { view ->
            //Start Camera
            val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cInt, Image_Capture_Code)
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
                    binding.tv.visibility = View.INVISIBLE
                }
            }
        }else if(requestCode == OPERATION_CHOOSE_PHOTO){
            if (resultCode == RESULT_OK) {
                binding.preview.background = null

                val imageUri = data?.data
                binding.preview.setImageURI(imageUri)
                binding.tv.visibility = View.INVISIBLE
            }
        }
    }

}