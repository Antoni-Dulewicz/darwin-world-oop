package agh.ics.oop.model.elements;

import agh.ics.oop.model.Vector2d;

public record Rectangle(Vector2d lowerLeft, Vector2d upperRight) {

    public boolean contains(Vector2d position) {
        return position.x() >= lowerLeft.x() && position.x() <= upperRight.x() && position.y() >= lowerLeft.y() && position.y() <= upperRight.y();
    }
}
