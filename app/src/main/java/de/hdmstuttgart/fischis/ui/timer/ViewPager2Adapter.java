package de.hdmstuttgart.fischis.ui.timer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdmstuttgart.fischis.R;
import de.hdmstuttgart.fischis.databinding.ShopListViewBinding;
import de.hdmstuttgart.fischis.databinding.ViewPagerItemBinding;
import de.hdmstuttgart.fischis.ui.shop.ShopAdapter;

/**
 * ViewPager2 Adapter for ViewPager2 in TimerFragment
 *
 * @extends RecyclerView.Adapter
 */

class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

 // Array of images which should be shown
 private int[] images;
 private Context context;
 private static ViewPagerItemBinding binding;

    // Constructor of our ViewPager2Adapter class
    ViewPager2Adapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    /** This method returns the layout
     * @return ViewHolder viewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewPagerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewPager2Adapter.ViewHolder viewHolder = new ViewHolder(binding);
        return viewHolder;
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        binding.imageViewMain.setImageResource(images[position]);


    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return images.length;
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(ViewPagerItemBinding b) {
            super(b.getRoot());
            binding = b;

        }


    }
}

    


