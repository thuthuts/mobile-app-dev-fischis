package de.hdmstuttgart.fischis.ui.timer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.stream.Collectors;

import de.hdmstuttgart.fischis.fish.Fish;
import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.model.User;
import de.hdmstuttgart.fischis.repository.BoughtFishRepository;
import de.hdmstuttgart.fischis.repository.QuoteRepository;
import de.hdmstuttgart.fischis.repository.TaskExecutor;
import de.hdmstuttgart.fischis.repository.UserRepository;


/**
 * ViewModel to TimerFragment
 *
 * extends AndroidViewModel
 */
public class TimerViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private QuoteRepository quoteRepository;
    private BoughtFishRepository boughtFishRepository;
    private LiveData<List<User>> usersInformation;
    private TaskExecutor executor = new TaskExecutor();
    private MutableLiveData<String> quote;
    private LiveData<List<BoughtFish>> boughtFishInformation;
    private SharedPreferences sharedPreferences;


    public TimerViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application, executor);
        this.quoteRepository = new QuoteRepository(application, executor);
        this.boughtFishRepository = new BoughtFishRepository(application, executor);
        boughtFishInformation = boughtFishRepository.getBoughtFish();
        usersInformation = userRepository.getUsersInformation();
        quote = quoteRepository.getQuote();
        Log.d("TimerViewModel", "Set quote to:" + quote);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);


    }

    public LiveData<List<User>> getUsersInformation() {
        return usersInformation;
    }

    public List<BoughtFish> getBoughtFishList(){return boughtFishRepository.getBoughtFishList();}

    public void setCoins(int coins) {
        userRepository.setCoins(coins);
    }

    public void addMinutes(int minutes) {
        userRepository.addMinutes(minutes);
        Log.d("TimerViewModel", "Increase amount of minutes by" + minutes);
    }

    public MutableLiveData<String> loadQuote() {
        return quote;
    }

    public void addAmount(String fishName, int amount) {
        boughtFishRepository.addAmount(fishName, amount);
        Log.d("TimerViewModel", "Increase amount of fish" + fishName + " by " + amount);
    }


    /**
     * creates list of fish while comparing the bought fish with all fish the user can possible receive
     * @param fishList   is the list of all fish
     * @param boughtFish is the list with stored BoughtFish from the BoughtFish database
     */
    public List<Fish> createFishPossesList(List<Fish> fishList, List<BoughtFish> boughtFish) {
        return fishList.stream()
                .filter(two -> boughtFish.stream()
                        .anyMatch(one -> one.getFishName().equals(two.getName())))
                .collect(Collectors.toList());
    }


    /**
     * gets the adult images of the Fish and add them to an array
     * @param possesList is the list of all fish the user posses
     * @param images     is the array the images are stored in
     */
    public int[] getAdultDrawable(List<Fish> possesList, int[] images) {

        for (int i = 0; i < possesList.size(); i++) {
            images[i] = possesList.get(i).getDrawables()[2];

        }
        return images;
    }


    /**
     * save boolean in shared preferences. If the user killed a fish by swiping the app, KilledFishFragment
     * is shown when the user open the app again.
     */
    public void saveFishKill() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FishKilled", true);
        editor.commit();
        Log.d("TimerViewModel", "FishKilled true");
    }


}
