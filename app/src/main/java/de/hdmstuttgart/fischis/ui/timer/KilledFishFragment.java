package de.hdmstuttgart.fischis.ui.timer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import de.hdmstuttgart.fischis.R;
import de.hdmstuttgart.fischis.databinding.FragmentKilledFishBinding;
import de.hdmstuttgart.fischis.ui.MainActivity;

/**
 * class that represents the gravestone of an killed fish when the user interrupted the focus process and leaves the app
 */
public class KilledFishFragment extends Fragment {

    private FragmentKilledFishBinding binding;
    private KilledFishViewModel killedFishViewModel;


    /**
     * Required empty public constructor
     */
    public KilledFishFragment() {
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.hideBottomNav();

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentKilledFishBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        killedFishViewModel = new ViewModelProvider(this).get(KilledFishViewModel.class);
        binding.fishGraveStone.setImageResource(R.drawable.grabstein);


        /*
         * on click the acceptButton the navGraph navigates back to the TimerFragment
         * set Shared Preferences value
         */
        binding.acceptButton.setOnClickListener(
                buttonView -> {
                    Navigation.findNavController(view).navigate(KilledFishFragmentDirections.actionKilledFishToTimer());
                    killedFishViewModel.setFishKill();
                    Log.i("KilledFishFragment", "Navigate to TimerFragment");
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.showBottomNav();
        binding = null;
    }


}