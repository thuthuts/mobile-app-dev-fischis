package de.hdmstuttgart.fischis.ui.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdmstuttgart.fischis.R;
import de.hdmstuttgart.fischis.databinding.FragmentMainScreenBinding;
import de.hdmstuttgart.fischis.fish.Fish;
import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.ui.MainActivity;

/**
 * class that represents the home fragment and let the user choose a fish to focus on
 */


public class TimerFragment extends Fragment{

    private CountDownTimer countdown;
    private long timeLeft;
    private FragmentMainScreenBinding binding;
    private TimerViewModel timerViewModel;
    private int pickedMinutes;
    private List<Fish> fishList = new ArrayList<>();
    private List<Fish> possesList;
    private boolean timerRunning;
    private int actualFishPosition;
    private List<BoughtFish> boughtFishList;

    private ViewPager2 viewPager2;
    ViewPager2Adapter viewPager2Adapter;
    ViewPager2Adapter viewPager2AdapterTimerRunning;

    int[] images;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fishList = MainActivity.getFishData().getFishList();

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timerViewModel = new ViewModelProvider(this).get(TimerViewModel.class);

        MainActivity.showBottomNav();

        boughtFishList = timerViewModel.getBoughtFishList();
        possesList = timerViewModel.createFishPossesList(fishList, boughtFishList);

        //set up array size for the image slider
        images = new int[possesList.size()];

        //add the images to the image slider array
        images = timerViewModel.getAdultDrawable(possesList, images);

        //create viewPager with the bought Fish
        viewPager2 = binding.viewPagerMain;
        viewPager2Adapter = new ViewPager2Adapter(this.getContext(), images);
        // adding the adapter to viewPager2
        // to show the views in recyclerview
        viewPager2.setAdapter(viewPager2Adapter);


        //hide the stop timer button
        binding.stopTimerBtn.setVisibility(View.GONE);

        //set the Coins with the livedata of actual coins from the user
        timerViewModel.getUsersInformation().observe(getViewLifecycleOwner(), users -> {
            binding.coinsTextView.setText(String.valueOf(users.get(0).getCoins()));
            Log.d("TimerFragment", "Coins are updated");
        });

        //set up default quote if API request is not working
        setDefaultQuote();

        //open number picker dialog to choose a focus time
        openNumberPickerDialog();

        //start timer by button click
        binding.startTimerBtn.setOnClickListener((View v) -> {
            calculateTimeLeft();
            MainActivity.hideBottomNav();
            startTimer();
            binding.startTimerBtn.setVisibility(View.GONE);
            binding.stopTimerBtn.setVisibility(View.VISIBLE);

        });

        //stops timer when user decides to give up
        binding.stopTimerBtn.setOnClickListener((View v) -> stopTimer());


