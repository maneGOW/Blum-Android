package com.nda.blum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nda.blum.DAO.*
import com.nda.blum.databinding.UsuarioNidoItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class NidosAlumnosListAdapter(
    val context: Context,
    val alumnos: UsuariosNidoResponse,
    val fragment: Fragment
) :
    ListAdapter<NidosAlumnosDataItem, RecyclerView.ViewHolder>(NidosAlumnosDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val alumnos = getItem(position) as NidosAlumnosDataItem.AlumnosItem
                holder.bind( alumnos.alumno,fragment)
            }
        }
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<UsuariosNidoResult>?) {
        adapterScope.launch {
            val items = when (alumnos.result) {
                null -> listOf(NidosAlumnosDataItem.Header)
                else -> list!!.map { NidosAlumnosDataItem.AlumnosItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NidosAlumnosDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is NidosAlumnosDataItem.AlumnosItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(
        val binding: UsuarioNidoItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UsuariosNidoResult, fragment: Fragment) {
            //binding.nidoData = item
            binding.txtRecursoNombre.text = item.nombreUsuarios

           /* binding.clickListener = clickListener
            binding.root.setOnClickListener {
                fragment.findNavController().navigate(
                    BlummersCoachFragmentDirections.actionBlummersCoachFragmentToChatWithCoachFragment(
                        "coachUser", item.Id_Usuario, item.Nombre_Usuario, item.Foto_Usuario
                    )
                )
            }*/

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UsuarioNidoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class NidosAlumnosDiffCallBack : DiffUtil.ItemCallback<NidosAlumnosDataItem>() {
    override fun areItemsTheSame(oldItem: NidosAlumnosDataItem, newItem: NidosAlumnosDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NidosAlumnosDataItem, newItem: NidosAlumnosDataItem): Boolean {
        return oldItem == newItem
    }
}

class NidosAlumnosListener(val clickListener: (alumnosId: String) -> Unit) {
    fun onClick(alumno: BuscarAlumnosCoachResult) = clickListener(alumno.Id_Usuario)
}

sealed class NidosAlumnosDataItem{
    data class AlumnosItem(val alumno: UsuariosNidoResult) : NidosAlumnosDataItem() {
        override val id = 1L
    }

    object Header : NidosAlumnosDataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}