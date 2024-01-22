package agh.ics.oop;

import agh.ics.oop.model.GameMap;
import agh.ics.oop.model.MutationType;
import agh.ics.oop.model.PlantsType;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.presenters.SimulationGUI;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {

        Application.launch(SimulationGUI.class, args);

        /*System.out.println("SYSTEM ZACZAL DZIALANIE");
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(11,11);
        GameMap map = new GameMap(lowerLeft,upperRight,MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);
        Simulation simulation = new Simulation(map);
        map.createMap(500,100,5,5);

        System.out.println(map.toString());
        for(int i = 0; i < 200; i++) {
            System.out.println("DAY " + (i+1));

            simulation.run(1,3,
                    5,8,5, 10);

            System.out.println(map.toString());
        }
        System.out.println("SYSTEM ZAKONCZYL DZIALANIE");*/


    }
}
