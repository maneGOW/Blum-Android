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

    @Insert
    fun insertFirstLaunch (launch:FirstLaunch)

    @Update
    fun updateFirstLaunch (launch: FirstLaunch)

    @Query("SELECT * FROM first_launch")
    fun getFristLaunchValue(): FirstLaunch?
}