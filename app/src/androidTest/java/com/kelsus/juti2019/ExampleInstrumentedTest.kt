package com.kelsus.juti2019

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.kelsus.juti2019.view.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class ExampleInstrumentedTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun isDisplayedTest() {
        onView(withId(R.id.getGifButton)).check(matches(isDisplayed()))
    }

    @Test
    fun buttonDisabledTest() {
        val onButton = onView(withId(R.id.getGifButton))
        onButton.perform(click())
        onButton.check(matches(not(isEnabled())))
    }
}