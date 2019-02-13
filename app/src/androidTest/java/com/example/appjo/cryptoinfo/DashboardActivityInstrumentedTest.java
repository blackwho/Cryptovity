package com.example.appjo.cryptoinfo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DashboardActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<DashboardActivity> activityTestRule = new ActivityTestRule<>(DashboardActivity.class);

    @Test
    public void frameLayoutDisplayTest(){
        onView(withId(R.id.dashboard_fragment_container)).check(matches(isDisplayed()));
    }

    //checks if the frame-layout is completely displayed
    @Test
    public void frameLayoutCompleteDisplayTest(){
        onView(withId(R.id.dashboard_fragment_container)).check(matches(isCompletelyDisplayed()));
    }
}
