package com.example.hci.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
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
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hci.R
import com.example.hci.databinding.FragmentProfileBinding
import com.example.hci.ldb
import com.example.hci.logged_user
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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG","onCreateView: called")
        if(logged_user == null) NavHostFragment.findNavController(this).navigate(R.id.loginFragment)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // inizio funzioni
        binding.errorTextp.visibility = View.GONE
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
            //binding.emaiEdit.isEnabled = true
            binding.phoneEdit.isEnabled=true

        }
        binding.done.setOnClickListener{
            if(binding.nameEdit.text.toString() == ""){
                showMsg("Name")
            } else if (binding.surnameEdit.text.toString() == ""){
                showMsg("Surname")
            } else if (binding.addressEdit.text.toString() == ""){
                showMsg("Address")
            } else if (binding.phoneEdit.text.toString() == ""){
                showMsg("Phone")
            } else{
                saveData()
                binding.nameEdit.isEnabled = false
                binding.surnameEdit.isEnabled= false
                binding.addressEdit.isEnabled =false
                //binding.emaiEdit.isEnabled = false
                binding.phoneEdit.isEnabled=false
            }
        }
        return root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun saveImage() {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 50, stream)
        val encodedImage = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)

        logged_user?.email?.let { ldb?.updateImage(it, encodedImage) }

        Toast.makeText( activity, "saved image", Toast.LENGTH_SHORT).show()

    }

    private fun saveData() {
        Log.d("called:","saveData")
        val nametext: String = binding.nameEdit.text.toString()
        val surnametext: String = binding.surnameEdit.text.toString()
        val addresstext: String = binding.addressEdit.text.toString()
        //val emailtext: String = binding.emaiEdit.text.toString()
        val phonetext: String = binding.phoneEdit.text.toString()
        Log.d("saved Data:",nametext)


        logged_user?.email?.let { ldb?.update(it, nametext, surnametext, addresstext, phonetext ) }

        Toast.makeText( activity, "saved data", Toast.LENGTH_SHORT).show()

    }


    private fun loadData() {
        Log.d("called: ","load data")

        //chiamata al DB per recuperare l'immagine e scrittura in imageView
        val img = logged_user?.email?.let { ldb?.getImage(it) }
        if (img != null) {
            val imageBytes = Base64.decode(img, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.imageView.setImageBitmap(decodedImage)
        }

        binding.nameEdit.setText(logged_user?.name)
        binding.surnameEdit.setText(logged_user?.surname)
        binding.addressEdit.setText(logged_user?.address)
        //binding.emaiEdit.setText(logged_user?.email)
        binding.phoneEdit.setText(logged_user?.phone)
    }

    private fun displayMsg(str: String){
        val errtxt = binding.errorTextp
        errtxt.text = str
        errtxt.visibility = View.VISIBLE
        Handler().postDelayed(Runnable { // hide your button here
            errtxt.visibility = View.GONE
        }, 2000)
    }

    private fun showMsg(msg: String){
        displayMsg(msg + " can't be empty")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}