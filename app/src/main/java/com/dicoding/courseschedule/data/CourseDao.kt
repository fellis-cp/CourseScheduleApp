package com.dicoding.courseschedule.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)
@Dao
interface CourseDao {

    @RawQuery(observedEntities = [Course::class])
    fun getNearestSchedule(query: SupportSQLiteQuery): LiveData<Course?>

    @RawQuery(observedEntities = [Course :: class])
    fun getAll(query: SupportSQLiteQuery): DataSource.Factory<Int, Course>

    @Query("SELECT * FROM course WHERE id=:id")
    fun getCourse(id: Int): LiveData<Course>

    @Query("SELECT * FROM course WHERE day=:day")
    fun getTodaySchedule(day: Int): List<Course>

    fun insert(course: Course)

    fun delete(course: Course)
}