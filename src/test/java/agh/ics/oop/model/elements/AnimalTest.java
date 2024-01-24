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

        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(5,5);

        List<Integer> genome1 = new ArrayList<>();
        genome1.add(2);

        Animal animal1 = new Animal(new Vector2d(5,5),10,new Genotype(genome1),0);

        GameMap map = new GameMap(lowerLeft,upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);

        animal1.move(map);
        assertEquals(animal1.getPosition(),new Vector2d(0,5));


        List<Integer> genome2 = new ArrayList<>();
        genome2.add(1);

        Animal animal2 = new Animal(new Vector2d(5,5),10,new Genotype(genome2),0);

        GameMap map2 = new GameMap(lowerLeft,upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);

        animal2.move(map2);
        assertEquals(animal2.getPosition(),new Vector2d(5,5));


        List<Integer> genome3 = new ArrayList<>();
        genome3.add(7);

        Animal animal3 = new Animal(new Vector2d(0,5),10,new Genotype(genome3),0);

        GameMap map3 = new GameMap(lowerLeft,upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);

        animal3.move(map3);
        assertEquals(animal3.getPosition(),new Vector2d(0,5));

        List<Integer> genome4 = new ArrayList<>();
        genome4.add(6);

        Animal animal4 = new Animal(new Vector2d(0,3),10,new Genotype(genome4),0);

        GameMap map4 = new GameMap(lowerLeft,upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);

        animal4.move(map4);
        assertEquals(animal4.getPosition(),new Vector2d(5,3));



        List<Integer> genome5 = new ArrayList<>();
        genome5.add(4);

        Animal animal5 = new Animal(new Vector2d(3,0),10,new Genotype(genome5),0);

        GameMap map5 = new GameMap(lowerLeft,upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);

        animal5.move(map5);
        assertEquals(animal5.getPosition(),new Vector2d(3,0));



        List<Integer> genome6 = new ArrayList<>();
        genome6.add(3);

        Animal animal6 = new Animal(new Vector2d(3,0),10,new Genotype(genome6),0);

        GameMap map6 = new GameMap(lowerLeft,upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);

        animal6.move(map6);
        assertEquals(animal6.getPosition(),new Vector2d(3,0));




        List<Integer> genome7 = new ArrayList<>();
        genome7.add(5);

        Animal animal7 = new Animal(new Vector2d(3,0),10,new Genotype(genome7),0);

        GameMap map7 = new GameMap(lowerLeft,upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);

        animal7.move(map7);
        assertEquals(animal6.getPosition(),new Vector2d(3,0));


    }
}
