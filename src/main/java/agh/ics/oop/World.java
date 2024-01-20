package agh.ics.oop;

import agh.ics.oop.model.GameMap;
import agh.ics.oop.model.MutationType;
import agh.ics.oop.model.PlantsType;
import agh.ics.oop.model.Vector2d;

public class World {
    public static void main(String[] args) {
        System.out.println("SYSTEM ZACZAL DZIALANIE");
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(5,5);
        GameMap map = new GameMap(lowerLeft,upperRight,MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);
        Simulation simulation = new Simulation(map);
        map.createMap(100,0,5,3);

        System.out.println(map.toString());
        for(int i = 0; i < 10; i++) {
            System.out.println("DAY " + (i+1));

            simulation.run(0,3,
                    5,5,3, 0);

        }
        System.out.println("SYSTEM ZAKONCZYL DZIALANIE");


    }
}
