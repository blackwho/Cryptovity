package com.example.appjo.cryptoinfo;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.appjo.cryptoinfo.Fragments.CoinListFragment;
import com.example.appjo.cryptoinfo.ViewModels.CoinListViewModel;

import static android.support.test.espresso.Espresso.onView;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CoinListFragmentInstrumentedTest {
    private CoinListFragment coinListFragment;

    @Rule
    public ActivityTestRule<DashboardActivity> activityActivityTestRule = new ActivityTestRule<DashboardActivity>(DashboardActivity.class);

    @Before
    public void init(){
        coinListFragment = (CoinListFragment) activityActivityTestRule.getActivity().getSupportFragmentManager().findFragmentByTag("CoinListFrag");
    }

    //checks if the recycler view is displayed
    @Test
    public void recyclerViewAddTest(){
        onView(withId(R.id.coin_list_rv)).check(matches(isDisplayed()));
    }

    //checks the viewmodel instance
    @Test
    public void viewModelAttachTest(){
        CoinListViewModel model = coinListFragment.getViewModel();
        assertNotEquals(model, null);
    }

    //checks the recycler view data integrity on 90th position
    @Test
    public void recyclerViewItemClickTest(){
        onView(withId(R.id.coin_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(90,
                        click()));
    }



}
