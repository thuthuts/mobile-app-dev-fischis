package de.hdmstuttgart.fischis;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.navigation.testing.TestNavHostController;
import androidx.navigation.ui.NavigationUI;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import de.hdmstuttgart.fischis.model.User;
import de.hdmstuttgart.fischis.repository.BoughtFishRepository;
import de.hdmstuttgart.fischis.repository.TaskExecutor;
import de.hdmstuttgart.fischis.repository.UserRepository;
import de.hdmstuttgart.fischis.ui.MainActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigationTest {
    private UserRepository userRepository;


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    public TestNavHostController navHostController;

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

    @Before
    public void testSetUp() {
        navHostController = new TestNavHostController(ApplicationProvider.getApplicationContext());
    }


    @Test
    public void testNavigation() {


        activityRule.getScenario().onActivity(activity -> {
            userRepository = new UserRepository(activity.getApplication(),new TaskExecutor());
            userRepository.insert(new User("TestUser"));
            navHostController.setGraph(R.navigation.nav_graph);
            NavigationUI.setupWithNavController((BottomNavigationView) activity.findViewById(R.id.bottomNavigationView), navHostController);

        });

        onView(withId(R.id.profileFragment)).perform(click());
        Assert.assertEquals(navHostController.getCurrentDestination().getId(), R.id.profileFragment);

        onView(withId(R.id.timerFragment)).perform(click());
        Assert.assertEquals(navHostController.getCurrentDestination().getId(), R.id.timerFragment);


    }





}
