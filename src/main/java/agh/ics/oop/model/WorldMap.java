package agh.ics.oop.model;

import agh.ics.oop.World;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Square;
import agh.ics.oop.model.elements.WorldElement;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public interface WorldMap{

    boolean place(WorldElement element, Vector2d position);
    boolean canMoveTo(Vector2d position);
    Object objectAt(Vector2d position);
    boolean isOccupied(Vector2d position);
    Vector2d getLowerLeft();
    Vector2d getUpperRight();
    /*void move(Animal animal);*/
    List<WorldElement> getElements();
    int getHeigth();
    int getWidth();
    Collection<Square> getAllSquares();


}