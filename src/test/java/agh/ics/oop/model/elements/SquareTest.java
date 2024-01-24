package agh.ics.oop.model.elements;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {
    @Test
    public void testAddAnimal() {
        Vector2d position = new Vector2d(1, 1);
        Square square = new Square(position);
        Animal animal = new Animal(position, 5, new Genotype(5), 0);
        square.addAnimal(animal);
        assertEquals(square.getAnimals().size(), 1);
        assertEquals(square.getAnimals().get(0), animal);
    }

    @Test
    public void testRemoveAnimal() {
        Vector2d position = new Vector2d(1, 1);
        Square square = new Square(position);
        Animal animal = new Animal(position, 5, new Genotype(5), 0);
        square.addAnimal(animal);
        square.removeAnimal(animal);
        assertEquals(square.getAnimals().size(), 0);
    }

    @Test
    public void testIsOccupied(){
        Vector2d position = new Vector2d(1, 1);
        Square square = new Square(position);
        Animal animal = new Animal(position, 5, new Genotype(5), 0);
        square.addAnimal(animal);
        assertTrue(square.isOccupied());
    }

    @Test
    public void testIsEmpty(){
        Vector2d position = new Vector2d(1, 1);
        Square square = new Square(position);
        assertTrue(square.isEmpty());
    }

}
