package agh.ics.oop.Statistics;

import agh.ics.oop.model.GameMap;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Genotype;
import agh.ics.oop.model.elements.Plant;
import agh.ics.oop.model.elements.Square;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private GameMap map;

    public Statistics(GameMap map) {
        this.map = map;
    }

    public int countAnimals(){
        int counter = 0;
        for(Square square : this.map.getAllSquares()){
            counter += square.getAnimals().size();
        }
        return counter;
    }

    public int countPlants(){
        int counter = 0;
        for(Square square : this.map.getAllSquares()){
            Plant currPlant = square.getPlant();
            if(currPlant != null) counter++;
        }
        return counter;
    }

    public int countFreeSquares(){
        int numberOfAllSquares = this.map.getHeigth() * this.map.getWidth();
        int numberOfOccupiedSquares = this.map.getAllSquares().size();
        int numberOfFreeSquares = numberOfAllSquares - numberOfOccupiedSquares;
        return numberOfFreeSquares;
    }

    public Genotype getMostCommonGenotype(){
        List<Animal> animals = new ArrayList<>();
        for(Square square : this.map.getAllSquares()){
            List<Animal> currAnimals = square.getAnimals();
            for(Animal animal : currAnimals){
                animals.add(animal);
            }
        }
        int mostCommonGenotypeCounter = 0;
        Genotype mostCommonGenotype = null;
        for(int i = 0; i < animals.size();i++){
            int counter = 0;
            for(int j = 0; j < animals.size();j++){
                if(animals.get(i).getGenotype().equals(animals.get(j).getGenotype())){
                    counter++;
                }
            }
            if(counter > mostCommonGenotypeCounter){
                mostCommonGenotype = animals.get(i).getGenotype();
                mostCommonGenotypeCounter = counter;
            }
        }
        return mostCommonGenotype;
    }

    public double getAverageEnergy(){
        double sum = 0;
        double counter = 0;
        for(Square square : this.map.getAllSquares()){
            List<Animal> currAnimals = square.getAnimals();
            for(Animal animal : currAnimals){
                if(animal.getEnergy() > 0){
                    sum += animal.getEnergy();
                    counter++;
                }

            }
        }
        if(counter == 0) return 0;
        return Math.round(sum/counter * 100.0) / 100.0;
    }

    public double getAverageLifeLength(){
        double sum = 0;
        double counter = 0;
        for(Animal animal : this.map.getAllDeadAnimals()){
            sum += animal.getAge();
            counter++;
        }

        if(counter == 0) return 0;
        return Math.round(sum/counter * 100.0) / 100.0;

    }

    public double getAverageChildrenCount(){
        double sum = 0;
        double counter = 0;
        for(Square square : this.map.getAllSquares()){
            List<Animal> currAnimals = square.getAnimals();
            for(Animal animal : currAnimals){
                if(animal.getEnergy() >0){
                    sum += animal.getChildrenCount();
                    counter++;
                }
            }
        }
        if(counter == 0) return 0;
        return Math.round(sum/counter * 100.0) / 100.0;
    }

    public String show(){
        StringBuilder statisticsStringBuilder = new StringBuilder();

        statisticsStringBuilder.append("Number of animals: ").append(this.countAnimals()).append("\n");
        statisticsStringBuilder.append("Number of plants: ").append(this.countPlants()).append("\n");
        statisticsStringBuilder.append("Most common genotype: ").append(this.getMostCommonGenotype()).append("\n");
        statisticsStringBuilder.append("Number of free squares: ").append(this.countFreeSquares()).append("\n");

        if (this.getAverageEnergy() == 0) {
            statisticsStringBuilder.append("There are no animals\n");
        } else {
            statisticsStringBuilder.append("Average energy: ").append(this.getAverageEnergy()).append("\n");
        }

        if (this.getAverageLifeLength() == 0) {
            statisticsStringBuilder.append("There are no dead animals\n");
        } else {
            statisticsStringBuilder.append("Average life length: ").append(this.getAverageLifeLength()).append("\n");
        }

        if (this.getAverageChildrenCount() == 0) {
            statisticsStringBuilder.append("There are no animals/animals with children\n");
        } else {
            statisticsStringBuilder.append("Average children count: ").append(this.getAverageChildrenCount()).append("\n");
        }

        return statisticsStringBuilder.toString();

    }
}
