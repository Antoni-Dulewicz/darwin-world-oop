package agh.ics.oop;

import agh.ics.oop.Statistics.Statistics;
import agh.ics.oop.model.GameMap;

public record Simulation(GameMap map) {

    public void run(int minMutatedGenes, int maxMutatedGenes, int energyOfPlant,
                    int energyNeededForCopulation, int energyUsedForCopulation,
                    int numberOfPlantsPerDay) {

        Statistics statistics = new Statistics(map);

        map.removeDeadAnimals();
        map.moveAllAnimals();
        map.eatPlants();
        map.allAnimalsCopulation(energyNeededForCopulation, energyUsedForCopulation, minMutatedGenes, maxMutatedGenes);
        map.growPlants(numberOfPlantsPerDay, energyOfPlant);


    }

}
