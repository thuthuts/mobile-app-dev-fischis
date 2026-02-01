package de.hdmstuttgart.fischis.ui.shop;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.fischis.R;
import de.hdmstuttgart.fischis.databinding.BuyFishFragmentBinding;
import de.hdmstuttgart.fischis.fish.Fish;
import de.hdmstuttgart.fischis.fish.FishType;
import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.model.User;
import de.hdmstuttgart.fischis.ui.MainActivity;


/**
 * dialog which is opened after clicking on an a fish in the shop
 */
public class BuyFishDialogFragment extends DialogFragment {

    private Fish dialogFish;
    private BuyFishFragmentBinding binding;
    private BuyFishViewModel buyFishViewModel;
    private List<Fish> fishList = new ArrayList<>();
    private boolean alreadyBought = false;
    private List<User> user;
    private List<BoughtFish> boughtFish;


    public BuyFishDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fishList = MainActivity.getFishData().getFishList();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = BuyFishFragmentBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        buyFishViewModel = new ViewModelProvider(this).get(BuyFishViewModel.class);

        FishType type = BuyFishDialogFragmentArgs.fromBundle(getArguments()).getFishType();
        Log.d("BuyFishDialogFragment", "Received" + type.name() + "from ShopAdapter");

        dialogFish = buyFishViewModel.getFittingFish(fishList, type);
        Log.d("BuyFishDialogFragment", "Set up dialog with " + dialogFish.getName());

        //configurations for round dialog
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return rootView;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.fishNameDialog.setText(dialogFish.getName());
        binding.fishBehaviourDialog.setText(dialogFish.getBehaviour());
        binding.fishIconDialog.setImageURI(dialogFish.getFishIcon());

        user = buyFishViewModel.getUser();
        boughtFish = buyFishViewModel.getBoughtFishList();


        //checks if user has already bought the fish so the buy button needs to be canceled
        buyFishViewModel.getBoughtFish().observe(getViewLifecycleOwner(), boughtFish -> {
            if (boughtFish != null && buyFishViewModel.containsName(boughtFish, dialogFish.getName())) {

                binding.buy.setText(R.string.alreadyBought);
                alreadyBought = true;
                binding.cancel.setVisibility(View.GONE);
                binding.buy.setBackgroundColor(Color.GRAY);
                binding.cardviewFishImage.setCardBackgroundColor(Color.GRAY);

                Log.d("BuyFishDialogFragment", "Hide buy button as fish was already bought");
            }
        });


        binding.buy.setOnClickListener(v -> {
            if (user.get(0).getCoins() - dialogFish.getCosts() < 0 && !alreadyBought) {

                Toast.makeText(getActivity().getApplicationContext(), R.string.noMoney, Toast.LENGTH_LONG).show();

            } else {

                if (buyFishViewModel.containsName(boughtFish, dialogFish.getName())) {
                    Log.d("BuyShopDialogFragment", "Fish was already bought");
                    getDialog().dismiss();
                } else {
                    Log.d("BuyShopDialogFragment", "Fish was not already bought");
                    buyFishViewModel.subtractCoins(dialogFish.getCosts());
                    buyFishViewModel.insertBoughtFish(new BoughtFish(dialogFish.getName(), 0));
                    getDialog().dismiss();

                }
            }
        });


        //Updating local list user, when live data detected a change in UserDb
        buyFishViewModel.getUsersInformation().observe(getViewLifecycleOwner(), usersLiveData -> user.get(0).setCoins(usersLiveData.get(0).getCoins()));

        //Updating local list boughtFish, when live data detected a change in BoughtFish database
        buyFishViewModel.getBoughtFish().observe(getViewLifecycleOwner(), boughtFishLiveData -> {
            boughtFish.clear();
            boughtFish.addAll(boughtFishLiveData);
        });

        binding.cancel.setOnClickListener(view1 -> getDialog().dismiss());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}


