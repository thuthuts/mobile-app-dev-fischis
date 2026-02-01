package de.hdmstuttgart.fischis.ui.profile;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.fischis.databinding.BoughtFishItemBinding;
import de.hdmstuttgart.fischis.fish.Fish;
import de.hdmstuttgart.fischis.model.BoughtFish;
import de.hdmstuttgart.fischis.ui.MainActivity;

public class BoughtFishAdapter extends RecyclerView.Adapter<BoughtFishAdapter.ViewHolder> {
    private List<BoughtFish> boughtFishList = new ArrayList<>();
    private BoughtFishItemBinding binding;

    public BoughtFishAdapter() {
    }


    /**
     * ViewHolder for BoughtFishAdapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mFishName;
        public final TextView mAmount;
        public final ImageView mfishImage;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);
            mFishName = binding.fishName;
            mAmount = binding.amount;
            mfishImage = binding.fishImage;
        }

    }

    @Override
    public BoughtFishAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = BoughtFishItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }


    @Override
    public void onBindViewHolder(@NonNull BoughtFishAdapter.ViewHolder holder, int position) {
        BoughtFish data = boughtFishList.get(position);
        holder.mFishName.setText(data.getFishName());
        holder.mAmount.setText(String.valueOf(data.getAmount()));
        holder.mfishImage.setImageURI(getImage(data.getFishName()));
    }

    @Override
    public int getItemCount() {
        return boughtFishList.size();
    }

    public void setList(List<BoughtFish> boughtFishList) {
        this.boughtFishList = boughtFishList;
        notifyDataSetChanged();
    }


    /**
     *
     * @param name name of fish object
     * @return Uri of the fish with the given name, so that it can be shown in the RecyclerView
     */
    private Uri getImage(String name) {
        List<Fish> list = new ArrayList<>(MainActivity.getFishData().getFishList());

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return list.get(i).getFishIcon();
            }
        }
        Log.e("debug", "fish not found in FishData");
        return null;
    }

}


