package agh.ics.oop.presenters;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;

import agh.ics.oop.model.GameMap;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Plant;


import java.util.ArrayList;
import java.util.List;

public class MapPanel extends Region {
    private GameMap map;
    private List<Circle> animalCircles = new ArrayList<>();
    private Animal chosenAnimal;

    public void setMap(GameMap map){
        this.map = map;
        requestLayout();
    }

    public Animal getChosenAnimal() {
        return chosenAnimal;
    }

    public void setChosenAnimal(Animal chosenAnimal) {
        this.chosenAnimal = chosenAnimal;
    }

    public void setAnimalColor(Animal animal, Color color){
        double tileWidth = getWidth() / map.getWidth();
        double tileHeight = getHeight() / map.getHeigth();
        double x = animal.getPosition().x() * tileWidth;
        double y = animal.getPosition().y() * tileHeight;
        double circleX = x + tileWidth / 2;
        double circleY = y + tileHeight / 2;
        double circleRadius = Math.min(tileWidth,tileHeight)/4;
        Circle circle = new Circle(circleX,circleY,circleRadius,color);
        circle.setFill(color);
        animalCircles.add(circle);
        getChildren().add(circle);
    }

    @Override
    protected void layoutChildren() {

        super.layoutChildren();
        getChildren().clear();

        animalCircles.clear();

        if(map != null){
            int mapWidth = map.getWidth();
            int mapHeight = map.getHeigth();
            double tileWidth = getWidth() / mapWidth;
            double tileHeight = getHeight() / mapHeight;

            int highestEnergy = map.getHighestEnergy();

            for(int i = 0; i < mapWidth; i++){
                for(int j = 0; j < mapHeight; j++){
                    double x = i * tileWidth;
                    double y = j * tileHeight;

                    Plant plant = map.getPlant(new Vector2d(i,j));
                    Rectangle rectangle = new Rectangle(x,y,tileWidth,tileHeight);
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setStrokeWidth(1);

                    if(plant != null) {
                        if(plant.isPoisonous()) {

                            rectangle.setFill(Color.web("#999900"));
                        } else { rectangle.setFill(Color.web("#336600")); }
                    } else {
                        rectangle.setFill(Color.TRANSPARENT);
                    }

                    getChildren().add(rectangle);
                    List<Animal> animals = map.getAnimals(new Vector2d(i,j));

                    if(animals != null){
                        boolean flag = false;
                        for(Animal animal : animals){
                            double circleX = x + tileWidth / 2;
                            double circleY = y + tileHeight / 2;
                            double circleRadius = Math.min(tileWidth,tileHeight)/4;

                            int animalEnergy = animal.getEnergy();
                            double energyRatio = (double) animalEnergy / (double) highestEnergy * 100;

                            Circle circle = new Circle(circleX,circleY,circleRadius,Color.web("#994C00"));

                            switch ((int)(energyRatio/10)){
                                case 0 -> circle.setFill(Color.web("#FFE5CC"));
                                case 1 -> circle.setFill(Color.web("#FFCC99"));
                                case 2 -> circle.setFill(Color.web("#FFB266"));
                                case 3 -> circle.setFill(Color.web("#FF9933"));
                                case 4 -> circle.setFill(Color.web("#FF8000"));
                                case 5 -> circle.setFill(Color.web("#CC6600"));
                                case 6 -> circle.setFill(Color.web("#994C00"));
                                case 7 -> circle.setFill(Color.web("#663300"));
                                case 8 -> circle.setFill(Color.web("#331900"));
                                case 9 -> circle.setFill(Color.web("#330000"));
                                case 10 -> circle.setFill(Color.web("#000000"));
                            }

                            circle.setOnMouseClicked(event -> {
                                chosenAnimal = animal;
                                circle.setFill(Color.BLUE);
                            });

                            if (chosenAnimal != null && chosenAnimal.equals(animal)){ flag = true; }

                            getChildren().add(circle);
                        }

                        if(flag){
                            double circleX = x + tileWidth / 2;
                            double circleY = y + tileHeight / 2;
                            double circleRadius = Math.min(tileWidth,tileHeight)/4;
                            Circle circle = new Circle(circleX,circleY,circleRadius,Color.web("#994C00"));
                            circle.setFill(Color.BLUE);
                            getChildren().add(circle);
                        }
                    }
                }
            }
        }
    }
}
