package de.hdmstuttgart.fischis.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * User object that is stored in AppDatabase UserDb
 */

@Entity(tableName = "UserDb")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String Username;
    private int Coins;
    private String ProfilePicture;
    private int FocusedTimeInMinutes;

    public User() {
    }


    public User(String username, String ProfilePicture) {
        this.Username = username;
        this.ProfilePicture = ProfilePicture;

    }


    public User(String username) {
        this.Username = username;
    }

    //getters and setters
    public int getFocusedTimeInMinutes() {
        return FocusedTimeInMinutes;
    }

    public void setFocusedTimeInMinutes(int focusedTime) {
        FocusedTimeInMinutes = focusedTime;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCoins() {
        return Coins;
    }

    public void setCoins(int coins) {
        Coins = coins;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }


}
