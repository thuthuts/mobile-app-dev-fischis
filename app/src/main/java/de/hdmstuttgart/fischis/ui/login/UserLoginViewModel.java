package de.hdmstuttgart.fischis.ui.login;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.repository.BoughtFishRepository;
import de.hdmstuttgart.fischis.repository.TaskExecutor;
import de.hdmstuttgart.fischis.model.User;
import de.hdmstuttgart.fischis.repository.UserRepository;

public class UserLoginViewModel extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<List<User>> usersInformation;
    private SharedPreferences sharedPreferences;
    private BoughtFishRepository boughtFishRepository;
    private LiveData<List<BoughtFish>> boughtFishInformation;

    public UserLoginViewModel(@NonNull Application application) {
        super(application);
        this.repository = new UserRepository(application, new TaskExecutor());
        usersInformation = repository.getUsersInformation();
        this.boughtFishRepository = new BoughtFishRepository(application, new TaskExecutor());
        boughtFishInformation = boughtFishRepository.getBoughtFish();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);

    }

    public void insert(User user) {
        repository.insert(user);
        Log.d("UserLoginViewModel", "Insert user" + user.toString() + "in DB");
    }

    /**
     * saves first log in of user by setting a boolean in shared preferences
     * the next time the user starts the app, UserLoginFragment will be skipped since he/she has already
     * logged in
     */
    public void saveLogin() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LoggedIn", true);
        editor.commit();
    }

    public void saveImage(String imageUrl) {
        repository.saveImage(imageUrl);
        Log.d("UserLoginViewModel", "Set user profile photo to: " + imageUrl);
    }

    public void setUsername(String username) {
        repository.setUsername(username);
        Log.d("UserLoginViewModel", "Set name of user to" + username + "in DB");
    }

    public LiveData<List<User>> getUsersInformation() {
        return usersInformation;
    }

    public void setCoins(int coins) {
        repository.setCoins(coins);
        Log.d("UserLoginViewModel", "Set coins of user to" + coins + "in DB");
    }

    public void insertBoughtFish(BoughtFish fish) {
        boughtFishRepository.insert(fish);
        Log.d("UserLoginViewModel", "Insert BoughtFish" + fish.getFishName() + "in DB");
    }

    public LiveData<List<BoughtFish>> getBoughtFish() {
        return boughtFishInformation;
    }

}
