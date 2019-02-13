package com.example.appjo.cryptoinfo;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appjo.cryptoinfo.Fragments.CoinDetailsFragment;
import com.example.appjo.cryptoinfo.ViewModels.CoinDetailViewModel;

import static android.support.test.espresso.Espresso.onView;
import static org.junit.Assert.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CoinDetailsFragmentInstrumentedTest {

    private CoinDetailsFragment coinDetailsFragment;
    @Rule
    public ActivityTestRule<DashboardActivity> activityActivityTestRule = new ActivityTestRule<DashboardActivity>(DashboardActivity.class);

    @Before
    public void init(){
        onView(withId(R.id.coin_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(90,
                        click()));
        coinDetailsFragment = (CoinDetailsFragment) activityActivityTestRule.getActivity().getSupportFragmentManager().findFragmentByTag("CoinDetailsFrag");

    }

    @Test
    public void viewModelAttachTest(){
        CoinDetailViewModel model = coinDetailsFragment.getViewModel();
        assertNotEquals(model, null);
    }

    @Test
    public void coinDetailsLowPriceTest(){
        onView(withId(R.id.low_value_tv)).check(matches(textViewHasValue()));
    }

    @Test
    public void coinDetailsHighPriceTest(){
        onView(withId(R.id.high_value_tv)).check(matches(textViewHasValue()));
    }

    @Test
    public void coinDetailsMarketCapTest(){
        onView(withId(R.id.market_cap_tv)).check(matches(textViewHasValue()));
    }

    @Test
    public void coinDetailsPriceTest(){
        onView(withId(R.id.price_tv)).check(matches(textViewHasValue()));
    }

    //custom matcher for null check
    private Matcher<View> textViewHasValue() {

        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("The TextView/EditText has value");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextView)) {
                    return false;
                }
                if (view != null) {
                    String text;
                    if (view instanceof TextView) {
                        text = ((TextView) view).getText().toString();
                    } else {
                        text = ((EditText) view).getText().toString();
                    }

                    Log.v("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC", text);
                    return (!TextUtils.isEmpty(text));
                }
                return false;
            }
        };
    }

}
