package agh.ics.oop.model.elements;

import agh.ics.oop.model.Vector2d;

import java.util.Random;

public class Plant {
    private Vector2d position;
    private int energy;
    private boolean isPoisonous;

    public Plant(Vector2d position,int energy, boolean isPoisonous) {
        this.position = position;
        this.energy = energy;
        this.isPoisonous = isPoisonous;
    }

    public void setPoisonous() {
        this.isPoisonous = true;
    }

    public void grow(){
        this.energy ++;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean getIsPoisonous() {
        return isPoisonous;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public String toString() {
        if (this.isPoisonous) return "!";
        return "*";
    }
}
