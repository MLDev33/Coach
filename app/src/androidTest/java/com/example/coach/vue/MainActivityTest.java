package com.example.coach.vue;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.coach.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule(MainActivity.class);

    @Test
    public void scenario(){
        onView(withId(R.id.btnCalc)).perform(click());
    }
}