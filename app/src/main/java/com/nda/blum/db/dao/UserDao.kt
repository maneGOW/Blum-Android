package com.nda.blum.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nda.blum.db.entity.FirstLaunch
import com.nda.blum.db.entity.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAllUserData()

    @Query("Select * FROM user_table")
    fun getUserNameLiveData(): LiveData<User?>

    @Query("Select * FROM user_table")
    fun getAllUserData(): User?

    @Query("UPDATE user_table SET rememberme = :value1 WHERE userId = 1")
    fun updateRemembermeValue(value1:Boolean)

    @Query("UPDATE user_table SET coachId = :value1, idNido =:value2 WHERE userId = 1")
    fun updatecoachIdAndNest(value1:String, value2: String)

    @Query("UPDATE user_table SET userServerId = :value1, userNombreUsuario =:value2, userCorreoElectronico = :value3, userRol =:value4, userTelefonoUsuario = :value5 WHERE userId = 1")
    fun loginUpdate(value1: String, value2: String, value3: String, value4: String, value5: String)

    @Insert
    fun insertFirstLaunch (launch:FirstLaunch)

    @Update
    fun updateFirstLaunch (launch: FirstLaunch)

    @Query("SELECT * FROM first_launch")
    fun getFristLaunchValue(): FirstLaunch?
}