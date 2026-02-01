package de.hdmstuttgart.fischis.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executor;

import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.model.User;
import de.hdmstuttgart.fischis.repository.BoughtFishRepository;
import de.hdmstuttgart.fischis.repository.TaskExecutor;
import de.hdmstuttgart.fischis.repository.UserRepository;

public class ProfileViewModel extends AndroidViewModel {

    private UserRepository repository;
    private BoughtFishRepository boughtFishRepository;
    private LiveData<List<User>> usersInformation;
    private LiveData<List<BoughtFish>> boughtFish;
    private Executor executor = new TaskExecutor();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        this.repository = new UserRepository(application, executor);
        this.boughtFishRepository = new BoughtFishRepository(application, executor);
        usersInformation = repository.getUsersInformation();
        boughtFish = boughtFishRepository.getBoughtFish();

    }

    public LiveData<List<User>> getUsersInformation() {
        return usersInformation;
    }

    public LiveData<List<BoughtFish>> getBoughtFish() {
        return boughtFish;
    }


    /**
     * @param focusedTime in minutes
     * @return focusedTime as hours in the format hh:mm
     */
    public String calculateTimeToHours(int focusedTime) {
        return LocalTime.MIN.plus(
                Duration.ofMinutes(focusedTime)
        ).toString();
    }

}
