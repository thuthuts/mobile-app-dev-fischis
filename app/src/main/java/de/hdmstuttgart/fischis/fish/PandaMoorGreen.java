package de.hdmstuttgart.fischis.fish;

import android.net.Uri;

import de.hdmstuttgart.fischis.R;

/**
 * class that represents one fish type and extends from Fish
 */

public class PandaMoorGreen extends Fish {

    PandaMoorGreen() {
        super(FishType.PANDAMOORGREEN);
        construct();
    }

    @Override
    public void construct() {
        setName("Panda Moor (green)");
        setCosts(500);
        setFishIcon(Uri.parse(("android.resource://de.hdmstuttgart.fischis/" + R.drawable.fish_green_adult)));
        setDrawable(R.drawable.fish_green_adult);
        setDrawables(new int[]{R.drawable.fish_green_mini, R.drawable.fish_green_middle, R.drawable.fish_green_adult});
        setBehaviour(R.string.pandaMoorGreen);

    }
}
