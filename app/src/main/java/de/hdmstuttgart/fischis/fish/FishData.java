package de.hdmstuttgart.fischis.fish;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * class to set up the FishData
 */
public class FishData {


    /**
     * this method creates a list of Fish with the FishFactory
     * fishList is a list of all Fish objects the user can receive
     */
    private final List<Fish> fishList = new ArrayList<>();

    //adding fish objects to list
    public FishData() {
        fishList.add(FishFactory.getFish(FishType.CLOWNFISH));
        fishList.add(FishFactory.getFish(FishType.PANDAMOOR));
        fishList.add(FishFactory.getFish(FishType.PANDAMOORGREEN));
        fishList.add(FishFactory.getFish(FishType.PANDAMOORPURPLE));

        Log.d("FishData", "Set up list with fish: " + fishList.toString());
    }

    public List<Fish> getFishList() {
        return fishList;
    }
}
