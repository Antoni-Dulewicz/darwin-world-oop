package agh.ics.oop;

import agh.ics.oop.Statistics.Statistics;
import agh.ics.oop.model.GameMap;

public class Simulation {
    private final GameMap map;

    public Simulation(GameMap map){
        this.map = map;
    }

    public void run(int minMutatedGenes, int maxMutatedGenes, int energyOfPlant,
                    int energyNeededForCopulation, int energyUsedForCopulation,
                    int numberOfPlantsPerDay){

        Statistics statistics = new Statistics(map);

        map.removeDeadAnimals();
        map.moveAllAnimals();
        map.eatPlants();
        map.allAnimalsCopulation(energyNeededForCopulation,energyUsedForCopulation,minMutatedGenes,maxMutatedGenes);
        map.growPlants(numberOfPlantsPerDay,energyOfPlant);



    }

    public GameMap getMap() {
        return map;
    }

}
