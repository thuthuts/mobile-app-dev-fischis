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

import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.repository.BoughtFishRepository;
import de.hdmstuttgart.fischis.repository.TaskExecutor;
import de.hdmstuttgart.fischis.ui.MainActivity;


/**
 * do "wipe data" before running tests !
 * After that, the tests can be executed without clearing the data, since the database is cleared
 * in @AfterClass
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoughtFishDatabaseTest {
    private BoughtFishRepository boughtFishRepository;


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void createDb() {
        activityRule.getScenario().onActivity(activity -> {
            TaskExecutor executor = new TaskExecutor();
            boughtFishRepository = new BoughtFishRepository(activity.getApplication(), executor);


        });

    }


    @AfterClass
    public static void deleteDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("BoughtFish");

    }


    @Test
    public void A_insertBoughtFishTest() throws InterruptedException {
        boughtFishRepository.insert(new BoughtFish("PandaMoor", 0));
        boughtFishRepository.insert(new BoughtFish("Nemo",0));
        pause(10);

        String addedFishName = boughtFishRepository.getBoughtFishList().get(0).getFishName();
        String addedFishName2 = boughtFishRepository.getBoughtFishList().get(1).getFishName();
        Assert.assertEquals("PandaMoor", addedFishName);
        Assert.assertEquals("Nemi", addedFishName2);

    }

    @Test
    public void B_increaseAmount(){
        int amount = boughtFishRepository.getBoughtFishList().get(0).getAmount();
        boughtFishRepository.addAmount("PandaMoor", 1);

        pause(10);

        int newAmount = boughtFishRepository.getBoughtFishList().get(0).getAmount();

        Assert.assertEquals(amount + 1, newAmount);

    }


    public static void pause(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {

        }
    }




}
