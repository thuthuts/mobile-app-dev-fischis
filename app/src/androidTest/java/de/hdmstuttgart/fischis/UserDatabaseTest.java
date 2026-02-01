package de.hdmstuttgart.fischis;


import android.support.test.InstrumentationRegistry;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import de.hdmstuttgart.fischis.repository.TaskExecutor;
import de.hdmstuttgart.fischis.repository.UserRepository;
import de.hdmstuttgart.fischis.ui.MainActivity;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)


/**
 * do "wipe data" before running tests !
 * After that, the tests can be executed without clearing the data, since the database is cleared
 * in @AfterClass
 */
public class UserDatabaseTest {

    private UserRepository userRepository;


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);



    @Before
    public void createDb() {
        activityRule.getScenario().onActivity(activity -> {
            userRepository = new UserRepository(activity.getApplication(), new TaskExecutor());

        });
    }



    @Test
    public void A_insertUserTest(){
        userRepository.setUsername("testUser");
        userRepository.saveImage("thisIsAurlToAnImage");
        pause(10);
        String dbUsername = userRepository.getUsersList().get(0).getUsername();
        String imageUrl = userRepository.getUsersList().get(0).getProfilePicture();

        Assert.assertEquals("testUser", dbUsername);
        Assert.assertEquals("thisIsAurlToAnImage", imageUrl);
    }

    @Test
    public void B_increaseCoins(){
        int coinsValue = userRepository.getUsersList().get(0).getCoins();
        userRepository.setCoins(150);

        pause(10);

        int newCoinsValue = userRepository.getUsersList().get(0).getCoins();

        Assert.assertEquals(coinsValue + 150, newCoinsValue);

    }

    @Test
    public void C_subtractCoins(){
        int coinsValue = userRepository.getUsersList().get(0).getCoins();
        userRepository.subtractCoins(150);

        pause(10);

        int newCoinsValue = userRepository.getUsersList().get(0).getCoins();

        Assert.assertEquals(coinsValue - 150, newCoinsValue);

    }

    @Test
    public void D_focusedTimeTest(){
        userRepository.addMinutes(10);
        pause(10);

        int focusedTime = userRepository.getUsersList().get(0).getFocusedTimeInMinutes();
        Assert.assertEquals(10,focusedTime);
    }


    public static void pause(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {

        }
    }

    @AfterClass
    public static void clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("UserDb");
    };

}



