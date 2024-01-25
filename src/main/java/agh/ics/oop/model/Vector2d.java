package agh.ics.oop.model;

public record Vector2d(int x, int y) {

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }


}
