package com.oyra.todolist.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.oyra.todolist.R;
import com.oyra.todolist.view.ItemsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by oyra on 21/01/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ItemsActivityTest {

    @Rule
    public ActivityTestRule<ItemsActivity> mActivityRule = new ActivityTestRule<>(
            ItemsActivity.class);

    @Test
    public void clickAddButton() {

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.item_name)).check(matches(isDisplayed()));

        String textTocheck = "Something" + System.currentTimeMillis();
        onView(withId(R.id.item_name)).perform(typeText(textTocheck));
        onView(withText("OK")).perform(click());
        onView(withText(textTocheck)).check(matches(isDisplayed()));

    }
}
