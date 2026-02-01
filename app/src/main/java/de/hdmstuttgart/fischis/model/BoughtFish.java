package de.hdmstuttgart.fischis.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * BoughtFish object that is stored in AppDatabase BoughtFish
 * amount is the amount of the specific fish type the user has collected after successfully having
 * focused for a specific time, which means that the user did not killed the fish by closing the app
 */


@Entity(tableName = "BoughtFish")
public class BoughtFish {


    @PrimaryKey(autoGenerate = true)
    private int boughtFishId;
    @NonNull
    private String FishName;
    private int Amount;

    public BoughtFish(String FishName, int Amount) {
        this.FishName = FishName;
        this.Amount = Amount;
    }

    //getters and setters
    public String getFishName() {
        return FishName;
    }

    public void setFishName(String fishName) {
        FishName = fishName;
    }

    public int getAmount() {
        return Amount;
    }

    public int getBoughtFishId() {
        return boughtFishId;
    }

    public void setBoughtFishId(int boughtFishId) {
        this.boughtFishId = boughtFishId;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

}
