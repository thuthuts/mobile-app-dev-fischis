package de.hdmstuttgart.fischis.fish;

import android.net.Uri;

import de.hdmstuttgart.fischis.R;

/**
 * class that represents one fish type and extends from Fish
 */

public class PandaMoorPurple extends Fish {
    PandaMoorPurple() {
        super(FishType.PANDAMOORPURPLE);
        construct();
    }

    @Override
    public void construct() {
        setName("Panda Moor (purple)");
        setCosts(10000);
        setFishIcon(Uri.parse(("android.resource://de.hdmstuttgart.fischis/" + R.drawable.fish_purple_adult)));
        setDrawable(R.drawable.fish_purple_adult);
        setDrawables(new int[]{R.drawable.fish_purple_mini, R.drawable.fish_purple_middle, R.drawable.fish_purple_adult});
        setBehaviour(R.string.pandaMoorPink);

    }
}
