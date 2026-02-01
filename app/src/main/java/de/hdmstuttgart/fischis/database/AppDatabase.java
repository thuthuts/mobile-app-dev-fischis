package de.hdmstuttgart.fischis.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.model.User;

/**
 * Creates Room DB with User and BoughtFish Model
 */

@Database(entities = {User.class, BoughtFish.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract BoughtFishDao boughtFishDao();

    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "UserDb";

    /**
     * This method returns AppDatabase object when it is already created or creates one
     *
     * @param context to instantiate and object with Room.databaseBuilder
     * @return existing instance or new instance of AppDatabase
     */

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
