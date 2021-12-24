package com.swatiarya.roomdemo.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.util.Log;

import com.swatiarya.roomdemo.model.Person;
import com.swatiarya.roomdemo.model.User;

/***
 * https://blog.csdn.net/wjzj000/article/details/95863976
 */

@Database(entities = {Person.class, User.class}, version = 4, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "personlist";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                //1.version+1的时候闪退
//                sInstance = Room.databaseBuilder(context.getApplicationContext(),
//                        AppDatabase.class, AppDatabase.DATABASE_NAME)
//                        .build();
                //2.version+1的时候，删除原来的数据库，然后从写新建，此时的确不会crash，但所有数据丢失。
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME).fallbackToDestructiveMigration()
                        .build();
                //3.version+1的时候，增加字段一个字段
//                sInstance = Room.databaseBuilder(context.getApplicationContext(),
//                        AppDatabase.class, AppDatabase.DATABASE_NAME).addMigrations(MIGRATION_1_2)
//                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract PersonDao personDao();


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // 因为没有变化，所以是一个空实现
            database.execSQL("ALTER TABLE person "
                    + " ADD COLUMN age INTEGER");
        }
    };

    public abstract UserDao userDao();

}
