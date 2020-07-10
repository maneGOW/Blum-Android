package com.nda.blum.ui.nidosdata

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nda.blum.R
import com.nda.blum.adapters.NidosAlumnosListAdapter
import com.nda.blum.databinding.NidosDataFragmentBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.*
import java.util.*

class NidosDataFragment : Fragment() {

    var bindingNidoData: NidosDataFragmentBinding? = null
    private val SELECT_PHOTO = 100
    private val CAMERA_REQUEST = 101
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var  nidosDataViewModel: NidosDataViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingNidoData = DataBindingUtil.inflate(
            inflater, R.layout.nidos_data_fragment, container, false
        )

        bindingNidoData!!.lifecycleOwner = this
        val application = requireNotNull(this.activity).application

        val viewModelFactory = NidosDataViewModelFactory(application)
        nidosDataViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NidosDataViewModel::class.java)

        nidosDataViewModel!!.nidoIDLiveData.value = NidosDataFragmentArgs.fromBundle(arguments!!).idNido

        bindingNidoData!!.tietNidoName.setText(NidosDataFragmentArgs.fromBundle(arguments!!).nombreNido)

        nidosDataViewModel!!.getNidosList()

        bindingNidoData!!.imgBackNidoData.setOnClickListener {
            this.findNavController().popBackStack()
        }

        nidosDataViewModel!!.alumnosNido.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it.result.isNotEmpty()){
                bindingNidoData!!.rvNidoIntegrantes.layoutManager =
                    LinearLayoutManager(this.context)
                val manager = GridLayoutManager(activity, 1)
                bindingNidoData!!.rvNidoIntegrantes.layoutManager = manager
                val adapter = NidosAlumnosListAdapter(
                    this.requireContext(),
                    it,
                    this
                )
                adapter.addHeaderAndSubmitList(it.result)
                bindingNidoData!!.rvNidoIntegrantes.adapter = adapter
            }
        })

        bindingNidoData!!.btnEditarPerfil.setOnClickListener{
            if(bindingNidoData!!.textInputUserName.isEnabled){
                bindingNidoData!!.btnEditarPerfil.text = "EDITAR"
                bindingNidoData!!.textInputUserName.setEnabled(false)
                nidosDataViewModel!!.updateNidoData("${bindingNidoData!!.tietNidoName.text}")
                Toast.makeText(this.requireContext(), "Se ha cambiado el nombre del nido", Toast.LENGTH_LONG).show()
            }else{
                bindingNidoData!!.btnEditarPerfil.text = "GUARDAR"
                bindingNidoData!!.textInputUserName.setEnabled(true)
            }
        }

        bindingNidoData!!.nidoPicture.setOnClickListener {
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
                bindingNidoData!!.nidoPicture.setImageURI(takePicture.toURI().toUri())
            }

            builder.setNeutralButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        Glide.with(this)
            .load(NidosDataFragmentArgs.fromBundle(arguments!!).fotoNido)
            .apply(RequestOptions.circleCropTransform())
            .into(bindingNidoData!!.nidoPicture)

        return bindingNidoData!!.root
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
                  /*  try {
                        userProfileViewModel!!.uploadPicture(file)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }*/
                }
                Glide.with(this)
                    .load(yourSelectedImage)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingNidoData!!.nidoPicture)
            } else if (requestCode == CAMERA_REQUEST) {
                val photo = data!!.extras!!["data"] as Bitmap?
                val stream = ByteArrayOutputStream()
                photo!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                coroutineScope.launch {
                    val file = File(bitmapToFile(photo).path!!)
                   /* try {
                        userProfileViewModel!!.uploadPicture(file)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("Error:", "$e")
                    }*/
                }
                Glide.with(this)
                    .load(stream.toByteArray())
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingNidoData!!.nidoPicture)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nidosDataViewModel!!.getNidosList()
        nidosDataViewModel!!.alumnosNido.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it.result.isNotEmpty()){
                bindingNidoData!!.rvNidoIntegrantes.layoutManager =
                    LinearLayoutManager(this.context)
                val manager = GridLayoutManager(activity, 1)
                bindingNidoData!!.rvNidoIntegrantes.layoutManager = manager
                val adapter = NidosAlumnosListAdapter(
                    this.requireContext(),
                    it,
                    this
                )
                adapter.addHeaderAndSubmitList(it.result)
                bindingNidoData!!.rvNidoIntegrantes.adapter = adapter
            }
        })
    }

    override fun onPause() {
        super.onPause()
        nidosDataViewModel!!.getNidosList()
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