package agh.ics.oop.presenters;

import agh.ics.oop.Statistics.StatsPerAnimal;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;


import agh.ics.oop.model.GameMap;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Plant;



import java.util.List;
import java.util.Vector;

public class MapPanel extends Region {
    private GameMap map;
    private ScrollPane statisticsPerAnimal;

    public void setMap(GameMap map){
        this.map = map;
        requestLayout();
    }

    @Override
    protected void layoutChildren() {

        super.layoutChildren();
        getChildren().clear();

        if(map != null){
            int mapWidth = map.getWidth();
            int mapHeight = map.getHeigth();
            double tileWidth = getWidth() / mapWidth;
            double tileHeight = getHeight() / mapHeight;

            for(int i = 0; i < mapWidth; i++){
                for(int j = 0; j < mapHeight; j++){
                    double x = i * tileWidth;
                    double y = j * tileHeight;

                    Plant plant = map.getPlant(new Vector2d(i,j));

                    Rectangle rectangle = new Rectangle(x,y,tileWidth,tileHeight);

                    rectangle.setStroke(Color.BLACK);
                    rectangle.setStrokeWidth(1);

                    if(plant != null){
                        if(plant.getIsPoisonous()){
                            rectangle.setFill(Color.web("#999900"));
                        }
                        else{
                            rectangle.setFill(Color.web("#336600"));
                        }

                    } else{
                        rectangle.setFill(Color.TRANSPARENT);
                    }


                    List<Animal> animals = map.getAnimals(new Vector2d(i,j));

                    if(animals != null){
                        for(Animal animal : animals){
                            double circleX = x + tileWidth / 2;
                            double circleY = y + tileHeight / 2;
                            double circleRadius = Math.min(tileWidth,tileHeight)/4;
                            Circle circle = new Circle(circleX,circleY,circleRadius,Color.web("#994C00"));
                            //TUTAJ DODAC OBSLUGE KLIKNIECIA NA ZWIERZE
                            circle.setOnMouseClicked(event -> {
                                showAnimalStatistics(animal);
                            });
                            getChildren().add(circle);
                        }
                    }
                    // ALBO TUTAJ
                    /*rectangle.setOnMouseClicked(event -> {
                        System.out.println("Rectangle clicked at position");

                    });*/

                    getChildren().add(rectangle);
                }
            }
        }
    }

    private void showAnimalStatistics(Animal animal){
        StatsPerAnimal statsPerAnimal = new StatsPerAnimal(animal);
        String statistics = statsPerAnimal.showAnimalStatistics();

        statisticsPerAnimal.setContent(new javafx.scene.control.Label(statistics));



    }


}
