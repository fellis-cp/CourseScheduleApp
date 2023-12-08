package com.dicoding.courseschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//TODO 3 : Define room database class
@Database ( version = 1 , entities = [Course::class] , exportSchema = false)
abstract class CourseDatabase : RoomDatabase() {


    abstract fun courseDao(): CourseDao

    companion object {

        @Volatile
        private var INSTANCE: CourseDatabase? = null

        fun getInstance(context: Context): CourseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, CourseDatabase::class.java,
                    "courses.db"
                ).build()
                INSTANCE = instance
                instance
            }

        }
    }
}
