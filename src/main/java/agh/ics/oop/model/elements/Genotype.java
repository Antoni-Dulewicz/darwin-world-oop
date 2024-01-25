package agh.ics.oop.model.elements;

import agh.ics.oop.model.MutationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Genotype {
    private final List<Integer> genome;
    private int currentGene;

    public Genotype(int genomeSize){
        this.genome = new ArrayList<>();
        for (int j = 0; j < genomeSize; j++){
            int x = (int)(Math.random()*8);
            this.genome.add(x);
        }
        Random random = new Random();
        this.currentGene = random.nextInt(genomeSize);
    }

    public Genotype(List<Integer> genome){
        this.genome = genome;
        Random random = new Random();
        this.currentGene = random.nextInt(genome.size());
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public int getCurrentGene() {
        return currentGene;
    }

    public void mutateNormal(int numberOfMutatedGenes){
        Random random = new Random();
        for (int i = 0; i < numberOfMutatedGenes; i++){
            int index = random.nextInt(this.genome.size());
            int value = random.nextInt(8);
            this.genome.set(index,value);
        }
    }

    public void mutateWithSwitch(int numberOfMutatedGenes){
        Random random = new Random();
        for (int i = 0; i < numberOfMutatedGenes; i++){
            int index1 = random.nextInt(this.genome.size());
            int index2 = random.nextInt(this.genome.size());
            int value1 = this.genome.get(index1);
            int value2 = this.genome.get(index2);
            this.genome.set(index1,value2);
            this.genome.set(index2,value1);
        }
    }

    public void mutate(MutationType mutationType, int numberOfMutatedGenes) {
        switch (mutationType) {
            case NORMALMUTATION -> {
                mutateNormal(numberOfMutatedGenes);
            }
            case SWITCHMUTATION -> {
                Random random = new Random();
                if(random.nextInt(2) == 0){
                    mutateNormal(numberOfMutatedGenes);
                }else{
                    mutateWithSwitch(numberOfMutatedGenes);
                }

            }
        }

    }

    public void setCurrentGene(int currentGene) {
        this.currentGene = currentGene;
    }

    public int getSize(){
        return this.genome.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return currentGene == genotype.currentGene && Objects.equals(genome, genotype.genome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genome, currentGene);
    }

    @Override
    public String toString() {
        return genome.toString();
    }
}
