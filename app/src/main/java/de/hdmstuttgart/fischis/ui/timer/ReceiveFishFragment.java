package de.hdmstuttgart.fischis.ui.timer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.jetbrains.annotations.NotNull;

import de.hdmstuttgart.fischis.databinding.FragmentReceiveFishBinding;

import de.hdmstuttgart.fischis.ui.MainActivity;

/**
 * class that represents the adult fish when the user finished the focus process successfully
 */
public class ReceiveFishFragment extends Fragment {

    private FragmentReceiveFishBinding binding;
    private int drawable;


    public ReceiveFishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReceiveFishBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        drawable = ReceiveFishFragmentArgs.fromBundle(getArguments()).getDrawable();
        Log.d("ReceiveFishFragment", "Received fish image from TimerFragment");
        MainActivity.hideBottomNav();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.adultFish.setImageResource(drawable);

        //on click the acceptButton the navGraph navigates back to the TimerFragment
        binding.acceptButton.setOnClickListener(
                buttonView -> Navigation.findNavController(view).navigate(ReceiveFishFragmentDirections.actionReceiveFishToTimer()));
        Log.i("ReceiveFishFragment", "Navigation to TimerFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.showBottomNav();
        binding = null;
    }

}