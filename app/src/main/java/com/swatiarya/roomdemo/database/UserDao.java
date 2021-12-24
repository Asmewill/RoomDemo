package com.swatiarya.roomdemo.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.swatiarya.roomdemo.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user ORDER BY ID")
    List<User> loadAllUsers();

    @Insert
    void insertUser(User person);

    @Update
    void updateUser(User person);

    @Delete
    void delete(User person);

    @Query("SELECT * FROM User WHERE id = :id")
    User loadUserById(int id);
}
