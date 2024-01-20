package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

public class GameMap extends AbstractWorldMap{

    public GameMap(Vector2d lowerLeft, Vector2d upperRight, MutationType mutationType, PlantsType plantsType){
        super(lowerLeft,upperRight,mutationType,plantsType);
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return super.canMoveTo(position);
    }

    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(getLowerLeft(),getUpperRight());
    }
}
