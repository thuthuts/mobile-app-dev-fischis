package de.hdmstuttgart.fischis.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import de.hdmstuttgart.fischis.R;
import de.hdmstuttgart.fischis.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        binding.boughtFish.setLayoutManager(manager);
        binding.boughtFish.setHasFixedSize(true);

        BoughtFishAdapter adapter = new BoughtFishAdapter();
        binding.boughtFish.setAdapter(adapter);

        profileViewModel.getBoughtFish().observe(getViewLifecycleOwner(), list -> {
            adapter.setList(list);
            Log.d("ProfileFragment", "set adapter with boughtFish list");
        });


        //set statistics
        profileViewModel.getUsersInformation().observe(getViewLifecycleOwner(), users -> {
            binding.focusedTimeValue.setText(String.format(getString(R.string.minutesFocusedTime),users.get(0).getFocusedTimeInMinutes()));
            binding.username.setText(users.get(0).getUsername());
            binding.focusedTimeValueInHours.setText(profileViewModel.calculateTimeToHours(users.get(0).getFocusedTimeInMinutes()));

            if (profileViewModel.getUsersInformation().getValue().get(0).getProfilePicture() != null) {
                binding.profilePicture.setImageURI(Uri.parse(users.get(0).getProfilePicture()));
                Log.d("ProfileFragment","set profile picture");
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}


