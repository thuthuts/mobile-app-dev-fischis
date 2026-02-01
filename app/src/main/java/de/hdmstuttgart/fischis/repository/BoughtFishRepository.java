package de.hdmstuttgart.fischis.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executor;

import de.hdmstuttgart.fischis.database.AppDatabase;
import de.hdmstuttgart.fischis.database.BoughtFishDao;
import de.hdmstuttgart.fischis.model.BoughtFish;

/**
 * class that represents the data layer for BoughtFish object
 */

public class BoughtFishRepository {

    private final BoughtFishDao boughtFishDao;
    private LiveData<List<BoughtFish>> boughtFish;
    private final Executor executor;


    public BoughtFishRepository(Application application, Executor executor) {
        this.executor = executor;
        AppDatabase db = AppDatabase.getDatabase(application);
        boughtFishDao = db.boughtFishDao();
        boughtFish = boughtFishDao.getAllBoughtFish();
    }


    /**
     * This function calls a query on BoughtFishDao
     * @return LiveData<List < BoughtFish>> with all BoughtFish objects that are stored in BoughtFish
     */
    public LiveData<List<BoughtFish>> getBoughtFish() {
        return boughtFish;
    }


    /**
     * * Calls insert on BoughtFishDao object
     * @param boughtFish that gets inserted into the DB
     */
    public void insert(BoughtFish boughtFish) {
        executor.execute(() -> boughtFishDao.insert(boughtFish));
    }


    /**
     * Calls setAmount on BoughtFishDao object
     * @param fishName that is updated in DB
     */
    public void setAmount(String fishName, int amount) {
        executor.execute(() -> boughtFishDao.setAmount(fishName, amount));
    }


    /**
     * Calls addAmount on BoughtFishDao object
     * @param fishName that is updated in DB
     * @param amount   that is added to the amount of the object that is updated
     */
    public void addAmount(String fishName, int amount) {
        executor.execute(()-> boughtFishDao.addAmount(amount, fishName));
    }


    /**
     * @return List<BoughtFish> which includes all saved BoughtFish objects in the database
     */
    public List<BoughtFish> getBoughtFishList() {
        return boughtFishDao.getBoughtFishList();
    }


    /**
     * Calls deleteAllBoughtFish on BoughtFishDao object
     */
    public void deleteAllBoughtFish(){
        executor.execute(boughtFishDao::deleteAllBoughtFish);
    }


}
