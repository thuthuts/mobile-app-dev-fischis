package de.hdmstuttgart.fischis.ui.shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import de.hdmstuttgart.fischis.databinding.FragmentShopRecyclerViewBinding;
import de.hdmstuttgart.fischis.fish.Fish;
import de.hdmstuttgart.fischis.ui.MainActivity;


public class ShopRecyclerViewFragment extends Fragment {

    private List<Fish> fishList = new ArrayList<>();
    private FragmentShopRecyclerViewBinding binding;
    private ShopViewModel shopViewModel;


    public ShopRecyclerViewFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fishList = MainActivity.getFishData().getFishList();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentShopRecyclerViewBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        shopViewModel.getUsersInformation().observe(getViewLifecycleOwner(), users -> {
            binding.coins.setText(String.valueOf(users.get(0).getCoins()));
            Log.d("ShopRecyclerViewFragment", "Coins are updated");
        });


        final ShopClickListener shopClickListener = (position, type) -> {
            ShopRecyclerViewFragmentDirections.ActionBuyFish action =
                    ShopRecyclerViewFragmentDirections.actionBuyFish(type);

            Navigation.findNavController(view).navigate(action);
            Log.i("ShopAdapter", "Navigation to BuyFishDialogFragment");

        };


        binding.shopList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.shopList.setHasFixedSize(true);
        ShopAdapter shopAdapter = new ShopAdapter(fishList, shopClickListener);
        binding.shopList.setAdapter(shopAdapter);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;

    }


}