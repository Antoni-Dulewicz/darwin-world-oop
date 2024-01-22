package agh.ics.oop.Statistics;

import agh.ics.oop.model.elements.Animal;

public class StatsPerAnimal {
    private Animal animal;

    public StatsPerAnimal(Animal animal){
        this.animal = animal;
    }


    public String showAnimalStatistics(){
        StringBuilder statsBuilder = new StringBuilder();
        statsBuilder.append("Animal statistics: \n");
        statsBuilder.append("Genotype: " + this.animal.getGenotype().toString() + "\n");
        statsBuilder.append("Energy: " + this.animal.getEnergy() + "\n");
        statsBuilder.append("Plants eaten: " + this.animal.getPlantsEaten() + "\n");
        statsBuilder.append("Children count: " + this.animal.getChildrenCount() + "\n");
        statsBuilder.append("Age: " + this.animal.getAge() + "\n");

        return statsBuilder.toString();
    }
}
