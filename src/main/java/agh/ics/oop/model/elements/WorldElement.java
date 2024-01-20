package agh.ics.oop.model.elements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class WorldElement {
    private PriorityQueue<Animal> animals;
    private Plant plant;

    public WorldElement() {
        this.animals = new PriorityQueue<>(Comparator.comparingInt(Animal::getEnergy).reversed());
        this.plant = null;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public List<Animal> getAnimals(){

        return List.copyOf(animals);
    }

    public PriorityQueue<Animal> getAnimalsAsQueue(){
        return this.animals;
    }

    public void addAnimal(Animal animal){

        this.animals.add(animal);
    }

    public void removeAnimal(Animal animal){
        this.animals.remove(animal);
    }

    public void addPlant(Plant plant){
        this.plant = plant;
    }

    public boolean hasAnimals(){
        return !this.animals.isEmpty();
    }

    public boolean hasPlant(){
        return this.plant != null;
    }

    public List<WorldElement> getElements(){
        return List.of(this);
    }

    @Override
    public String toString() {
        if(this.hasAnimals()){
            return this.animals.peek().toString();
        }
        if(this.hasPlant()){
            return this.plant.toString();
        }
        return " ";
    }
}
