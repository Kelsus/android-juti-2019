package com.kelsus.juti2019

import androidx.test.rule.ActivityTestRule
import com.kelsus.juti2019.view.MainActivity
import org.junit.Rule

class ExampleInstrumentedTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)
}