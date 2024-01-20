package agh.ics.oop.model.elements;

import agh.ics.oop.model.Vector2d;

public class Rectangle {
    private Vector2d lowerLeft;
    private Vector2d upperRight;

    public Rectangle(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }

    public boolean contains(Vector2d position) {
        return position.getX() >= lowerLeft.getX() && position.getX() <= upperRight.getX() && position.getY() >= lowerLeft.getY() && position.getY() <= upperRight.getY();
    }
}
