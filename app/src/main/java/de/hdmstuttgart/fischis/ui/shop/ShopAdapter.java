package de.hdmstuttgart.fischis.ui.shop;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdmstuttgart.fischis.databinding.ShopListViewBinding;
import de.hdmstuttgart.fischis.fish.Fish;

/**
 * RecyclerView Adapter for ShopRecyclerViewFragment RecyclerView
 *
 * @extends RecyclerView.Adapter
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {


    private List<Fish> fishList;
    private ShopListViewBinding binding;
    private final ShopClickListener shopClickListener;

    public ShopAdapter(List<Fish> fishList, ShopClickListener shopClickListener) {
        this.fishList = fishList;
        this.shopClickListener = shopClickListener;

    }


    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ShopListViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);

        binding.cardView.setOnClickListener(view -> {
            int position = viewHolder.getAdapterPosition();
            if (shopClickListener != null)
                shopClickListener.onItemClick(position, fishList.get(position).getType());

        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {

        Fish fish = fishList.get(position);
        binding.fishName.setText(fish.getName());
        binding.fishIcon.setImageURI(fish.getFishIcon());
        binding.fishPrice.setText(Integer.toString(fish.getCosts()));


    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }


    /**
     * ViewHolder for this Adapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(ShopListViewBinding b) {
            super(b.getRoot());
            binding = b;

        }

    }
}











