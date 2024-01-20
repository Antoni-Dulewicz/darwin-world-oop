package agh.ics.oop;

import agh.ics.oop.model.GameMap;
import agh.ics.oop.model.MutationType;
import agh.ics.oop.model.PlantsType;

public class Simulation {
    private final GameMap map;

    public Simulation(GameMap map){
        this.map = map;
    }

    public void run(int minMutatedGenes, int maxMutatedGenes, int energyOfPlant,
                    int energyNeededForCopulation, int energyUsedForCopulation,
                    int numberOfPlantsPerDay){

        Statistics statistics = new Statistics(map);

        /*this.map.createMap(numberOfAnimals,numberOfPlants,energyOfPlant,genomeSize);


        statistics.show();

        System.out.println(map.toString());*/

       /* for(int i = 0; i < numberOfDays; i++) {
        }*/
        map.removeDeadAnimals();
        map.moveAllAnimals();
        map.eatPlants();
        map.allAnimalsCopulation(energyNeededForCopulation,energyUsedForCopulation,minMutatedGenes,maxMutatedGenes);
        map.growPlants(numberOfPlantsPerDay,energyOfPlant);
        /*statistics.show();
        System.out.println(map.toString());*/
            /*System.out.println("DAY " + (i+1));

            System.out.println(map.toString());*/




    }

    public GameMap getMap() {
        return map;
    }


}
