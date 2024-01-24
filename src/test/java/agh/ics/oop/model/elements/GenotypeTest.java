package agh.ics.oop.model.elements;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GenotypeTest {
    @Test
    public void testMutateNormal(){
        List<Integer> genome = new ArrayList<>();
        genome.add(0);
        genome.add(1);
        genome.add(2);
        genome.add(3);
        genome.add(4);
        genome.add(5);
        genome.add(6);
        genome.add(7);

        List<Integer> genome1 = new ArrayList<>(genome);
        Genotype genotype = new Genotype(genome);
        genotype.mutateNormal(1);
        assertNotEquals(genotype.getGenome(),genome1);
    }

    @Test
    public void testMutateWithSwitch(){
        List<Integer> genome = new ArrayList<>();
        genome.add(0);
        genome.add(1);
        genome.add(2);
        genome.add(3);
        genome.add(4);
        genome.add(5);
        genome.add(6);
        genome.add(7);


        List<Integer> genome1 = new ArrayList<>(genome);
        Genotype genotype = new Genotype(genome);
        genotype.mutateWithSwitch(1);
        assertNotEquals(genotype.getGenome(),genome1);
    }

    @Test
    public void testMutate(){
        List<Integer> genome = new ArrayList<>();
        genome.add(0);
        genome.add(1);
        genome.add(2);
        genome.add(3);
        genome.add(4);
        genome.add(5);
        genome.add(6);
        genome.add(7);

        List<Integer> genome1 = new ArrayList<>(genome);
        Genotype genotype = new Genotype(genome);
        genotype.mutate(MutationType.NORMALMUTATION,1);
        assertNotEquals(genotype.getGenome(),genome1);
    }

    @Test
    public void testEquals(){
        List<Integer> genome = new ArrayList<>();
        genome.add(0);
        genome.add(1);
        genome.add(2);
        genome.add(3);
        genome.add(4);
        genome.add(5);
        genome.add(6);
        genome.add(7);


        Genotype genotype1 = new Genotype(genome);
        Genotype genotype2 = new Genotype(genome);
        Genotype genotype3 = new Genotype(genome);

        assertTrue(genotype1.equals(genotype1));
        assertFalse(genotype1.equals(genotype2));
        assertFalse(genotype1.equals(genotype3));
        assertFalse(genotype1.equals(null));
        assertTrue(genotype3.equals(genotype3));

    }

}
