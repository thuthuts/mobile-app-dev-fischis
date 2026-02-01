package de.hdmstuttgart.fischis.ui.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModelProvider;

import de.hdmstuttgart.fischis.R;
import de.hdmstuttgart.fischis.databinding.FragmentLoginBinding;
import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.model.User;
import de.hdmstuttgart.fischis.ui.MainActivity;


public class UserLoginFragment extends Fragment {

    private Uri imageUri;
    private UserLoginViewModel userLoginViewModel;
    private FragmentLoginBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userLoginViewModel = new ViewModelProvider(this).get(UserLoginViewModel.class);

        userLoginViewModel.insert(new User());

        //set placeholder image
        binding.profilePhoto.setImageResource(R.drawable.placeholder_avatar);

        binding.profilePhoto.setOnClickListener((View v) -> checkPermission());

        //close keyboard
        binding.username.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });


        //save input to database after clicking on start button
        //insert Nemo as default fish
        binding.startBtn.setOnClickListener((View v) -> {

            if (binding.username.getText().length() > 0 && imageUri != null) {
                userLoginViewModel.setUsername(binding.username.getText().toString());
                userLoginViewModel.saveImage(imageUri.toString());
                userLoginViewModel.setCoins(1000);

                userLoginViewModel.insertBoughtFish(new BoughtFish("Nemo", 0));
                userLoginViewModel.saveLogin();
                Log.d("UserLoginFragment", "Saved Login");

                Navigation.findNavController(view).navigate(R.id.action_toTimer);
                Log.i("UserLoginFragment", "Navigation to TimerFragment");
                MainActivity.bottomNavigation.setVisibility(View.VISIBLE);

            } else if (binding.username.getText().length() > 0) {

                userLoginViewModel.setUsername(binding.username.getText().toString());
                Navigation.findNavController(view).navigate(R.id.action_toTimer);
                Log.i("UserLoginFragment", "Navigation to TimerFragment");

                MainActivity.bottomNavigation.setVisibility(View.VISIBLE);
                userLoginViewModel.setCoins(1000);
                userLoginViewModel.insertBoughtFish(new BoughtFish("Nemo", 0));

                userLoginViewModel.saveLogin();
                Log.i("UserLoginFragment", "Saved Login");


            } else {
                Toast.makeText(requireContext(), R.string.enterName, Toast.LENGTH_SHORT).show();
            }

        });

    }


    /**
     * Checks if permission was already granted. If not, ask for permission to pick a photo from storage
     */
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            pickPhoto();
        } else {
            mPermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    private ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    Log.d("UserLoginFragment", "onActivityResult: PERMISSION GRANTED");
                    pickPhoto();
                } else {
                    Log.d("UserLoginFragment", "onActivityResult: PERMISSION DENIED");
                }
            });


    /**
     * Create intent for picking a photo from the gallery
     */
    public void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        pickPhotoActivityResultLauncher.launch(intent);
    }


    /**
     * handles result of pickPhoto
     * saves url to picked photo in userDb
     */
    ActivityResultLauncher<Intent> pickPhotoActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        imageUri = data.getData();
                        binding.profilePhoto.setImageURI(imageUri);

                        //persistableUriPermission, so that the image can be shown in profile fragment after restarting the app
                        getContext().getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    }
                }
            });


    @Override
    public void onResume() {
        super.onResume();
        MainActivity.bottomNavigation.setVisibility(View.GONE);
    }


    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}


