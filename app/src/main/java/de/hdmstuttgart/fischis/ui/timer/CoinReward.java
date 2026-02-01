package de.hdmstuttgart.fischis.ui.timer;

/**
 * enum that represents the amount of coins the user can receive for his/her focussed time
 */

public enum CoinReward {

    MIN(5), LOW(10), MEDIUM(20), HIGH(30);

    private final int coins;

    CoinReward(final int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }

    //based on how long the user stays focused the reward is calculated
    static CoinReward getReward(int minutes) {
        if (minutes <= 10) {
            return MIN;
        } else if (minutes <= 20) {
            return LOW;
        } else if (minutes <= 40) {
            return MEDIUM;
        } else {
            return HIGH;
        }
    }
}
