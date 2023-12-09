package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.MainViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var selectedView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        setupActionBar()
        setupViewModel()
        observeSavedChanges()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.add_course)
        }
    }

    private fun setupViewModel() {
        val factory = MainViewModelFactory.factoryCreate(this, 0)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]
    }

    private fun observeSavedChanges() {
        viewModel.saved.observe(this) { event ->
            val message = if (event.getContentIfNotHandled() == true) {
                onBackPressed()
                null
            } else {
                getString(R.string.input_empty_message)
            }
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                insertCourse()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun insertCourse() {
        val courseName = findViewById<TextInputEditText>(R.id.ed_course_name).text.toString()
        val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
        val startTime = findViewById<TextView>(R.id.start_time_hour).text.toString()
        val endTime = findViewById<TextView>(R.id.end_time_hour).text.toString()
        val lecturer = findViewById<TextInputEditText>(R.id.ed_lecturer).text.toString()
        val note = findViewById<TextInputEditText>(R.id.ed_note).text.toString()

        viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
    }

    fun showStartTimePicker(view: View) {
        TimePickerFragment().show(supportFragmentManager, "startTime")
        selectedView = view
    }

    fun showEndTimePicker(view: View) {
        TimePickerFragment().show(supportFragmentManager, "endTime")
        selectedView = view
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (selectedView.id) {
            R.id.ib_start_time -> {
                findViewById<TextView>(R.id.start_time_hour).text = timeFormat.format(calendar.time)
            }
            R.id.ib_end_time -> {
                findViewById<TextView>(R.id.end_time_hour).text = timeFormat.format(calendar.time)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
