package com.dicoding.courseschedule.ui.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    val ruleHome = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun cleanup() {
        Intents.release()
    }

    @Test
    fun addAddActivityAppear_HomeActivity() {
        ruleHome.scenario

        Espresso.onView(ViewMatchers.withId(R.id.action_add))
            .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(AddCourseActivity::class.java.name))
    }

    @Test
    fun addAddActivityAppear_ListActivity() {
        ruleHome.scenario
        Espresso.onView(ViewMatchers.withId(R.id.action_list))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.fab))
            .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(AddCourseActivity::class.java.name))


    }
}
