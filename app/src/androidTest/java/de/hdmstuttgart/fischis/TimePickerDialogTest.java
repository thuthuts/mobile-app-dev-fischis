package de.hdmstuttgart.fischis;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hdmstuttgart.fischis.ui.MainActivity;

import de.hdmstuttgart.fischis.ui.timer.TimePickerDialog;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class TimePickerDialogTest {
    FragmentScenario<TimePickerDialog> scenario;

    @Rule
        public ActivityScenarioRule<MainActivity> activityRule =
                new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpSharedPreferences(){
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LoggedIn", true);
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



    @Test
    public void launchDialogFragmentAndVerifyUI() {
        // Use launchFragment to launch the dialog fragment in a dialog.
        scenario = FragmentScenario.launch(TimePickerDialog.class);

        scenario.onFragment(fragment ->{

                Assert.assertNotNull(fragment.getDialog());
                Assert.assertTrue(fragment.requireDialog().isShowing());

        });

    }


    @Test
    public void launchDialogFragmentEmbeddedToHostActivityAndVerifyUI() {
        // Use launchFragmentInContainer to inflate a dialog fragment's view into Activity's content view.

        scenario = FragmentScenario.launchInContainer(TimePickerDialog.class);
        // Dialog is not created because you use launchFragmentInContainer and the view is inflated
        // into the Activity's content view.


       scenario.onFragment(fragment -> {

            Assert.assertNull(fragment.getDialog());

           });


    }}



