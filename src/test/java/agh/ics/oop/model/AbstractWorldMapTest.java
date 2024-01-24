package agh.ics.oop.model;

import agh.ics.oop.model.*;
import agh.ics.oop.model.elements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AbstractWorldMapTest {
    @Test
    public void testPlace(){
        Vector2d position = new Vector2d(1, 1);
        Vector2d position2 = new Vector2d(11, 11);
        AbstractWorldMap map = new GameMap(new Vector2d(0,0), new Vector2d(10,10), MutationType.NORMALMUTATION, PlantsType.REGULARPLANTS);
        Animal animal = new Animal(position, 5, new Genotype(5), 0);
        Plant plant = new Plant(position, 5,false);
        WorldElement element = new WorldElement();
        element.addAnimal(animal);
        element.addPlant(plant);
        map.place(element, position);
        Square square = (Square) map.objectAt(position);
        assertEquals(square.getAnimals().size(), 1);
        assertEquals(square.getAnimals().get(0), animal);
        assertEquals(square.getPlant(), plant);

        assertThrows(IllegalArgumentException.class, () -> {
            map.place(element, position2);
        });
    }

    @Test
    public void testCanMoveTo(){
        Vector2d position = new Vector2d(1, 1);
        Vector2d position2 = new Vector2d(11, 11);
        AbstractWorldMap map = new GameMap(new Vector2d(0,0), new Vector2d(10,10), MutationType.NORMALMUTATION, PlantsType.REGULARPLANTS);
        assertTrue(map.canMoveTo(position));
        assertFalse(map.canMoveTo(position2));
    }

    @Test
    public void testGetAnimals(){
        Vector2d position = new Vector2d(1, 1);
        Vector2d position2 = new Vector2d(11, 11);
        AbstractWorldMap map = new GameMap(new Vector2d(0,0), new Vector2d(10,10), MutationType.NORMALMUTATION, PlantsType.REGULARPLANTS);
        Animal animal = new Animal(position, 5, new Genotype(5), 0);
        WorldElement element = new WorldElement();
        element.addAnimal(animal);
        map.place(element, position);

        assertEquals(map.getAnimals(position).size(), 1);
        assertEquals(map.getAnimals(position).get(0), animal);

        assertEquals(map.getAnimals(position2), null);

    }

    @Test
    public void testGetPlant(){
        Vector2d position = new Vector2d(1, 1);
        Vector2d position2 = new Vector2d(11, 11);
        AbstractWorldMap map = new GameMap(new Vector2d(0,0), new Vector2d(10,10), MutationType.NORMALMUTATION, PlantsType.REGULARPLANTS);
        Plant plant = new Plant(position, 5,false);
        WorldElement element = new WorldElement();
        element.addPlant(plant);
        map.place(element, position);

        assertEquals(map.getPlant(position), plant);
        assertEquals(map.getPlant(position2), null);
    }


}
