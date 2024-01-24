package agh.ics.oop.model.elements;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    public void testMove(){
        List<Integer> genome = new ArrayList<>();
        genome.add(0);
        genome.add(1);
        genome.add(2);
        genome.add(3);
        genome.add(4);
        genome.add(5);
        genome.add(6);
        genome.add(7);

        Animal animal = new Animal(new Vector2d(5,5),10,new Genotype(genome),0);
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(20,20);
        GameMap map = new GameMap(lowerLeft,upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);


        int currIndex = animal.getGenotype().getCurrentGene();
        switch (animal.getGenotype().getGenome().get(currIndex)){
            case 0:
                animal.move(map);
                assertEquals(animal.getPosition(),new Vector2d(5,6));
                break;
            case 1:
                animal.move(map);
                assertEquals(animal.getPosition(),new Vector2d(6,6));
                break;
            case 2:
                animal.move(map);
                assertEquals(animal.getPosition(),new Vector2d(6,5));
                break;
            case 3:
                animal.move(map);
                assertEquals(animal.getPosition(),new Vector2d(6,4));
                break;
            case 4:
                animal.move(map);
                assertEquals(animal.getPosition(),new Vector2d(5,4));
                break;
            case 5:
                animal.move(map);
                assertEquals(animal.getPosition(),new Vector2d(4,4));
                break;
            case 6:
                animal.move(map);
                assertEquals(animal.getPosition(),new Vector2d(4,5));
                break;
            case 7:
                animal.move(map);
                assertEquals(animal.getPosition(),new Vector2d(4,6));
                break;
        }
    }

    @Test
    public void testMoveNearBounds(){
        List<Integer> genome = new ArrayList<>();
        genome.add(2);

        Animal animal = new Animal(new Vector2d(5,5),10,new Genotype(genome),0);
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(5,5);
        GameMap map = new GameMap(lowerLeft,upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);

        animal.move(map);
        assertEquals(animal.getPosition(),new Vector2d(0,5));
    }
}