        //observes BackStackEntry and sets new value for the pickedTime
        NavController navController = NavHostFragment.findNavController(this);
        MutableLiveData<String> liveData = navController.getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("pickedTime");
        liveData.observe(getViewLifecycleOwner(), s -> binding.timerText.setText(s + getString(R.string.MinutesInTimer)));

    }



    /**
     * gets user input and parses minutes in milliseconds
     */
    private void calculateTimeLeft() {
        StringBuilder minutes = new StringBuilder();

        for (int i = 0; i < binding.timerText.getText().length(); i++) {
            if (binding.timerText.getText().charAt(i) == ':') {
                break;
            } else {
                minutes.append(binding.timerText.getText().charAt(i));
            }
        }
        pickedMinutes = Integer.parseInt(minutes.toString());
        timeLeft = pickedMinutes * 60000;
    }


    /**
     * opens timer dialog to set timer
     */
    private void openNumberPickerDialog() {
        binding.timerText.setOnClickListener((View v) -> Navigation.findNavController(v).navigate(R.id.action_timerFragment_to_timePickerDialog));
    }


    /**
     * starts timer and handles onTick() and onFinish()
     */
    private void startTimer() {
        int drawable = getAdultFishDrawable();
        actualFishPosition = viewPager2.getCurrentItem();
        setActualImages();
        binding.timerText.setEnabled(false);
        viewPager2.setUserInputEnabled(false);
        Log.d("TimerFragment", "Disable scrolling of ViewPager");


        countdown = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimerText();
                updateFishImage();
                timerRunning = true;
                Log.d("TimerFragment", "Timer running true");
            }

            @Override
            public void onFinish() {
                timerViewModel.setCoins(CoinReward.getReward(pickedMinutes).getCoins());
                timerViewModel.addMinutes(pickedMinutes);
                timerViewModel.addAmount(possesList.get(actualFishPosition).getName(), 1);
                timerRunning = false;
                Log.d("TimerFragment", "Timer running false");


                TimerFragmentDirections.ActionTimertoReceiveFish action = TimerFragmentDirections.actionTimertoReceiveFish(drawable);
                Navigation.findNavController(requireView()).navigate(action);
                viewPager2.setAdapter(viewPager2Adapter);
                viewPager2.setUserInputEnabled(true);
                Log.i("TimerFragment", "Navigate to ReceiveFishFragment");

                resetTimer();
                Log.d("TimerFragment", "Timer finished");

            }
        }.start();
        Log.d("TimerFragment", "Timer started");
    }


    private void setActualImages() {

        viewPager2AdapterTimerRunning = new ViewPager2Adapter(this.getContext(), possesList.get(viewPager2.getCurrentItem()).getDrawables());
        actualFishPosition = viewPager2.getCurrentItem();
        viewPager2.setAdapter(viewPager2AdapterTimerRunning);

        Log.d("TimerFragment", "Set Actual Images of ViewPager to: " + Arrays.toString(possesList.get(viewPager2.getCurrentItem()).getDrawables()));
    }

    private int getAdultFishDrawable() {
        return possesList.get(viewPager2.getCurrentItem()).getDrawables()[2];
    }


    private void updateFishImage() {
        int position;

        if (timeLeft <= pickedMinutes * 0.33 * 60000) {
            position = 2;
        } else if (timeLeft <= pickedMinutes * 60000 * 0.66) {
            position = 1;
        } else {
            position = 0;
        }
        Log.d("TimerFragment", Integer.toString(viewPager2.getCurrentItem()));
        viewPager2.setCurrentItem(position);
    }


    /**
     * Update text view every second to imitate countdown
     */
    private void updateTimerText() {
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;
        String timeLeft;
        timeLeft = "" + minutes;
        timeLeft += ":";

        if (seconds < 10) {
            timeLeft += "0";
        }
        timeLeft += seconds;
        binding.timerText.setText(timeLeft);
    }


    private void stopTimer() {
        if (countdown != null)
            countdown.cancel();

        Navigation.findNavController(requireView()).navigate(TimerFragmentDirections.actionTimerToKilledFish());
        resetTimer();
        timerRunning = false;
        viewPager2.setAdapter(viewPager2Adapter);
        viewPager2.setUserInputEnabled(true);
        Log.d("TimerFragment", "Enable scrolling of ViewPager");
        Log.i("TimerFragment", "Timer stopped. Navigate to FishKilledFragment");
    }


    /**
     * sets default quote if the quote which is given by the api does not work for some reason
     */
    private void setDefaultQuote() {
        timerViewModel.loadQuote().observe(getViewLifecycleOwner(), s -> {
            if (s == null) {
                binding.quote.setText(getText(R.string.defaultQuote));
                Log.i("TimerFragment", "Set default quote: Only dead fish go with the flow");
            } else {
                binding.quote.setText(s);
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        if (timerRunning) {
            timerViewModel.saveFishKill();
            Log.d("TimerFragment", "Fish killed onStop()");
        }
    }


    /**
     * resets timer to default value and sets start value for button
     */
    private void resetTimer() {
        MainActivity.showBottomNav();
        binding.timerText.setText(getString(R.string.startTime));
        binding.startTimerBtn.setVisibility(View.VISIBLE);
        binding.stopTimerBtn.setVisibility(View.GONE);
        binding.timerText.setEnabled(true);
        Log.d("TimerFragment", "Enable scrolling of ViewPager");
        Log.d("TimerFragment", "reset timer to 10 minutes");
    }


    /**
     * KilledFishFragment is shown when user did not kill the application, but let it run in the background
     * and reopens the app again
     */
    @Override
    public void onResume() {
        super.onResume();
        if (timerRunning) {
            stopTimer();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerRunning) {
            stopTimer();
            Log.d("TimerFragment", "Timer was stopped, because the activity was destroyed");
        }
        binding = null;
    }


}
