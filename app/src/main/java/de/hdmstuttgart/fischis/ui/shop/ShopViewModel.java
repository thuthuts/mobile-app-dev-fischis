package de.hdmstuttgart.fischis.ui.shop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import de.hdmstuttgart.fischis.model.User;
import de.hdmstuttgart.fischis.repository.BoughtFishRepository;
import de.hdmstuttgart.fischis.repository.TaskExecutor;
import de.hdmstuttgart.fischis.repository.UserRepository;

/**
 * ViewModel to ShopRecyclerViewFragment
 * extends AndroidViewModel
 */
public class ShopViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private BoughtFishRepository boughtFishRepository;
    private LiveData<List<User>> usersInformation;

    public ShopViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application, new TaskExecutor());
        usersInformation = userRepository.getUsersInformation();

        this.boughtFishRepository = new BoughtFishRepository(application, new TaskExecutor());

    }

    public LiveData<List<User>> getUsersInformation() {
        return usersInformation;
    }
/*
    *
     *
     * @param fishName
     * @param amount
    public void setAmount(String fishName, int amount) {
        boughtFishRepository.setAmount(fishName, amount);
    }*/


}
