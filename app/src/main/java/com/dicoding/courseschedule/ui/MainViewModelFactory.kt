package com.dicoding.courseschedule.ui

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.ui.add.AddCourseViewModel
import com.dicoding.courseschedule.ui.detail.DetailViewModel
import com.dicoding.courseschedule.ui.home.HomeViewModel
import com.dicoding.courseschedule.ui.list.ListViewModel


object MainViewModelFactory {

    fun factoryCreate(activity: Activity , id : Int)  : ViewModelProvider.Factory {
        val context = activity.applicationContext
            ?: throw  IllegalStateException ("Not yet attached to Application")

        val repo = DataRepository.getInstance(context)

        return object  : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                        repo?.let { HomeViewModel(it) } as T

                    modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                        repo?.let {DetailViewModel(it ,id) } as T

                    modelClass.isAssignableFrom(ListViewModel::class.java) ->
                        repo?.let { ListViewModel(it) } as T

                    modelClass.isAssignableFrom(AddCourseViewModel::class.java) ->
                        repo?.let { AddCourseViewModel(it) } as T

                 else -> throw IllegalArgumentException("Unknown ViewModel Class")
                }
            }
        }
    }



}