package de.hdmstuttgart.fischis;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;

import androidx.test.core.app.ActivityScenario;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import de.hdmstuttgart.fischis.ui.MainActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@LargeTest
@RunWith(AndroidJUnit4.class)

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserLoginFragmentTest {

    public static final String STRING_TO_BE_TYPED = "Espresso";


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpSharedPreferences(){
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }

    @AfterClass
    public static void deleteSharedPreferences() {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    };




    /**
     * checks login and transition to timer fragment
     */
    @Test
    public void A_addUserName() {
        // Type text and then press the button.
        onView(withId(R.id.username))
                .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());

        // Check that the text was changed.
        onView(withId(R.id.username)).check(matches(withText(STRING_TO_BE_TYPED)));
        onView(withId(R.id.startBtn)).perform(click());
    };
/*

    @Test
    public void testToastMessageIfNoName() {
        activityRule.getScenario().onActivity(activity -> {
            onView(withId(R.id.startBtn)).perform(click());
            //Check if toastMessage pops up
            onView(withText(R.string.enterName)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));

        });

 }
*/



    /**
     * tests if login is saved by checking if timer fragment is opened when closing and
     * re-opening the application
     */
    @Test
    public void B_testSaveLogin() {
        activityRule.getScenario().close();
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.timerText)).check(matches(withText("10:00")));

    }


}
















