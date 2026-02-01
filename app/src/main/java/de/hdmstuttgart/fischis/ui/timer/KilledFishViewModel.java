package de.hdmstuttgart.fischis.ui.timer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * ViewModel for KilledFishFragment
 */
public class KilledFishViewModel extends AndroidViewModel {
    private SharedPreferences sharedPreferences;

    public KilledFishViewModel(@NonNull @NotNull Application application) {
        super(application);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
    }


    /**
     * is called when user "accepts" the fish kill
     * changes SharedPreferences value to false so that after restarting the app, the user sees TimerFragment and
     * not the KilledFishFragment
     */
    public void setFishKill() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FishKilled", false);
        Log.d("KilledFishViewModel", "set value FishKilled to false");
        editor.apply();
    }
}
