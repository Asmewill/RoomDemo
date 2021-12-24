package com.swatiarya.roomdemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.swatiarya.roomdemo.model.Person;

/**
 * Created by jsxiaoshui on 2021-12-24
 */
@Database(entities = {Person.class}, version = 1,exportSchema = false)
abstract class MyDatabases extends RoomDatabase {



}
