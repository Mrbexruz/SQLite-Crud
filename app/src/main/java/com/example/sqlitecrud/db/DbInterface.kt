package com.example.sqlitecrud.db

import com.example.sqlitecrud.models.User

interface DbInterface {
    fun addUsers(user: User)
    fun showUsers():List <User>
    fun deleteUser(user: User)
    fun editUser(user: User)



}