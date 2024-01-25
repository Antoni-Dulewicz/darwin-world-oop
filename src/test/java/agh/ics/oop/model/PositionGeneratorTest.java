package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionGeneratorTest {
    @Test
    public void testRandomPosition(){
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(10,10);
        PositionGenerator positionGenerator = new PositionGenerator(lowerLeft,upperRight);

        for(int i=0;i<100;i++){
            Vector2d position = positionGenerator.randomPosition();
            assertTrue(position.x()>=0 && position.x()<=10);
            assertTrue(position.y()>=0 && position.y()<=10);
        }
    }

    @Test
    public void testRandomPositionInMiddle(){
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(10,10);
        PositionGenerator positionGenerator = new PositionGenerator(lowerLeft,upperRight);

        for(int i=0;i<100;i++){
            Vector2d position = positionGenerator.randomPositionInMiddle();
            assertTrue(position.x()>=0 && position.x()<=10);
            assertTrue(position.y()>=4 && position.y()<=6);
        }
    }

    @Test
    public void testRandomPositionOutsideMiddle(){
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(10,10);
        PositionGenerator positionGenerator = new PositionGenerator(lowerLeft,upperRight);

        for(int i=0;i<100;i++){
            Vector2d position = positionGenerator.randomPositionOutsideMiddle();
            assertTrue(position.x()>=0 && position.x()<=10);
            assertTrue(position.y()>=0 && position.y()<=10);
            assertTrue(position.y()<=4 || position.y()>=6);
        }
    }
}
