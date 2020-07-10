package com.nda.blum.ui.listarecursos

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nda.blum.DAO.RecursosResult
import com.nda.blum.R
import com.nda.blum.adapters.RecursosAdapter
import com.nda.blum.databinding.ListaRecursosFragmentBinding
import com.nda.blum.ui.chat.ChatWithCoachFragmentArgs

class ListaRecursosFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingRecursosFragment: ListaRecursosFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.lista_recursos_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ListaRecursosViewModelFactory(application)
        val listaRecursosViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            ListaRecursosViewModel::class.java
        )

        bindingRecursosFragment.lifecycleOwner = this

        listaRecursosViewModel.getFilesFromServer()

        listaRecursosViewModel.userProfilePicture.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank()) {
                Glide.with(this.requireContext())
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingRecursosFragment.userProfilePicResources2)
            } else {
                Glide.with(this.requireContext())
                    .load(R.drawable.profile_imagen)
                    .apply(RequestOptions.circleCropTransform())
                    .into(bindingRecursosFragment.userProfilePicResources2)
            }
        })

        listaRecursosViewModel.recursosListLiveData.observe(viewLifecycleOwner, Observer {
            if (it.result.isNotEmpty()) {
                bindingRecursosFragment.rvListaDeRecursos.layoutManager =
                    LinearLayoutManager(this.context)
                val manager = GridLayoutManager(activity, 1)
                bindingRecursosFragment.rvListaDeRecursos.layoutManager = manager
                val adapter = RecursosAdapter(
                    this.requireContext(),
                    it,
                    this
                )
                var newListOfItems: MutableList<RecursosResult>
                newListOfItems = ArrayList()
                it.result.forEach { archivo ->
                    val tipoRecurso = ListaRecursosFragmentArgs.fromBundle(arguments!!).fileType
                    val fileExtension = archivo.nombreArchivo.substringAfterLast(".")
                    if (tipoRecurso == "pdf") {
                        if (fileExtension == "PDF" || fileExtension == "pdf") {
                            newListOfItems.add(archivo)
                            bindingRecursosFragment.txtNoItems.visibility = View.GONE
                        }else {
                            if(newListOfItems.size == 0){
                                bindingRecursosFragment.txtNoItems.visibility = View.VISIBLE
                            }
                        }
                    } else if (tipoRecurso == "audio") {
                        if (fileExtension == "wav" || fileExtension == "WAV" || fileExtension == "mp3" || fileExtension == "MP3") {
                            newListOfItems.add(archivo)
                            bindingRecursosFragment.txtNoItems.visibility = View.GONE
                        } else {
                            if(newListOfItems.size == 0){
                                bindingRecursosFragment.txtNoItems.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                adapter.addHeaderAndSubmitList(newListOfItems)
                bindingRecursosFragment.rvListaDeRecursos.adapter = adapter
            }
        })

        bindingRecursosFragment.imgResoursesBack2.setOnClickListener {
            this.findNavController().popBackStack()
        }

        bindingRecursosFragment.rvListaDeRecursos

        return bindingRecursosFragment.root

    }
}