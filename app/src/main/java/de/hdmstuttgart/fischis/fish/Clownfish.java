package de.hdmstuttgart.fischis.fish;

import android.net.Uri;

import de.hdmstuttgart.fischis.R;

/**
 * class that represents one fish type and extends from Fish
 */


public class Clownfish extends Fish {

    Clownfish() {
        super(FishType.CLOWNFISH);
        construct();
    }

    @Override
    public void construct() {

        setName("Nemo");
        setCosts(0);
        setFishIcon(Uri.parse(("android.resource://de.hdmstuttgart.fischis/" + R.drawable.nemo_adult)));
        setDrawable(R.drawable.nemo_adult);
        setDrawables(new int[]{R.drawable.nemo_mini, R.drawable.nemo_middle, R.drawable.nemo_adult});
        setBehaviour(R.string.clownFishName);

    }
}
