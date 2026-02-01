package de.hdmstuttgart.fischis.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executor;
import de.hdmstuttgart.fischis.database.AppDatabase;
import de.hdmstuttgart.fischis.database.UserDao;
import de.hdmstuttgart.fischis.model.User;


/**
 * class that represents the data layer for User object
 */
public class UserRepository {

    private final UserDao userDao;
    private LiveData<List<User>> usersInformation;
    private final Executor executor;

    public UserRepository(Application application, Executor executor) {
        this.executor = executor;
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        usersInformation = userDao.getUsersInformation();
    }


    /**
     * Calls insert on UserDao object
     * @param user that gets inserted into the DB
     */
    public void insert(User user) {
        executor.execute(() -> userDao.insert(user));
    }


    /**
     * Calls setUsername on UserDao object
     * @param username that is updated in DB
     */
    public void setUsername(String username) {
        executor.execute(() -> userDao.setUsername(username));
    }


    /**
     * Calls saveImage on UserDao object
     * @param imageUrl that is updated in DB
     */
    public void saveImage(String imageUrl) {
        executor.execute(() -> userDao.saveImage(imageUrl));
    }


    /**
     * Calls setCoins on UserDao object
     * @param coins that are added to the coins from the user stored in DB
     */
    public void setCoins(int coins) {
        executor.execute(() -> userDao.setCoins(coins));
    }


    /**
     * Calls addMinuted on UserDao object
     * @param minutes that are added to the coins from the user stored in DB
     */
    public void addMinutes(int minutes) {
        executor.execute(() -> userDao.addMinutes(minutes));
    }


    /**
     * This function calls a query on UserDao
     * @return LiveData<List < User>> with all User objects that are stored in UserDb
     */
    public LiveData<List<User>> getUsersInformation() {
        return usersInformation;
    }


    /**
     * Calls subtractCoins on UserDao object
     * @param coins that are subtracted to the coins from the user stored in DB
     */
    public void subtractCoins(int coins) {
        executor.execute(() -> userDao.subtractCoins(coins));
    }


    /**
     *
     * @return List<User> with all users (we only have one)
     */
    public List<User> getUsersList() {
        return userDao.getUsersList();
    }


    /**
     * Calls deleteUser on UserDao object
     */
    public void deleteUser(){ executor.execute(userDao::deleteUser);}
}
