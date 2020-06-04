package com.nda.blum.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,
    var userServerId: String?,
    var userNombreUsuario: String?,
    var userCorreoElectronico: String?,
    var userRol: String?,
    var userTelefonoUsuario: String?,
    var userFotoDePerfil: String?,
    var userFirstLaunch: Boolean?
)