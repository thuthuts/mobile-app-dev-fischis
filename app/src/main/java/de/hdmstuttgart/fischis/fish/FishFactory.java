package de.hdmstuttgart.fischis.fish;

import android.util.Log;

/**
 * class that creates the different fish type the user can receive
 */
public class FishFactory {


    /**
     * factory sets up the different fish objects
     */
    public static Fish getFish(FishType type) {

        Fish fish = null;
        switch (type) {

            case CLOWNFISH:
                fish = new Clownfish();
                break;
            case PANDAMOOR:
                fish = new PandaMoor();
                break;
            case PANDAMOORGREEN:
                fish = new PandaMoorGreen();
                break;

            case PANDAMOORPURPLE:
                fish = new PandaMoorPurple();
                break;

            default:
                Log.e("FishFactory", "Fish type does not exist");
                break;
        }
        return fish;
    }
}





