package de.hdmstuttgart.fischis.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import de.hdmstuttgart.fischis.model.BoughtFish;

/**
 * Data access object to call methods to get the bought fish and update the amount of received fish
 */

@Dao
public interface BoughtFishDao {


    /**
     * This function calls a query on BoughtFish Database
     * and returns all BoughtFish objects in a LiveData object from the table
     *
     * @return LiveData<List < BoughtFish>> with all BoughtFish objects that are stored in BoughtFish
     */
    @Query("SELECT * from BoughtFish")
    LiveData<List<BoughtFish>> getAllBoughtFish();


    /**
     * This function calls a query on BoughtFish databse
     * returns all boughFish objects saved in the database as a list
     * @return List<BoughtFish>
     */
    @Query("SELECT * from BoughtFish")
    List<BoughtFish> getBoughtFishList();


    /**
     * This function inserts a bought fish in BoughtFish database
     * @param fish are the BoughtFish objects that are inserted
     */
    @Insert
    void insert(BoughtFish fish);


    /**
     * This function updates the amount of the bought fish
     *
     * @param amount   is the new amount of the object that is updated
     * @param fishName is the name of the bought fish which amount needs to be updated
     */
    @Query("UPDATE BoughtFish set Amount = :amount WHERE FishName = :fishName")
    void setAmount(String fishName, int amount);


    /**
     * This function updates the amount of the bought fish
     *
     * @param amount   is the added amount
     * @param fishName is the name of the bought fish which amount needs to be updated
     */
    @Query("UPDATE BoughtFish set Amount = Amount + :amount WHERE FishName = :fishName")
    void addAmount(int amount, String fishName);


    /**
     * This function deletes all entries in BoughtFish
     *
     */
    @Query("Delete from BoughtFish")
    void deleteAllBoughtFish();








}
