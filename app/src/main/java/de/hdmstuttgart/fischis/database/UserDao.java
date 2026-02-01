package de.hdmstuttgart.fischis.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import de.hdmstuttgart.fischis.model.User;

/**
 * Data access object to call methods to insert and update objects in Userdb
 */
@Dao
public interface UserDao {



    /**
     * This function calls a query on userDb
     * and returns all user objects in a LiveData object from the table
     * @return LiveData<List < User>> with all user objects that are stored in Userdb
     */
    @Query("SELECT * from Userdb")
    LiveData<List<User>> getUsersInformation();


    /**
     * This function returns all users (in our case we only have one) with his/her information as a list
     * @return List<User> with all user objects that are stored in UserDb
     */
    @Query("SELECT * from Userdb")
    List<User> getUsersList();


    /**
     * This function inserts a user in UserDb
     * @param user is the User object which is inserted in Userdb
     */
    @Insert
    void insert(User user);


    /**
     * This function selects the name of an user stored in the Userdb
     */
    @Query("SELECT Username from Userdb")
    String getUsername();


    /**
     * This function sets the name of an user stored in the Userdb
     * @param username is the name of the User object which is inserted in Userdb
     */
    @Query("UPDATE Userdb set Username = :username")
    void setUsername(String username);


    /**
     * This function sets the profile picture of an user stored in the Userdb
     * @param imageUrl is the url for the profile picture of the User object which is inserted in Userdb
     */
    @Query("UPDATE Userdb set ProfilePicture = :imageUrl")
    void saveImage(String imageUrl);


    /**
     * This function sets the amount of coins of an user stored in the Userdb
     * @param coins are the added coins
     */
    @Query("UPDATE UserDb set Coins = Coins + :coins")
    void setCoins(int coins);


    /**
     * This function sets the amount of focused minutes of an user stored in the Userdb
     * @param minutes are the added minutes
     */
    @Query("UPDATE UserDb set FocusedTimeInMinutes = FocusedTimeInMinutes + :minutes")
    void addMinutes(int minutes);


    /**
     * This function reduce the amount of coins of an user stored in the Userdb
     * @param coins are the subtracted coins
     */
    @Query("UPDATE UserDb set Coins = Coins - :coins")
    void subtractCoins(int coins);


    /**
     * This function deletes all entries in UserDb
     */
    @Query("Delete from UserDb")
    void deleteUser();


}
