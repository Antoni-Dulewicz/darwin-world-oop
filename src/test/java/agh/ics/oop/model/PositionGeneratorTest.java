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
            assertTrue(position.getX()>=0 && position.getX()<=10);
            assertTrue(position.getY()>=0 && position.getY()<=10);
        }
    }

    @Test
    public void testRandomPositionInMiddle(){
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(10,10);
        PositionGenerator positionGenerator = new PositionGenerator(lowerLeft,upperRight);

        for(int i=0;i<100;i++){
            Vector2d position = positionGenerator.randomPositionInMiddle();
            assertTrue(position.getX()>=0 && position.getX()<=10);
            assertTrue(position.getY()>=4 && position.getY()<=6);
        }
    }

    @Test
    public void testRandomPositionOutsideMiddle(){
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(10,10);
        PositionGenerator positionGenerator = new PositionGenerator(lowerLeft,upperRight);

        for(int i=0;i<100;i++){
            Vector2d position = positionGenerator.randomPositionOutsideMiddle();
            assertTrue(position.getX()>=0 && position.getX()<=10);
            assertTrue(position.getY()>=0 && position.getY()<=10);
            assertTrue(position.getY()<=4 || position.getY()>=6);
        }
    }
}
