package com.nda.blum.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nda.blum.DAO.BuscarAlumnosCoachResponse
import com.nda.blum.DAO.BuscarAlumnosCoachResult
import com.nda.blum.DAO.BuscarNidoCoachResponse
import com.nda.blum.DAO.BuscarNidoCoachResult
import com.nda.blum.databinding.NidosCoachItemBinding
import com.nda.blum.ui.bluumerscoach.BlummersCoachFragmentDirections
import com.nda.blum.ui.nidoscoachfragment.NidosCoachFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class AlumnosAdapter(
    val context: Context,
    val alumnos: BuscarAlumnosCoachResponse,
    val clickListener: NidosListener,
    val fragment: Fragment
) :
    ListAdapter<AlumnoDataItem, RecyclerView.ViewHolder>(AlumnosDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val alumnos = getItem(position) as AlumnoDataItem.AlumnosItem
                holder.bind(clickListener, alumnos.alumno , fragment)
            }
        }
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<BuscarAlumnosCoachResult>?) {
        adapterScope.launch {
            val items = when (alumnos.result) {
                null -> listOf(AlumnoDataItem.Header)
                else -> list!!.map { AlumnoDataItem.AlumnosItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AlumnoDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is AlumnoDataItem.AlumnosItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(
        val binding: NidosCoachItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: NidosListener, item: BuscarAlumnosCoachResult, fragment: Fragment) {
            //binding.nidoData = item
            binding.txtAlumnoName.text = item.Nombre_Usuario
            Glide.with(fragment)
                .load(item.Foto_Usuario)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imgAlumno)
            binding.clickListener = clickListener
            binding.root.setOnClickListener {
                fragment.findNavController().navigate(BlummersCoachFragmentDirections.actionBlummersCoachFragmentToChatWithCoachFragment(
                    "coachUser", item.Id_Usuario, item.Nombre_Usuario, item.Foto_Usuario))
            }
            binding.root.setOnLongClickListener {
                val builder = AlertDialog.Builder(fragment.requireContext())
                builder.setTitle("BLUM")
                builder.setCancelable(false)
                builder.setMessage("¿Deseas enviar un archivo al alumno ${item.Nombre_Usuario}?")
                builder.setPositiveButton("Sí") { _, _ ->

                }
                builder.setNegativeButton("No"){ dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
                true
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NidosCoachItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class AlumnosDiffCallBack : DiffUtil.ItemCallback<AlumnoDataItem>() {
    override fun areItemsTheSame(oldItem: AlumnoDataItem, newItem: AlumnoDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AlumnoDataItem, newItem: AlumnoDataItem): Boolean {
        return oldItem == newItem
    }
}

class AlumnosListener(val clickListener: (alumnosId: String) -> Unit) {
    fun onClick(alumno: BuscarAlumnosCoachResult) = clickListener(alumno.Id_Usuario)
}

sealed class AlumnoDataItem {
    data class AlumnosItem(val alumno: BuscarAlumnosCoachResult) : AlumnoDataItem() {
        override val id = alumno.Id_Usuario.toLong()
    }

    object Header : AlumnoDataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}