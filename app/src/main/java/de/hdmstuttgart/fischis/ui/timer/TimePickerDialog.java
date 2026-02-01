package de.hdmstuttgart.fischis.ui.timer;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import de.hdmstuttgart.fischis.databinding.TimePickerDialogBinding;


public class TimePickerDialog extends DialogFragment implements NumberPicker.OnValueChangeListener {

    private NumberPicker.OnValueChangeListener valueChangeListener;
    private TimePickerDialogBinding binding;
    private int pickedTime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = TimePickerDialogBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();


        binding.numberPicker.setMinValue(1);
        binding.numberPicker.setMaxValue(60);

        this.setValueChangeListener(this);
        binding.numberPicker.setOnValueChangedListener(this);

        //configurations for round dialog
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        NavController navController = NavHostFragment.findNavController(this);


        //andles START button and saves pickedTime to BackStackEntry so that TimerFragment can
        //access pickedTime
        binding.start.setOnClickListener(v -> {
            valueChangeListener.onValueChange(binding.numberPicker, binding.numberPicker.getMinValue(), binding.numberPicker.getValue());
            navController.getPreviousBackStackEntry().getSavedStateHandle().set("pickedTime", String.valueOf(pickedTime));
            getDialog().dismiss();
            Log.d("TimerPickerDialog", String.valueOf(pickedTime));
            Log.i("TimerPickerDialog", "Closed dialog on start");

        });


        // handles CANCEL button and set NumberPicker to default value (10 minutes)
        binding.cancel.setOnClickListener(
                view1 -> {
                    valueChangeListener.onValueChange(binding.numberPicker, binding.numberPicker.getMinValue(), 10);
                    navController.getPreviousBackStackEntry().getSavedStateHandle().set("key", String.valueOf(pickedTime));
                    getDialog().dismiss();
                    Log.i("TimerPickerDialog", "Closed dialog on cancel");
                });

    }


    /**
     * @param picker numberPicker
     * @param oldVal old time value
     * @param newVal new value which was picked by the user
     *               this methods sets pickedTime to the new value to save in backStackEntry
     */
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        pickedTime = newVal;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }


    /**
     * @param valueChangeListener sets valueChangeListener on NumberPicker
     */
    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }


}

