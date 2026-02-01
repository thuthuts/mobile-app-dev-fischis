package de.hdmstuttgart.fischis.fish;

import android.net.Uri;

import androidx.room.Entity;

/**
 * abstract class to create different fish types per factory
 */

@Entity
public abstract class Fish {
    private String name;
    private int costs;
    private int behaviour;
    private Uri fishIcon;
    private int drawable;
    private int[] drawables;

    public Fish(FishType type) {
        this.type = type;
    }

    public abstract void construct();

    private FishType type;


    //getters and setters
    public FishType getType() {
        return type;
    }

    public void setType(FishType type) {
        this.type = type;
    }

    public int getCosts() {
        return costs;
    }

    public int[] getDrawables() {
        return drawables;
    }

    public int getBehaviour() {
        return behaviour;
    }

    public String getName() {
        return name;
    }

    public Uri getFishIcon() {
        return fishIcon;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public void setBehaviour(int behaviour) {
        this.behaviour = behaviour;
    }

    public void setFishIcon(Uri fishIcon) {
        this.fishIcon = fishIcon;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public void setDrawables(int[] drawables) {
        this.drawables = drawables;
    }
}
