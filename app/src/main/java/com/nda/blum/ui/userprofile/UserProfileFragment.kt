package com.nda.blum.ui.userprofile

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nda.blum.R
import com.nda.blum.databinding.UserProfileFragmentBinding
import kotlinx.coroutines.*
import java.io.*
import java.util.*

class UserProfileFragment : Fragment() {

    private val PERMISSION_REQUEST_CODE = 1
    private val SELECT_PHOTO = 100
    private val CAMERA_REQUEST = 101
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var bindingProfile: UserProfileFragmentBinding? = null
    var userProfileViewModel: UserProfileViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val progressDialog = ProgressDialog.show(this.requireContext(), "", "Cargando...", true)

        bindingProfile = DataBindingUtil.inflate(
            inflater,
            R.layout.user_profile_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = UserProfileViewModelFactory(application)
        userProfileViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel::class.java)

        bindingProfile!!.lifecycleOwner = this

        userProfileViewModel!!.setUserDataValues()

        userProfileViewModel!!.correoUsuario.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                bindingProfile!!.tietUserEmail.setText(it)
            }
        })

        userProfileViewModel!!.nombreUsuario.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                bindingProfile!!.tietUserName.setText(it)
            }
        })

        userProfileViewModel!!.numeroTelefono.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                bindingProfile!!.tietPhoneNumber.setText(it)

            }
        })

        userProfileViewModel!!.userProfilePicture.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                Glide.with(this)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingProfile!!.userProfilePic)
            }
        })

        userProfileViewModel!!.showProgressDialog.observe(viewLifecycleOwner, Observer {
            if(it){
                progressDialog.show()
            }else{
                progressDialog.dismiss()
            }
        })

        bindingProfile!!.btnEditarPerfil.setOnClickListener {
            println("BOTON PRESIONADO")
            println("VALUE ${bindingProfile!!.textInputUserName.isEnabled}")
            if(bindingProfile!!.textInputUserName.isEnabled){
                bindingProfile!!.btnEditarPerfil.text = "EDITAR"
                bindingProfile!!.textInputUserName.setEnabled(false)
                bindingProfile!!.textInputPhoneNumber.setEnabled(false)
                userProfileViewModel!!.updateUserData("${bindingProfile!!.tietUserName.text}", "${bindingProfile!!.tietPhoneNumber.text}")
            }else{
                bindingProfile!!.btnEditarPerfil.text = "GUARDAR"
                bindingProfile!!.textInputUserName.setEnabled(true)
                bindingProfile!!.textInputPhoneNumber.setEnabled(true)

            }
        }

        bindingProfile!!.imgBackProfile.setOnClickListener {
            this.findNavController().popBackStack()
        }

        bindingProfile!!.constraintImgContainer.setOnClickListener {
            println("IMAGE CLICKED")
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Cambia tu foto de perfil")
            builder.setMessage("¿De donde quieres tomar la foto?")
            builder.setPositiveButton("Galería") { _, _ ->
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                pickPhoto.type = "image/*"
                startActivityForResult(pickPhoto, SELECT_PHOTO)
            }
            builder.setNegativeButton("Cámara") { _, _ ->
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, CAMERA_REQUEST)
                bindingProfile!!.userProfilePic.setImageURI(takePicture.toURI().toUri())
            }

            builder.setNeutralButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        bindingProfile!!.btnEditarPerfil2.setOnClickListener {
            val builder = AlertDialog.Builder(this.context!!)
            builder.setTitle("Alerta")
            builder.setMessage("¿Deseas cerrar tu sesión de BLUM?")
            builder.setPositiveButton("Sí") { _, _ ->
                this.activity!!.finish()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        return bindingProfile!!.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PHOTO) {
                val selectedImage: Uri? = data!!.data
                var imageStream: InputStream? = null
                try {
                    imageStream = this.activity!!.contentResolver.openInputStream(selectedImage!!)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                val yourSelectedImage = BitmapFactory.decodeStream(imageStream)
                coroutineScope.launch {
                    val file = File(bitmapToFile(yourSelectedImage).path!!)
                    try {
                        userProfileViewModel!!.uploadPicture(file)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                Glide.with(this)
                    .load(yourSelectedImage)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingProfile!!.userProfilePic)
            } else if (requestCode == CAMERA_REQUEST) {
                val photo = data!!.extras!!["data"] as Bitmap?
                val stream = ByteArrayOutputStream()
                photo!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                coroutineScope.launch {
                    val file = File(bitmapToFile(photo).path!!)
                    try {
                        userProfileViewModel!!.uploadPicture(file)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("Error:", "$e")
                    }
                }
                Glide.with(this)
                    .load(stream.toByteArray())
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingProfile!!.userProfilePic)
            }
        }
    }

    private fun bitmapToFile(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(requireContext())
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

}