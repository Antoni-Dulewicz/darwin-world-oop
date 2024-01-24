package agh.ics.oop.model.elements;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.Objects;

public class Animal {

    private int direction;

    private Vector2d position;

    private Genotype genotype;

    private int energy;
    private int age;
    private int childrenCount;

    private int plantsEaten;
    private int dayOfDeath;

    public Animal(Vector2d initialPosition, int energy, Genotype genotype, int age) {
        this.direction = 0;
        this.position = initialPosition;
        this.genotype = genotype;
        this.energy = energy;
        this.age = age;
        this.childrenCount = 0;
        this.plantsEaten = 0;
        this.dayOfDeath = -1;

    }

    public Animal(int initialDirection,Vector2d initialPosition, int energy, Genotype genotype, int age) {
        this.direction = initialDirection;
        this.position = initialPosition;
        this.genotype = genotype;
        this.energy = energy;
        this.age = age;
        this.childrenCount = 0;
        this.plantsEaten = 0;
        this.dayOfDeath = -1;

    }


    public Genotype getGenotype() {
        return genotype;
    }
    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public int getAge() {
        return age;
    }

    public int getEnergy() {
        return energy;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public void setPlantsEaten(int plantsEaten) {
        this.plantsEaten = plantsEaten;
    }
    public String toString() {
        return String.valueOf(this.energy);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public int getDayOfDeath() {
        return dayOfDeath;
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public boolean isDead(){
        return this.energy <= 0;
    }


    public Animal move(WorldMap map){
        int currentDirection = this.direction;
        int currentGene = this.genotype.getCurrentGene();
        int moveDirection = this.genotype.getGenome().get(currentGene);
        int newDirection = (currentDirection+moveDirection)%8;

        switch(newDirection){
            case 0 -> {
                int x = 0;
                int y = 1;
                Vector2d newPos = this.position.add(new Vector2d(x,y));
                int x1 = newPos.getX();
                int y1 = newPos.getY();
                int width = map.getWidth() + map.getLowerLeft().getX() - 1;
                int height = map.getHeigth() + map.getLowerLeft().getY() - 1;
                if (x1 == map.getLowerLeft().getX()-1) x1 = width;
                if (y1 == height+1){
                    y1 = height;
                    this.direction = 4;
                }else{
                    this.direction = 0;
                }

                newPos = new Vector2d(x1%(width+1), y1);
                this.position = newPos;
            }
            case 1 -> {
                int x = 1;
                int y = 1;
                Vector2d newPos = this.position.add(new Vector2d(x,y));
                int x1 = newPos.getX();
                int y1 = newPos.getY();
                int width = map.getWidth() + map.getLowerLeft().getX()-1;
                int height = map.getHeigth() + map.getLowerLeft().getY()-1;
                if (x1 == map.getLowerLeft().getX()-1) x1 = width;
                if (y1 == map.getLowerLeft().getY()-1) y1 = 0;
                if (y1 == height+1){
                    y1 = height;
                    x1 = this.position.getX();
                    this.direction = 4;
                }else{
                    this.direction = 1;
                }

                newPos = new Vector2d(x1%(width+1), y1);
                this.position = newPos;
            }
            case 2 -> {
                int x = 1;
                int y = 0;
                Vector2d newPos = this.position.add(new Vector2d(x,y));
                int x1 = newPos.getX();
                int y1 = newPos.getY();
                int width = map.getWidth() + map.getLowerLeft().getX()-1;
                int height = map.getHeigth() + map.getLowerLeft().getY()-1;
                if (x1 == map.getLowerLeft().getX()-1) x1 = width;

                newPos = new Vector2d(x1%(width), y1);
                this.position = newPos;
                this.direction = 2;
            }
            case 3 -> {
                int x = 1;
                int y = -1;
                Vector2d newPos = this.position.add(new Vector2d(x,y));
                int x1 = newPos.getX();
                int y1 = newPos.getY();
                int width = map.getWidth() + map.getLowerLeft().getX()-1;
                int height = map.getHeigth() + map.getLowerLeft().getY()-1;
                if (x1 == map.getLowerLeft().getX()-1) x1 = width;
                if (y1 == map.getLowerLeft().getY()-1){
                    y1 = map.getLowerLeft().getY();
                    x1 = this.position.getX();
                    this.direction = 0;
                }else{
                    this.direction = 3;
                }
                newPos = new Vector2d(x1%(width), y1);
                this.position = newPos;
            }
            case 4 -> {
                int x = 0;
                int y = -1;
                Vector2d newPos = this.position.add(new Vector2d(x,y));
                int x1 = newPos.getX();
                int y1 = newPos.getY();
                int width = map.getWidth() + map.getLowerLeft().getX()-1;
                int height = map.getHeigth() + map.getLowerLeft().getY()-1;
                if (x1 == map.getLowerLeft().getX()-1) x1 = width;
                if (y1 == map.getLowerLeft().getY()-1){
                    y1 = map.getLowerLeft().getY();
                    this.direction = 0;
                }else{
                    this.direction = 4;
                }

                newPos = new Vector2d(x1%(width+1), y1);
                this.position = newPos;
            }
            case 5 -> {
                int x = -1;
                int y = -1;
                Vector2d newPos = this.position.add(new Vector2d(x,y));
                int x1 = newPos.getX();
                int y1 = newPos.getY();
                int width = map.getWidth() + map.getLowerLeft().getX()-1;
                int height = map.getHeigth() + map.getLowerLeft().getY()-1;
                if (x1 == map.getLowerLeft().getX()-1) x1 = width;
                if (y1 == map.getLowerLeft().getY()-1){
                    y1 = map.getLowerLeft().getY();
                    x1 = this.position.getX();
                    this.direction = 0;
                }else{
                    this.direction = 5;
                }

                newPos = new Vector2d(x1%(width+1), y1);
                this.position = newPos;
            }
            case 6 -> {
                int x = -1;
                int y = 0;
                Vector2d newPos = this.position.add(new Vector2d(x,y));
                int x1 = newPos.getX();
                int y1 = newPos.getY();
                int width = map.getWidth() + map.getLowerLeft().getX()-1;
                int height = map.getHeigth() + map.getLowerLeft().getY()-1;
                if (x1 == map.getLowerLeft().getX()-1) x1 = width;

                newPos = new Vector2d(x1%(width+1), y1);
                this.position = newPos;
                this.direction = 6;
            }
            case 7 -> {
                int x = -1;
                int y = 1;
                Vector2d newPos = this.position.add(new Vector2d(x,y));
                int x1 = newPos.getX();
                int y1 = newPos.getY();
                int width = map.getWidth() + map.getLowerLeft().getX()-1;
                int height = map.getHeigth() + map.getLowerLeft().getY()-1;
                if (x1 == map.getLowerLeft().getX()-1) x1 = width;
                if (y1 == height+1){
                    y1 = height;
                    x1 = this.position.getX();
                    this.direction = 4;
                }else{
                    this.direction = 7;
                }

                newPos = new Vector2d(x1%(width+1), y1);
                this.position = newPos;
            }
            default -> throw new IllegalArgumentException();
        }
        currentGene = (currentGene + 1) % this.genotype.getSize();
        this.genotype.setCurrentGene(currentGene);
        this.energy -= 1;
        return this;
    }

    public String showStats(){
        return "Energy: " + this.energy + "\n" +
                "Age: " + this.age + "\n" +
                "Children: " + this.childrenCount + "\n" +
                "Plants eaten: " + this.plantsEaten + "\n" +
                "Genotype: " + this.genotype.toString();
    }



}
