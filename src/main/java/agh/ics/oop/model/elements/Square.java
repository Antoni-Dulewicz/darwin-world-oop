package agh.ics.oop.model.elements;

import agh.ics.oop.model.Vector2d;

import java.util.List;

public class Square {
    private final Vector2d position;
    private WorldElement element; //element - lista animali i plant

    public Square(Vector2d position) {
        this.position = position;
        this.element = new WorldElement();
    }
    public Square(Vector2d position, WorldElement element) {
        this.position = position;
        this.element = element;
    }

    public void addAnimal(Animal animal){

        this.element.addAnimal(animal);
    }

    public void removeAnimal(Animal animal){

        this.element.removeAnimal(animal);
    }

    public WorldElement getElement() {
        return element;
    }
    public List<Animal> getAnimals(){

        return this.element.getAnimals();
    }


    public Plant getPlant(){
        return this.element.getPlant();
    }
    public void setPlant(Plant plant){
        this.element.setPlant(plant);
    }
    public WorldElement ObjectAt(){
        return this.element;

    }
    public boolean isOccupied(){
        return this.element.hasAnimals() || this.element.hasPlant();
    }

    public String toString() {
        return this.element.toString();
    }

    public boolean isEmpty() {
        return !this.isOccupied();
    }
}
