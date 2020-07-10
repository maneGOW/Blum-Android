package com.nda.blum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nda.blum.DAO.*
import com.nda.blum.databinding.NotificacionesItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class NotificacionesAdapter(
    val context: Context,
    val recursos: NotificacionesResponse,
    val fragment: Fragment
) :
    ListAdapter<NotificacionesDataItem, RecyclerView.ViewHolder>(NotificacionesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val notificaciones = getItem(position) as NotificacionesDataItem.NotificacionesItem
                holder.bind(notificaciones.notificacion, fragment)

            }
        }
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<NotificacionesResult?>) {
        adapterScope.launch {
            val items = when (recursos.result) {
                null -> listOf(NotificacionesDataItem.Header)
                else -> list!!.map { NotificacionesDataItem.NotificacionesItem(it!!) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NotificacionesDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is NotificacionesDataItem.NotificacionesItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(
        val binding: NotificacionesItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NotificacionesResult, fragment: Fragment) {
            binding.txtTituloNotificacion.text = "Cita: ${item.Dia_Cita}"
            binding.txtDetalleNotificacion.text = "Hora de la cita: ${item.Hora_Cita}:00 HRS con tu coach ${item.coach}"
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NotificacionesItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class NotificacionesDiffCallback : DiffUtil.ItemCallback<NotificacionesDataItem>() {
    override fun areItemsTheSame(oldItem: NotificacionesDataItem, newItem: NotificacionesDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotificacionesDataItem, newItem: NotificacionesDataItem): Boolean {
        return oldItem == newItem
    }
}

class NotificacionesListener(val clickListener: (recursosId: String) -> Unit) {
    fun onClick(nido: NotificacionesResult) = clickListener(nido.Id_Cita!!)
}

sealed class NotificacionesDataItem {
    data class NotificacionesItem(val notificacion: NotificacionesResult) : NotificacionesDataItem() {
        override val id = 1L
    }

    object Header : NotificacionesDataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}