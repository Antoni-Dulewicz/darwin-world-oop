package agh.ics.oop.model.elements;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {
    @Test
    public void testContains(){
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(10,10);

        Rectangle rectangle = new Rectangle(lowerLeft,upperRight);

        Vector2d v1 = new Vector2d(5,5);
        Vector2d v2 = new Vector2d(0,0);
        Vector2d v3 = new Vector2d(10,10);
        Vector2d v4 = new Vector2d(12,12);

        assertTrue(rectangle.contains(v1));
        assertTrue(rectangle.contains(v2));
        assertTrue(rectangle.contains(v3));
        assertFalse(rectangle.contains(v4));
    }

}
