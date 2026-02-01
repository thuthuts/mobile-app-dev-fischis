package de.hdmstuttgart.fischis.ui.shop;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hdmstuttgart.fischis.fish.Fish;
import de.hdmstuttgart.fischis.fish.FishType;
import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.model.User;
import de.hdmstuttgart.fischis.repository.BoughtFishRepository;
import de.hdmstuttgart.fischis.repository.TaskExecutor;
import de.hdmstuttgart.fischis.repository.UserRepository;

/**
 * ViewModel to BuyFishDialogFragment
 * @extends AndroidViewModel
 */
public class BuyFishViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private BoughtFishRepository boughtFishRepository;
    private LiveData<List<User>> usersInformation;
    private LiveData<List<BoughtFish>> boughtFishInformation;
    private TaskExecutor executor = new TaskExecutor();


    public BuyFishViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application, executor);
        usersInformation = userRepository.getUsersInformation();

        this.boughtFishRepository = new BoughtFishRepository(application, executor);
        boughtFishInformation = boughtFishRepository.getBoughtFish();

    }

    public LiveData<List<User>> getUsersInformation() {
        return usersInformation;
    }

    public LiveData<List<BoughtFish>> getBoughtFish() {
        return boughtFishInformation;
    }


    public void subtractCoins(int coins) {
        userRepository.subtractCoins(coins);
        Log.d("BuyFishViewModel", "Reduce coins of the user by" + coins);
    }

    public void insertBoughtFish(BoughtFish fish) {
        boughtFishRepository.insert(fish);
        Log.d("BuyFishViewModel", "Insert BoughtFish" + fish.toString() + "in DB");
    }


    public void setAmount(String fishName, int amount) {
        boughtFishRepository.setAmount(fishName, amount);
    }


    public List<BoughtFish> getBoughtFishList() {
        return boughtFishRepository.getBoughtFishList();
    }


    public List<User> getUser() {
        return userRepository.getUsersList();
    }


    /**
     * checks if a fish was already bought by the user
     *
     * @param list     is the list of all fish the user bought
     * @param listName is the name of the fish which shoud match with one of the bought fish list
     */
    public boolean containsName(final List<BoughtFish> list, final String listName) {
        return list.stream().anyMatch(o -> o.getFishName().equals(listName));
    }


    public Fish getFittingFish(final List<Fish> list, final FishType type) {
        Fish fish = list.stream()
                .filter(o -> o.getType().equals(type))
                .findFirst()
                .orElse(null);
        return fish;
    }
}
