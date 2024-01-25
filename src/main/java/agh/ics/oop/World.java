package agh.ics.oop;

import agh.ics.oop.presenters.SimulationGUI;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        System.out.println("System ropzocza dzialanie");
        Application.launch(SimulationGUI.class, args);
        System.out.println("System zakonczyl dzialanie");
    }
}
