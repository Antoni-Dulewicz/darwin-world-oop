package agh.ics.oop.model.elements;

import agh.ics.oop.model.Vector2d;

public record Plant(Vector2d position, int energy, boolean isPoisonous) {

    public String toString() {
        if (this.isPoisonous) return "!";
        return "*";
    }
}
