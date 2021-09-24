package com.example.hci.profile


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.hci.databinding.FragmentProfileBinding
import java.io.ByteArrayOutputStream


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private var mGetContent: ActivityResultLauncher<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG","onCreate: called")
        super.onCreate(savedInstanceState)
    }

    //var IMAGE_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG","onCreateView: called")

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // inizio funzioni
        loadData()

        imageView = binding.imageView
        button = binding.imgPickBtn

        val getAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val bitmap=it?.data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
        }
        val getImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imageView.setImageURI(it)
                saveImage()
            })

        button.setOnClickListener{
            //val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //   camera
            // getAction.launch(intent)
            getImage.launch("image/*")
        }
        binding.edit.setOnClickListener{
            binding.nameEdit.isEnabled=true
            binding.surnameEdit.isEnabled=true
            binding.addressEdit.isEnabled =true
            binding.emaiEdit.isEnabled = true
            binding.phoneEdit.isEnabled=true

        }
        binding.done.setOnClickListener{
             saveData()
            binding.nameEdit.isEnabled = false
            binding.surnameEdit.isEnabled= false
            binding.addressEdit.isEnabled =false
            binding.emaiEdit.isEnabled = false
            binding.phoneEdit.isEnabled=false
         }
        return root
    }
    // get a variable




   /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){

            imageView.setImageURI(data?.data)

        }
    }*/

    private fun saveImage() {
        val context: Context? = activity
        val sharedPreferences = context!!.getSharedPreferences("sharedPrefers",
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val encodedImage = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)

        editor.apply{
            putString("IMAGE_KEY", encodedImage)
        }.apply()
        Toast.makeText( activity, "saved image", Toast.LENGTH_SHORT).show()

    }

    private fun saveData() {
        Log.d("called:","saveData")
        val nametext: String = binding.nameEdit.text.toString()
        val surnametext: String = binding.surnameEdit.text.toString()
        val addresstext: String = binding.addressEdit.text.toString()
        val emailtext: String = binding.emaiEdit.text.toString()
        val phonetext: String = binding.phoneEdit.text.toString()
        Log.d("saved Data:",nametext)

        val context: Context? = activity
        val sharedPreferences = context!!.getSharedPreferences("sharedPrefers",
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //save data
        editor.apply{
            putString("NAME_KEY",nametext)
            putString("SURNAME_KEY",surnametext)
            putString("ADDRESS_KEY",addresstext)
            putString("EMAIL_KEY",emailtext)
            putString("PHONE_KEY",phonetext)


        }.apply()
        Toast.makeText( activity, "saved data", Toast.LENGTH_SHORT).show()

    }


    private fun loadData() {
        Log.d("called: ","load data")

        val context: Context? = activity
        val sharedPreferences = context!!.getSharedPreferences("sharedPrefers",
            Context.MODE_PRIVATE)

        val nameString = sharedPreferences.getString("NAME_KEY",null)
        val surnameString = sharedPreferences.getString("SURNAME_KEY",null)
        val addressString = sharedPreferences.getString("ADDRESS_KEY",null)
        val emailString = sharedPreferences.getString("EMAIL_KEY",null)
        val phoneString = sharedPreferences.getString("PHONE_KEY",null)

        val encodedImage = sharedPreferences.getString("IMAGE_KEY","DEFAULT")

        if (encodedImage != "DEFAULT") {
            val imageBytes = Base64.decode(encodedImage, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.imageView.setImageBitmap(decodedImage)
        }


        binding.nameEdit.setText(nameString)
        binding.surnameEdit.setText(surnameString)
        binding.addressEdit.setText(addressString)
        binding.emaiEdit.setText(emailString)
        binding.phoneEdit.setText(phoneString)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}