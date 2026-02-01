package de.hdmstuttgart.fischis.fish;

import android.net.Uri;

import de.hdmstuttgart.fischis.R;

/**
 * class that represents one fish type and extends from Fish
 */

public class PandaMoor extends Fish {

    PandaMoor() {
        super(FishType.PANDAMOOR);
        construct();
    }

    @Override
    public void construct() {
        setName("Panda Moor");
        setCosts(200);
        setFishIcon(Uri.parse(("android.resource://de.hdmstuttgart.fischis/" + R.drawable.bluefish_adult)));
        setDrawable(R.drawable.bluefish_adult);
        setDrawables(new int[]{R.drawable.bluefish_mini, R.drawable.bluefish_middle, R.drawable.bluefish_adult});
        setBehaviour(R.string.pandaMoor);


    }
}
