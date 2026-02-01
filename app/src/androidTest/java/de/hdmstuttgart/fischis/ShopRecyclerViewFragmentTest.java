package de.hdmstuttgart.fischis;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import de.hdmstuttgart.fischis.ui.shop.ShopRecyclerViewFragment;

import de.hdmstuttgart.fischis.ui.MainActivity;


import static androidx.test.espresso.Espresso.onView;


import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShopRecyclerViewFragmentTest {
    FragmentScenario<ShopRecyclerViewFragment> scenario;


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        scenario = FragmentScenario.launchInContainer(ShopRecyclerViewFragment.class);

    }

    @Test
    public void existTestShopRecyclerViewTest() {
        onView(withId(R.id.shopList)).check(matches(isDisplayed()));

        onView(withId(R.id.shopList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.fishName)));
    }

    @Test
    public void ShopRecyclerViewTest() {


        scenario.onFragment(fragment -> {

            RecyclerView recyclerView = fragment.getActivity().findViewById(R.id.shopList);
            int count = recyclerView.getAdapter().getItemCount();
            View itemView = recyclerView.getLayoutManager().findViewByPosition(0);
            TextView nameView = itemView.findViewById(R.id.fishName);
            String name = nameView.getText().toString();

            TextView priceView = itemView.findViewById(R.id.fishPrice);
            String price = priceView.getText().toString();


            Assert.assertTrue(count > 0);
            Assert.assertEquals("Nemo", name);
            Assert.assertEquals("100", price);
        });


    }

    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }


    }
}







